package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import entity.LineType;
import entity.Position;

/**
 * An implementation of the {@link IModel} interface.
 *
 * @author Alex Mandelias
 */
@SuppressWarnings("nls")
public class Model implements IModel {

	/**
	 * Constructs a Model for a {@code user} that communicates with the live
	 * underlying database hosted on another {@code host} to retrieve and return
	 * data using objects from the {@link entity} package.
	 *
	 * @param host     the ip address of the database's host
	 * @param user     the database use on whose behalf the connection is being made
	 * @param password the user's password
	 *
	 * @return the Model
	 */
	public static Model forHost(String host, String user, String password) {
		return new Model("jdbc:mysql://" + host + ":3306/oasaripoff", user, password);
	}

	/**
	 * Constructs a Model for the {@code root} user that communicates with the live
	 * underlying database hosted on {@code localhost} to retrieve and return data
	 * using objects from the {@link entity} package.
	 *
	 * @return the Model
	 */
	public static Model forRoot() {
		return new Model("jdbc:mysql://localhost/oasaripoff", "root", "localhostMVCMy$QL");
	}

	private final String url, user, password;

	/**
	 * Constructs a Model that communicates with a database to retrieve and return
	 * data using objects from the {@link entity} package.
	 *
	 * @param url      a database url of the form jdbc:subprotocol:subname
	 * @param user     the database user on whose behalf the connection is being
	 *                 made
	 * @param password the user's password
	 */
	Model(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	@Override
	public List<ETown> getTowns(ELine line) throws SQLException {
		final String qSelectAllTowns    = "SELECT C.* FROM City AS C";
		final String qSelectTownsByLine = "SELECT DISTINCT C.id, C.name FROM City AS C "
		        + "JOIN Station AS S ON S.city_id = C.id "
		        + "JOIN LineStation AS LS ON LS.station_id = S.id "
		        + "JOIN Line AS L ON L.id = LS.line_id "
		        + "WHERE L.id=@1";

		final String qFinalisedQuery;
		if (line == null)
			qFinalisedQuery = qSelectAllTowns + " ORDER BY C.name;";
		else {
			final int id = line.getId();
			qFinalisedQuery = qSelectTownsByLine.replaceAll("@1", String.valueOf(id))
			        + " ORDER BY C.name;";
		}

		final List<ETown> townsFromDatabase = new LinkedList<>();

		doWithStatement((Statement stmt) -> {
			try (ResultSet rs = stmt.executeQuery(qFinalisedQuery)) {
				while (rs.next())
					townsFromDatabase.add(new ETown(rs.getInt("C.id"), rs.getString("C.name")));
			}
		});

		return townsFromDatabase;
	}

	@Override
	public List<ELine> getLines(ETown town, EStation station) throws SQLException {
		if ((town != null) && (station != null))
			throw new IllegalArgumentException("town and station can't both be non-null");

		final String qSelectAllLines;

		if (town != null)
			qSelectAllLines = "SELECT DISTINCT L.* FROM Line AS L "
			        + "JOIN LineStation AS LS ON L.id = LS.line_id "
			        + "JOIN Station AS S ON LS.station_id = S.id "
			        + "JOIN City AS C ON S.city_id = C.id "
			        + "WHERE C.id = " + town.getId() + " "
			        + "ORDER BY L.type, L.lineNo;";
		else if (station != null)
			qSelectAllLines = "SELECT DISTINCT L.* FROM Line AS L "
			        + "JOIN LineStation AS LS ON L.id = LS.line_id "
			        + "JOIN Station AS S ON LS.station_id = S.id "
			        + "WHERE S.id = " + station.getId() + " "
			        + "ORDER BY L.type, L.lineNo;";
		else
			qSelectAllLines = "SELECT L.* FROM Line AS L "
			        + "ORDER BY L.type, L.lineNo;";

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
		        + "JOIN City AS C ON C.id = S.city_id "
		        + "WHERE LS.line_id = @1 "
		        + "ORDER BY LS.station_index;";

		doWithConnection((Connection conn) -> {

			for (final ELine line : linesFromDatabase) {

				final List<EStation> newStations = new LinkedList<>();

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

		final String qSelectTimetablesForLine = "SELECT LT.departure_time FROM LineTimetable AS LT "
		        + "WHERE LT.line_id = @1 "
		        + "ORDER BY LT.departure_time;";

		doWithConnection((Connection conn) -> {

			for (final ELine line : linesFromDatabase) {

				final List<ETimetable> newTimetables = new LinkedList<>();

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

		final List<ELine> completeLinesFromDatabase = new LinkedList<>();

		for (final ELine line : linesFromDatabase) {
			final int              lineId            = line.getId();
			final List<EStation>   stationsForLine   = stationsFromDatabase.get(lineId);
			final List<ETimetable> timetablesForLine = timetablesFromDatabase.get(lineId);

			final ELine newLine = new ELine(lineId, line.getName(), line.getType(),
			        line.getDescription(), stationsForLine, timetablesForLine);

			completeLinesFromDatabase.add(newLine);
		}

		return completeLinesFromDatabase;
	}

	@Override
	public List<EStation> getStations(ETown town) throws SQLException {

		final String qSelectAllStations    = "SELECT S.*, C.* FROM Station AS S "
		        + "JOIN City AS C ON S.city_id = C.id "
		        + "ORDER BY S.name";
		final String qSelectStationsByTown = "SELECT S.*, C.* FROM Station AS S "
		        + "JOIN City AS C ON S.city_id = C.id "
		        + "WHERE C.id=@1 "
		        + "ORDER BY S.name";

		final String qFinalisedQuery;
		if (town == null) {
			qFinalisedQuery = qSelectAllStations;
		} else {
			qFinalisedQuery = qSelectStationsByTown.replaceAll("@1", String.valueOf(town.getId()));
		}

		final List<EStation> sationsFromDatabase = new LinkedList<>();

		doWithStatement((Statement stmt) -> {
			try (ResultSet rs = stmt.executeQuery(qFinalisedQuery)) {
				while (rs.next()) {

					Position newPosition = new Position(rs.getDouble("S.x_coord"),
					        rs.getDouble("S.y_coord"));

					ETown newTown = town;
					if (newTown == null)
						newTown = new ETown(rs.getInt("C.id"), rs.getString("C.name"));

					sationsFromDatabase.add(new EStation(rs.getInt("S.id"), rs.getString("S.name"),
					        newPosition, newTown));
				}
			}
		});

		return sationsFromDatabase;
	}

	@Override
	public void insertLine(ELine line) throws SQLException {
		if (line == null)
			throw new IllegalArgumentException("line can't be null");

		final String qInsertToLine = "INSERT INTO Line (lineNo, description, type) VALUES ('@2', '@3', '@4')";

		doWithStatement((Statement stmt) -> {
			stmt.execute(qInsertToLine.replace("@2", line.getName())
			        .replace("@3", line.getDescription())
			        .replace("@4", line.getType().toString()));
		});
	}

	@Override
	public void insertTown(ETown town) throws SQLException {
		if (town == null)
			throw new IllegalArgumentException("town can't be null");

		final String qInsertToCity = "INSERT INTO City(name) VALUES ('@2')";

		doWithStatement((Statement stmt) -> {
		    stmt.execute(qInsertToCity.replace("@2", town.getName()));
		});
	}

	@Override
	public void insertStation(EStation station) throws SQLException {
		if (station == null)
			throw new IllegalArgumentException("station can't be null");

		final String   qInsertToStation = "INSERT INTO Station(name, x_coord, y_coord, city_id) VALUES ('@2', @3, @4, @5)";
		final Position position         = station.getPosition();

		doWithStatement((Statement stmt) -> {
			stmt.execute(
			        qInsertToStation.replace("@2", station.getName())
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

		final String qDeleteExistingStationsFromLine = "DELETE FROM LineStation AS LS "
		        + "WHERE LS.line_id = @1";
		final String lineID                          = String.valueOf(line.getId());

		doWithStatement((Statement stmt) -> {
		    stmt.execute(
		            qDeleteExistingStationsFromLine.replace("@1", lineID));
		});

		final String qInsertToLineStation = "INSERT INTO LineStation VALUES (@1, @2, @3)";

		final List<EStation> stationsToAdd = new ArrayList<>(line.getStations());
		stationsToAdd.add(index, station);

		doWithConnection((Connection conn) -> {
			for (int i = 0; i < stationsToAdd.size(); i++)
				try (Statement stmt = conn.createStatement()) {
					stmt.execute(qInsertToLineStation.replace("@1", lineID)
					        .replace("@2", String.valueOf(stationsToAdd.get(i).getId()))
					        .replace("@3", String.valueOf(i)));
				}
		});
	}

	@Override
	public void insertTimetableToLine(ELine line, ETimetable timetable) throws SQLException {
		if (line == null)
			throw new IllegalArgumentException("line can't be null");
		if (timetable == null)
			throw new IllegalArgumentException("timetable can't be null");

		final String qInsertToLineTimetable = "INSERT INTO LineTimetable VALUES (@1, '@2')";

		doWithStatement((Statement stmt) -> {
			stmt.execute(qInsertToLineTimetable.replace("@1", String.valueOf(line.getId()))
			        .replace("@2", timetable.getFormattedTime()));
		});
	}

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
	private void doWithStatement(ExecutableWithStatement<Statement> executable)
	        throws SQLException {
		try (Connection conn = DriverManager.getConnection(url, user, password);
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
	private void doWithConnection(ExecutableWithConnection<Connection> executable)
	        throws SQLException {
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			executable.execute(conn);
		}
	}

}
