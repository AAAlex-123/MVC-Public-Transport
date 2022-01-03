package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import entity.LineType;
import entity.MissingSpriteException;
import entity.Position;

/**
 * An implementation of the {@link IModel} interface.
 *
 * @author Alex Mandelias
 */
@SuppressWarnings("nls")
public class Model implements IModel {

	private static final String URL  = "jdbc:mysql://localhost/oasaripoff";
	private static final String USER = "***";
	private static final String PASS = "***";

	@FunctionalInterface
	private interface ExecutableWithStatement<S extends Statement> {
		void execute(S statement) throws SQLException;
	}

	private interface ExecutableWithConnection<C extends Connection> {
		void execute(C connection) throws SQLException;
	}

	/**
	 * Creates a new {@code Connection} that executes an instance of an
	 * {@code ExecutableWithStatement}. A {@code Statement} is created from that
	 * Connection and is passed as a parameter to the ExecutableWithStatement.
	 * <p>
	 * Use to execute a single Statement with a single Statement.
	 *
	 * @param executable the instance of {@code ExecutableWithStatement} to run
	 *
	 * @throws SQLException if the executable throws an SQLException
	 */
	private static void doWithStatement(ExecutableWithStatement<Statement> executable)
	        throws SQLException {
		try (Connection conn = DriverManager.getConnection(Model.URL, Model.USER, Model.PASS);
		        Statement stmt = conn.createStatement();) {
			executable.execute(stmt);
		}
	}

	/**
	 * Creates a new {@code Connection} that is passed as a parameter to the
	 * {@code ExecutableWithConnection}, which is then executed.
	 * <p>
	 * Use to execute many Statements with a single Connection.
	 *
	 * @param executable the instance of {@code ExecutableWithConnection} to run
	 *
	 * @throws SQLException if the executable throws an SQLException
	 */
	private static void doWithConnection(ExecutableWithConnection<Connection> executable)
	        throws SQLException {
		try (Connection conn = DriverManager.getConnection(Model.URL, Model.USER, Model.PASS)) {
			executable.execute(conn);
		}
	}

	private final HashMap<LineType, BufferedImage> spriteCache;

	/**
	 * Constructs a Model that communicates with the underlying database to retrieve
	 * data and return them using Entity objects from the {@link entity} package.
	 */
	public Model() {
		spriteCache = new HashMap<>();
	}

	@Override
	public List<ETown> getTowns(ELine line) throws SQLException {
		final String qSelectAllTowns    = "SELECT C.* FROM City AS C";
		final String qSelectTownsByLine = "SELECT DISTINCT C.id, C.name FROM City AS C "
		        + "JOIN Station AS S ON S.city_id = C.id "
		        + "JOIN LineStation AS LS ON LS.station_id = S.id "
		        + "JOIN Line AS L ON L.id = LS.line_id "
		        + "WHERE L.name = @1 AND L.type = @2";

		final String qFinalisedQuery;
		if (line == null) {
			qFinalisedQuery = qSelectAllTowns;
		} else {
			final String name = line.getLineNumber();
			final String type = line.getType().getName();
			qFinalisedQuery = qSelectTownsByLine.replaceAll("@1", name).replace("@2", type);
		}

		List<ETown> towns = new LinkedList<>();

		doWithStatement((Statement stmt) -> {
			try (ResultSet rs = stmt.executeQuery(qFinalisedQuery)) {
				while (rs.next())
					towns.add(new ETown(rs.getInt("C.id"), rs.getString("C.name")));
			}
		});

		return towns;
	}

	@Override
	public List<ELine> getLines(ETown town, EStation station) throws SQLException {
		if ((town != null) && (station != null))
			throw new IllegalArgumentException("town and station can't both be non-null");

		final String qSelectAllLines;

		if (town != null) {
			qSelectAllLines = "SELECT DISTINCT L.* FROM Line AS L "
			        + "JOIN LineStation AS LS ON L.id = LS.line_id "
			        + "JOIN Station AS S ON LS.station_id = S.id "
			        + "JOIN City AS C ON Station.city_id = C.id "
			        + "WHERE C.id = " + town.getId();
		} else if (station != null) {
			qSelectAllLines = "SELECT DISTINCT L.* FROM Line AS L "
			        + "JOIN LineStation AS LS ON L.id = LS.line_id "
			        + "JOIN Station AS S ON LS.station_id = S.id "
			        + "WHERE S.id = " + station.getId();
		} else {
			qSelectAllLines = "SELECT L.* FROM Line AS L ";
		}

		final List<ELine>                    linesFromDatabase      = new LinkedList<>();
		final Map<Integer, List<EStation>>   stationsFromDatabase   = new HashMap<>();
		final Map<Integer, List<ETimetable>> timetablesFromDatabase = new HashMap<>();

		doWithStatement((Statement stmt) -> {
			try (ResultSet rs = stmt.executeQuery(qSelectAllLines)) {
				while (rs.next()) {
					final int      id          = rs.getInt("L.id");
					final String   lineNo      = rs.getString("L.lineNo");
					final LineType type        = LineType.valueOf(rs.getString("L.type"));
					final String   description = rs.getString("L.description");

					linesFromDatabase.add(new ELine(id, lineNo, type, description, null, null));
				}
			}
		});

		final String qSelectStationsForLine = "SELECT S.*, C.* FROM Station AS S "
		        + "JOIN LineStation AS LS ON S.id = LS.station_id "
		        + "JOIN City AS C ON C.id = S.id "
		        + "WHERE LS.line_id = @1";

		doWithConnection((Connection conn) -> {

			for (ELine line : linesFromDatabase) {

				List<EStation> newStations = new LinkedList<>();

				final String lineID = String.valueOf(line.getId());
				final String query  = qSelectStationsForLine.replace("@1", lineID);

				try (Statement stmt = conn.createStatement();
				        ResultSet rs = stmt.executeQuery(query)) {

					while (rs.next()) {
						final ETown    newTown = new ETown(rs.getInt("C.id"),
						        rs.getString("C.name"));
						final int      id      = rs.getInt("S.id");
						final String   name    = rs.getString("S.name");
						final Position pos     = new Position(rs.getDouble("S.x_coord"),
						        rs.getDouble("S.y_coord"));

						newStations.add(new EStation(id, name, pos, newTown));
					}
				}

				stationsFromDatabase.put(line.getId(), newStations);
			}
		});

		final String qSelectTimetablesForLine = "SELECLT LT.departure_time FROM LineTimetable AS LT"
		        + "WHERE LT.line_id = @1";

		doWithConnection((Connection conn) -> {

			for (ELine line : linesFromDatabase) {

				List<ETimetable> newTimetables = new LinkedList<>();

				final String lineID = String.valueOf(line.getId());
				final String query  = qSelectTimetablesForLine.replace("@1", lineID);

				try (Statement stmt = conn.createStatement();
				        ResultSet rs = stmt.executeQuery(query)) {

					while (rs.next()) {
						final Time     time    = rs.getTime("departure_time");
						final String[] parts   = time.toString().split(":");
						final int      hours   = Integer.parseInt(parts[0]);
						final int      minutes = Integer.parseInt(parts[1]);

						newTimetables.add(new ETimetable(hours, minutes));
					}
				}

				timetablesFromDatabase.put(line.getId(), newTimetables);
			}
		});

		final List<ELine> finalLines = new LinkedList<>();

		for (ELine line : linesFromDatabase) {
			final int              lineId            = line.getId();
			final List<EStation>   stationsForLine   = stationsFromDatabase.get(lineId);
			final List<ETimetable> timetablesForLine = timetablesFromDatabase.get(lineId);

			final ELine newLine = new ELine(lineId, line.getLineNumber(), line.getType(),
			        line.getName(), stationsForLine, timetablesForLine);

			finalLines.add(newLine);
		}

		return finalLines;
	}

	@Override
	public List<EStation> getStations(ETown town) throws SQLException {
		if (town == null)
			throw new IllegalArgumentException("town can't be null");

		final String stationsByTown = "SELECT S.* FROM Station AS S "
		        + "JOIN City AS C ON S.city_id = C.id "
		        + "WHERE C.name=@1";

		final String query = stationsByTown.replaceAll("@1", town.getName());

		List<EStation> stations = new LinkedList<>();

		doWithStatement((Statement stmt) -> {
			try (ResultSet rs = stmt.executeQuery(query)) {
				while (rs.next())
					stations.add(new EStation(rs.getInt("id"), rs.getString("name"),
					        new Position(rs.getDouble("x_coord"), rs.getDouble("y_coord")), town));
			}
		});

		return stations;
	}

	@Override
	public BufferedImage getVehicleSprite(LineType type) throws MissingSpriteException {
		if (type == null)
			throw new IllegalArgumentException("type can't be null");

		BufferedImage cachedSprite = spriteCache.get(type);
		if (cachedSprite != null)
			return cachedSprite;

		BufferedImage loadedSprite = null;
		File          file         = null;

		try {
			file = new File(type.getName());
			loadedSprite = ImageIO.read(file);
		} catch (final IOException e) {
			// should this simply consume the exception and not load anything?
			throw new MissingSpriteException(file);
		}

		spriteCache.put(type, loadedSprite);

		return loadedSprite;
	}

	@Override
	public void insertLine(ELine line) throws SQLException {
		if (line == null)
			throw new IllegalArgumentException("line can't be null");

		final String insertToLine = "INSERT INTO Line VALUES ('@2', '@3', '@4')";

		doWithStatement((Statement stmt) -> {
			stmt.execute(insertToLine.replace("@2", line.getLineNumber())
				        .replace("@3", line.getName())
				        .replace("@4", line.getType().getName()));
		});
	}

	@Override
	public void insertTown(ETown town) throws SQLException {
		if (town == null)
			throw new IllegalArgumentException("town can't be null");

		final String insertToCity = "INSERT INTO City(name) VALUES ('@2')";

		doWithStatement((Statement stmt) -> {
		    stmt.execute(insertToCity.replace("@2", town.getName()));
		});
	}

	@Override
	public void insertStation(EStation station) throws SQLException {
		if (station == null)
			throw new IllegalArgumentException("station can't be null");

		final String   insertToStation = "INSERT INTO Station(name, x_coord, y_coord, city_id) VALUES ('@2', @3, @4, @5)";
		final Position position        = station.getPosition();

		doWithStatement((Statement stmt) -> {
			stmt.execute(
			        insertToStation.replace("@2", station.getName())
			                .replace("@3", String.valueOf(position.getX()))
			                .replace("@4", String.valueOf(position.getY()))
			                .replace("@5", String.valueOf(station.getTown().getId())));
		});
	}

	@Override
	public void insertStationToLine(ELine line, EStation station, int index) throws SQLException {
		if (line == null)
			throw new IllegalArgumentException("line can't be null");
		if (station == null)
			throw new IllegalArgumentException("station can't be null");
		if ((index < 0) || (index > line.getStations().size()))
			throw new IllegalArgumentException(
			        "index must be between 0 and the number of the line's stations");

		final String insertToLineStation = "INSERT INTO LineStation VALUES (@1, @2, @3)";

		doWithStatement((Statement stmt) -> {
			stmt.execute(insertToLineStation.replace("@1", String.valueOf(line.getId()))
			        .replace("@2", String.valueOf(station.getId()))
			        .replace("@3", String.valueOf(index)));
		});
	}

	@Override
	public void insertTimetableToLine(ELine line, ETimetable timetable) throws SQLException {
		if (line == null)
			throw new IllegalArgumentException("line can't be null");
		if (timetable == null)
			throw new IllegalArgumentException("timetable can't be null");

		final String insertToLineTimetable = "INSERT INTO LineTimetable VALUES (@1, '@2')";

		doWithStatement((Statement stmt) -> {
			stmt.execute(insertToLineTimetable.replace("@1", String.valueOf(line.getId()))
			        .replace("@2", timetable.toString()));
		});
	}
	
	@Override
	public BufferedImage loadImage(String name) throws IOException {
		BufferedImage loadedSprite = null;
		File          file         = null;
		file = getResourcePath(name);
		loadedSprite = ImageIO.read(file);
		return loadedSprite;
	}
	
	private static File getResourcePath(String spriteName) {
		Path path = Paths.get(System.getProperty("user.dir"));
		String resource_dir = path.toString() + File.separator + "other_resources" + File.separator + "resources";
		String icon_path = resource_dir + File.separator + spriteName;
		return new File(icon_path);
	}
}
