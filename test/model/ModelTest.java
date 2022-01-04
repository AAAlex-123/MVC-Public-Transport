package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import entity.ELine;
import entity.EStation;
import entity.ETown;
import entity.LineType;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
@SuppressWarnings("nls")
class ModelTest {

	private static final String url  = "jdbc:mysql://localhost/test";
	private static final String user = "root";
	private static final String pass = "localhostMVCMy$QL";

	private final IModel model = new Model(url, user, pass);

	/**
	 * TODO
	 *
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		final String[] qCreateTables = {
		        "CREATE TABLE IF NOT EXISTS City ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(31));",
				"CREATE TABLE IF NOT EXISTS Station ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(63), x_coord DOUBLE, y_coord DOUBLE, city_id INT, FOREIGN KEY (city_id) REFERENCES City (id));",
				"CREATE TABLE IF NOT EXISTS Line ( id INT AUTO_INCREMENT PRIMARY KEY, lineNo VARCHAR(7), description VARCHAR(255), type VARCHAR(15));",
				"CREATE TABLE IF NOT EXISTS LineStation ( line_id INT, station_id INT, station_index INT, PRIMARY KEY (line_id, station_id), FOREIGN KEY (line_id) REFERENCES Line(id), FOREIGN KEY (station_id) REFERENCES Station(id));",
				"CREATE TABLE IF NOT EXISTS LineTimetable ( line_id INT, departure_time TIME, PRIMARY KEY (line_id, departure_time), FOREIGN KEY (line_id) REFERENCES Line(id));",
		};

		final String[] qInsertIntoTables = {
		        "INSERT INTO City (name) VALUES ('spata'), ('agia paraskevi'), ('nea filadelfia'), ('metamorfwsh'), ('athina')",

		        "INSERT INTO Station (name, x_coord, y_coord, city_id) VALUES "
		                + "('fournos', 18, 5, 1),"
		                + "('agia paraskevi', 14, 5, 2),"
		                + "('nomismatokopeio', 12, 6, 2),"

						// agia paraskevi
		                + "('nea ionia', 13, 17, 3),"
		                + "('plateia', 14, 19, 3),"

		                + "('papandreou', 17, 25, 4),"
						// plateia
		                + "('syntagma', 3, 10, 5),"

		                + "('aerodromio', 20, 3, 1),"
						// agia paraskevi
						// nomismatokopeio
						// syntagma
		                + "('monasthraki', 0, 12, 5);",

		        "INSERT INTO Line (lineNo, description, type) VALUES "
		                + "('305', 'spata-nomismatokopeio', 'bus'),"
		                + "('421', 'agia paraskevi-nea filadelfia', 'bus'),"
		                + "('B9', 'metamorfwsi-athina', 'bus'),"
		                + "('3', 'aerodromio-syntagma', 'subway');",

		        "INSERT INTO LineStation (line_id, station_id, station_index) VALUES "
		                + "(1, 1, 0),"
		                + "(1, 2, 1),"
		                + "(1, 3, 2),"

		                + "(2, 2, 0),"
		                + "(2, 4, 1),"
		                + "(2, 5, 2),"

		                + "(3, 6, 0),"
		                + "(3, 5, 1),"
		                + "(3, 7, 2),"

		                + "(4, 8, 0),"
		                + "(4, 2, 1),"
		                + "(4, 3, 2),"
		                + "(4, 7, 3),"
		                + "(4, 9, 4);",

		        "INSERT INTO LineTimetable (line_id, departure_time) VALUES "
		                + "(1, '12:00'), (1, '13:00'), (1, '14:00'), (1, '15:00'), (1, '16:00'), (1, '17:00'), (1, '18:00'),"
		                + "(2, '9:00'), (2, '9:30'), (2, '10:00'), (2, '10:30'), (2, '11:00'), (2, '11:30'), (2, '12:00'),"
		                + "(3, '17:00'), (3, '18:00'), (3, '19:00'), (3, '20:00'), (3, '21:00'), (3, '22:00'), (3, '23:00'),"
		                + "(4, '5:00'), (4, '8:00'), (4, '11:00'), (4, '14:00'), (4, '17:00'), (4, '20:00'), (4, '23:00');",

		};

		if (doStart) {
			try (Connection conn = DriverManager.getConnection(url, user, pass)) {
				for (String qCreateTable : qCreateTables) {
					try (Statement stmt = conn.createStatement()) {
						stmt.executeUpdate(qCreateTable);
					}
				}

				for (String qInsertIntoTable : qInsertIntoTables) {
					try (Statement stmt = conn.createStatement()) {
						stmt.executeUpdate(qInsertIntoTable);
					}
				}
			}
		}
	}

	/**
	 * TODO
	 *
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		final String[] qDropTables = {
		        "DROP TABLE LineTimetable",
		        "DROP TABLE LineStation",
		        "DROP TABLE Line",
		        "DROP TABLE Station",
		        "DROP TABLE City",
		};

		if (doEnd) {
			try (Connection conn = DriverManager.getConnection(url, user, pass)) {
				for (String qDropTable : qDropTables) {
					try (Statement stmt = conn.createStatement()) {
						stmt.executeUpdate(qDropTable);
					}
				}
			}
		}
	}

	private static boolean doStart = false, doEnd = false;

	/**
	 * Test method for {@link model.Model#getTowns(entity.ELine)}.
	 */
	@Test
	final void testGetTowns() {

		ETown spata          = new ETown(1, "spata");
		ETown agia_paraskevi = new ETown(2, "agia paraskevi");
		ETown nea_filadelfia = new ETown(3, "nea filadelfia");
		ETown metamorfwsh    = new ETown(4, "metamorfwsh");
		ETown athina         = new ETown(5, "athina");

		List<ETown> eAllTowns = List.of(agia_paraskevi, athina, metamorfwsh, nea_filadelfia, spata);
		List<ETown> aAllTowns = assertDoesNotThrow(() -> {
		    return model.getTowns(null);
		});

		assertIterableEquals(eAllTowns, aAllTowns);

		EStation sSpata          = new EStation(-1, null, null, spata);
		EStation sAgia_paraskevi = new EStation(-1, null, null, agia_paraskevi);
		EStation sAthina         = new EStation(-1, null, null, athina);
		ELine    line            = new ELine(4, "null", null, "null",
		        List.of(sSpata, sAgia_paraskevi, sAthina),
		        new LinkedList<>());

		List<ETown> eTownsByLine = List.of(agia_paraskevi, athina, spata);
		List<ETown> aTownsByLine = assertDoesNotThrow(() -> {
		    return model.getTowns(line);
		});

		assertIterableEquals(eTownsByLine, aTownsByLine);
	}

	/**
	 * Test method for {@link model.Model#getLines(entity.ETown, entity.EStation)}.
	 */
	@Test
	final void testGetLines() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link model.Model#getStations(entity.ETown)}.
	 */
	@Test
	final void testGetStations() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link model.Model#getVehicleSprite(entity.LineType)}.
	 */
	@Test
	final void testGetVehicleSprite() {
		for (LineType type : LineType.values()) {
			assertDoesNotThrow(() -> {
			    model.getVehicleSprite(type);
			});
		}
	}

	/**
	 * Test method for {@link model.Model#insertLine(entity.ELine)}.
	 */
	@Test
	final void testInsertLine() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link model.Model#insertTown(entity.ETown)}.
	 */
	@Test
	final void testInsertTown() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link model.Model#insertStation(entity.EStation)}.
	 */
	@Test
	final void testInsertStation() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link model.Model#insertStationToLine(entity.ELine, entity.EStation, int)}.
	 */
	@Test
	final void testInsertStationToLine() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link model.Model#insertTimetableToLine(entity.ELine, entity.ETimetable)}.
	 */
	@Test
	final void testInsertTimetableToLine() {
		fail("Not yet implemented"); // TODO
	}

}
