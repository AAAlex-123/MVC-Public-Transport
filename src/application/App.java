package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import controller.CLocalImageController;
import controller.Controller;
import controller.IController;
import controller.IImageController;
import localisation.Languages;
import model.IImageModel;
import model.IModel;
import model.MLocalImageModel;
import model.Model;
import view.IView;
import view.OASAView;

/**
 * The entry point of the program. Constructs and runs an {@link view.IView
 * View}.
 *
 * @author Alex Mandelias
 */
public class App {

	private static boolean fail;

	private static final String OASA        = "oasa";                       //$NON-NLS-1$
	private static final String CONSOLE     = "console";                    //$NON-NLS-1$
	private static final String USE_COMMAND = "java App [%s|%s] <options>"; //$NON-NLS-1$

	/**
	 * Constructs and runs an Application.
	 *
	 * @param args used to customise the functionality of the App
	 */
	public static void main(String[] args) {
		boolean oasa, console, reset = false;

		if (args.length == 0)
			fail(String.format(Languages.getString("App.3"), USE_COMMAND), OASA, CONSOLE); //$NON-NLS-1$

		oasa = args[0].equals(OASA);
		console = args[0].equals(CONSOLE);
		if (oasa == console)
			fail(Languages.getString("App.4")); //$NON-NLS-1$

		if (args.length > 1) {
			for (int i = 1; i < args.length; i++) {
				if (args[i].equals("-r")) //$NON-NLS-1$
					reset = true;
				else
					fail(Languages.getString("App.6"), args[i]); //$NON-NLS-1$
			}
		}

		if (reset) {
			try {
				resetDatabase();
			} catch (SQLException e) {
				fail(Languages.getString("App.7")); //$NON-NLS-1$
				e.printStackTrace();
			}
		}

		if (fail)
			return;

		IModel      model      = new Model();

		// TODO replace ConsoleView() with actual implementation
		IView       view       = oasa ? new OASAView() : null /* new ConsoleView() */ ;
		IController controller = new Controller(model, view);

		IImageModel      imageModel       = new MLocalImageModel();
		IImageController imageController = new CLocalImageController(imageModel);

		view.registerController(controller);
		view.registerImageController(imageController);

		view.start();
	}

	private static void fail(String format, Object... args) {
		System.err.printf(format + "%n", args); //$NON-NLS-1$
		fail = true;
	}

	/**
	 * Resets the oasaripoff database with the default data.
	 *
	 * @throws SQLException if an SQLException is thrown while resetting the
	 *                      database
	 */
	private static void resetDatabase() throws SQLException {
		final String[] qDropTables = {
		        "DROP TABLE IF EXISTS LineTimetable", //$NON-NLS-1$
		        "DROP TABLE IF EXISTS LineStation", //$NON-NLS-1$
		        "DROP TABLE IF EXISTS Line", //$NON-NLS-1$
		        "DROP TABLE IF EXISTS Station", //$NON-NLS-1$
		        "DROP TABLE IF EXISTS City", //$NON-NLS-1$
		};

		final String[] qCreateTables = {
		        "CREATE TABLE IF NOT EXISTS City ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(31));", //$NON-NLS-1$
		        "CREATE TABLE IF NOT EXISTS Station ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(63), x_coord DOUBLE, y_coord DOUBLE, city_id INT, FOREIGN KEY (city_id) REFERENCES City (id));", //$NON-NLS-1$
		        "CREATE TABLE IF NOT EXISTS Line ( id INT AUTO_INCREMENT PRIMARY KEY, lineNo VARCHAR(7), description VARCHAR(255), type VARCHAR(15));", //$NON-NLS-1$
		        "CREATE TABLE IF NOT EXISTS LineStation ( line_id INT, station_id INT, station_index INT, PRIMARY KEY (line_id, station_id), FOREIGN KEY (line_id) REFERENCES Line(id), FOREIGN KEY (station_id) REFERENCES Station(id));", //$NON-NLS-1$
		        "CREATE TABLE IF NOT EXISTS LineTimetable ( line_id INT, departure_time TIME, PRIMARY KEY (line_id, departure_time), FOREIGN KEY (line_id) REFERENCES Line(id));", //$NON-NLS-1$
		};

		final String[] qInsertIntoTables = {
		        "INSERT INTO City (name) VALUES ('spata'), ('agia paraskevi'), ('nea filadelfia'), ('metamorfwsh'), ('athina');", //$NON-NLS-1$

		        "INSERT INTO Station (name, x_coord, y_coord, city_id) VALUES " //$NON-NLS-1$
		                + "('spata', 18, 5, 1)," //$NON-NLS-1$
		                + "('agia paraskevi', 14, 5, 2)," //$NON-NLS-1$
		                + "('nomismatokopeio', 12, 6, 2)," //$NON-NLS-1$

						// agia paraskevi
		                + "('nea ionia', 13, 17, 3)," //$NON-NLS-1$
		                + "('plateia', 14, 19, 3)," //$NON-NLS-1$

		                + "('papandreou', 17, 25, 4)," //$NON-NLS-1$
						// plateia
		                + "('syntagma', 3, 10, 5)," //$NON-NLS-1$

		                + "('aerodromio', 20, 3, 1)," //$NON-NLS-1$
						// agia paraskevi
						// nomismatokopeio
						// syntagma
		                + "('monasthraki', 0, 12, 5);", //$NON-NLS-1$

		        "INSERT INTO Line (lineNo, description, type) VALUES " //$NON-NLS-1$
		                + "('305', 'spata-nomismatokopeio', 'BUS')," //$NON-NLS-1$
		                + "('421', 'agia paraskevi-nea filadelfia', 'BUS')," //$NON-NLS-1$
		                + "('B9', 'metamorfwsi-athina', 'BUS')," //$NON-NLS-1$
		                + "('3', 'aerodromio-syntagma', 'SUBWAY');", //$NON-NLS-1$

		        "INSERT INTO LineStation (line_id, station_id, station_index) VALUES " //$NON-NLS-1$
		                + "(1, 1, 0)," //$NON-NLS-1$
		                + "(1, 2, 1)," //$NON-NLS-1$
		                + "(1, 3, 2)," //$NON-NLS-1$

		                + "(2, 2, 0)," //$NON-NLS-1$
		                + "(2, 4, 1)," //$NON-NLS-1$
		                + "(2, 5, 2)," //$NON-NLS-1$

		                + "(3, 6, 0)," //$NON-NLS-1$
		                + "(3, 5, 1)," //$NON-NLS-1$
		                + "(3, 7, 2)," //$NON-NLS-1$

		                + "(4, 8, 0)," //$NON-NLS-1$
		                + "(4, 2, 1)," //$NON-NLS-1$
		                + "(4, 3, 2)," //$NON-NLS-1$
		                + "(4, 7, 3)," //$NON-NLS-1$
		                + "(4, 9, 4);", //$NON-NLS-1$

		        "INSERT INTO LineTimetable (line_id, departure_time) VALUES " //$NON-NLS-1$
		                + "(1, '12:00'), (1, '13:00'), (1, '14:00'), (1, '15:00'), (1, '16:00'), (1, '17:00'), (1, '18:00')," //$NON-NLS-1$
		                + "(2, '9:00'), (2, '9:30'), (2, '10:00'), (2, '10:30'), (2, '11:00'), (2, '11:30'), (2, '12:00')," //$NON-NLS-1$
		                + "(3, '17:00'), (3, '18:00'), (3, '19:00'), (3, '20:00'), (3, '21:00'), (3, '22:00'), (3, '23:00')," //$NON-NLS-1$
		                + "(4, '5:00'), (4, '8:00'), (4, '11:00'), (4, '14:00'), (4, '17:00'), (4, '20:00'), (4, '23:00');", //$NON-NLS-1$
		};

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/oasaripoff", //$NON-NLS-1$
		        "root", "localhostMVCMy$QL")) { //$NON-NLS-1$ //$NON-NLS-2$
			for (String qDropTable : qDropTables) {
				try (Statement stmt = conn.createStatement()) {
					stmt.executeUpdate(qDropTable);
				}
			}

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
