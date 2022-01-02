package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
	private interface ExecutableWithStatement<S extends Statement, R> {
		R execute(S statement) throws SQLException;
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
	 * @param <R>        the type of object that the executable returns
	 *
	 * @return whatever the executable returns
	 *
	 * @throws SQLException if the executable throws an SQLException
	 */
	private static <R> R doWithStatement(ExecutableWithStatement<Statement, R> executable)
	        throws SQLException {
		try (Connection conn = DriverManager.getConnection(Model.URL, Model.USER, Model.PASS);
		        Statement stmt = conn.createStatement();) {
			return executable.execute(stmt);
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
		final String allTowns    = "SELECT * FROM City";
		final String townsByLine = "SELECT DISTINCT City.id, City.name FROM City "
		        + "JOIN Station ON Station.city_id = City.id "
		        + "JOIN LineStation ON LineStation.station_id = Station.id "
		        + "JOIN Line ON Line.id = LineStation.line_id "
		        + "WHERE Line.name=@1 AND Line.type=@2";

		String query;
		if (line == null)
			query = allTowns;
		else {
			final String name = line.getLineNumber();
			final String type = line.getType().getName();
			query = townsByLine.replaceAll("@1", name).replace("@2", type);
		}

		return doWithStatement((Statement stmt) -> {
			List<ETown> towns = new LinkedList<>();

			try (ResultSet rs = stmt.executeQuery(query)) {
				while (rs.next())
					towns.add(new ETown(rs.getInt("id"), rs.getString("name")));
			}

			return towns;
		});
	}

	@Override
	public List<ELine> getLines(ETown town, EStation station) throws SQLException {
		if ((town != null) && (station != null))
			throw new IllegalArgumentException("town and station can't both be non-null");

		if (town != null) {

		} else if (station != null) {

		} else {

		}

		final String allLines = "SELECT * FROM Line "
				+ "JOIN LineStation ON Line.id = LineStation.line_id "
				+ "JOIN Station ON LineStation.station_id = Station.id "
				+ "JOIN LineTimetable ON Line.id = LineTimetable.line_id ";
		final String whereTown = "JOIN City ON City.id = Station.city_id "
		        + "WHERE City.name == ";

		return null;
	}

	@Override
	public List<EStation> getStations(ETown town) throws SQLException {
		if (town == null)
			throw new IllegalArgumentException("town can't be null");

		final String stationsByTown = "SELECT Station.* FROM Station "
		        + "JOIN City ON Station.city_id = City.id "
				+ "WHERE City.name=@1";

		final String query = stationsByTown.replaceAll("@1", town.getName());

		return doWithStatement((Statement stmt) -> {
			List<EStation> stations = new LinkedList<>();

			try (ResultSet rs = stmt.executeQuery(query)) {
				while (rs.next())
					stations.add(new EStation(rs.getInt("id"), rs.getString("name"),
					        new Position(rs.getDouble("x_coord"), rs.getDouble("y_coord")), town));
			}

			return stations;
		});
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
			return stmt.execute(insertToLine.replace("@2", line.getLineNumber())
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
			return stmt.execute(
		            insertToCity.replace("@2", town.getName()));
		});
	}

	@Override
	public void insertStation(EStation station) throws SQLException {
		if (station == null)
			throw new IllegalArgumentException("station can't be null");

		final String   insertToStation = "INSERT INTO Station(name, x_coord, y_coord, city_id) VALUES ('@2', @3, @4, @5)";
		final Position position        = station.getPosition();

		doWithStatement((Statement stmt) -> {
			return stmt.execute(
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
			return stmt.execute(
			        insertToLineStation.replace("@1", String.valueOf(line.getId()))
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
			return stmt.execute(
			        insertToLineTimetable.replace("@1", String.valueOf(line.getId()))
			                .replace("@2", timetable.toString()));
		});
	}
}
