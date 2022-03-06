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
import view.ConsoleView;
import view.IView;
import view.MapView;
import view.OASAView;

/**
 * The entry point of the program. Constructs and runs an {@link view.IView
 * View}.
 *
 * @author Alex Mandelias
 */
public class App {

	private static boolean fail;

	private static final String OASA        = "oasa";                                       //$NON-NLS-1$
	private static final String CONSOLE     = "console";                                    //$NON-NLS-1$
	private static final String MAP         = "map";                                        //$NON-NLS-1$
	private static final String USE_COMMAND = "java App [%s|%s] <options>\n\n"              //$NON-NLS-1$
	        + "where possible options include:\n"                                           //$NON-NLS-1$
	        + "\t-r       reset the database\n"                                             //$NON-NLS-1$
	        + "\t-h <ip>  connect to the database at that IP address";                      //$NON-NLS-1$

	/**
	 * Constructs and runs an Application.
	 *
	 * @param args used to customise the functionality of the App
	 */
	public static void main(String[] args) {
		boolean oasa, console, map, reset = false;
		String  ip = "localhost";            //$NON-NLS-1$

		if (args.length == 0) {
			fail(String.format(Languages.getString("App.3"), USE_COMMAND), OASA, CONSOLE); //$NON-NLS-1$
			System.exit(1);
		}

		oasa = args[0].equals(OASA);
		console = args[0].equals(CONSOLE);
		map = args[0].equals(MAP);
		if (!(oasa || console || map))
			fail(Languages.getString("App.4")); //$NON-NLS-1$

		if (args.length > 1) {
			for (int i = 1; i < args.length; i++) {
				if (args[i].equals("-r")) //$NON-NLS-1$
					reset = true;
				else if (args[i].equals("-h")) //$NON-NLS-1$
					ip = args[++i];
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
			System.exit(2);

		IImageModel      imageModel      = new MLocalImageModel();
		IImageController imageController = new CLocalImageController(imageModel);

		IView view = null;
		if (oasa)
			view = new OASAView(imageController);
		else if (console)
			view = new ConsoleView();
		else if (map)
			view = new MapView(imageController);

		IModel model;
		if (ip.equals("localhost")) //$NON-NLS-1$
			model = Model.forRoot();
		else
			model = Model.forHost(ip, "cant_drop_db", "pass"); //$NON-NLS-1$ //$NON-NLS-2$

		IController controller = new Controller(model, view);

		view.registerController(controller);

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
	@SuppressWarnings("nls")
	private static void resetDatabase() throws SQLException {
		final String[] qDropTables = {
		        "DROP TABLE IF EXISTS LineTimetable",
		        "DROP TABLE IF EXISTS LineStation",
		        "DROP TABLE IF EXISTS Line",
		        "DROP TABLE IF EXISTS Station",
		        "DROP TABLE IF EXISTS City",
		};

		final String[] qCreateTables = {
		        "CREATE TABLE IF NOT EXISTS City ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(31), latitude DOUBLE, longitude DOUBLE);",
		        "CREATE TABLE IF NOT EXISTS Station ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(63), latitude DOUBLE, longitude DOUBLE, city_id INT, FOREIGN KEY (city_id) REFERENCES City (id));",
		        "CREATE TABLE IF NOT EXISTS Line ( id INT AUTO_INCREMENT PRIMARY KEY, lineNo VARCHAR(7), description VARCHAR(255), type VARCHAR(15));",
		        "CREATE TABLE IF NOT EXISTS LineStation ( line_id INT, station_id INT, station_index INT, PRIMARY KEY (line_id, station_id), FOREIGN KEY (line_id) REFERENCES Line(id), FOREIGN KEY (station_id) REFERENCES Station(id));",
		        "CREATE TABLE IF NOT EXISTS LineTimetable ( line_id INT, departure_time TIME, PRIMARY KEY (line_id, departure_time), FOREIGN KEY (line_id) REFERENCES Line(id));",
		};

		// potential source to be used in the future: https://simplemaps.com/data/world-cities
		final String[] qInsertIntoTables = {
		        "INSERT INTO City (name, latitude, longitude) VALUES "
		                + "('Spata', 37.964726085901376, 23.90711123884409),"
		                + "('Agia Paraskevi', 38.013635641541335, 23.821842200915835),"
		                + "('Nea Filadelfeia', 38.033863580434875, 23.737784817644012),"
		                + "('Metamorfosis', 38.063226353421896, 23.760918013856145),"
		                + "('Athens', 37.984043267396316, 23.72763911966045);",

		        "INSERT INTO Station (name, latitude, longitude, city_id) VALUES "
		                + "('Phournos', 37.963260781753874, 23.925781627657045, 1),"
		                + "('Pallini', 38.005795341732345, 23.86958981065715, 1),"
		                + "('ert', 38.0138378551, 23.822938361951255, 2),"
		                + "('nomismatokopeio', 38.00921972340927, 23.80564713712815, 2),"

						// agia paraskevi
		                + "('ika nea ionia', 38.03926151328087, 23.753077896621797, 3),"
		                + "('pl. patriarxou', 38.03359228597294, 23.73756871015507, 3),"

		                + "('hrwwn', 38.063447275075326, 23.760910134208473, 4),"
						// plateia
		                + "('syntagma', 37.975616126169726, 23.73540837227412, 5),"

		                + "('aerodromio', 37.93694595215465, 23.944832305007893, 1),"
						// agia paraskevi
						// nomismatokopeio
						// syntagma
		                + "('monasthraki', 37.976149195440065, 23.725662316788313, 5);",

		        "INSERT INTO Line (lineNo, description, type) VALUES "
		                + "('305', 'spata-nomismatokopeio', 'BUS'),"
		                + "('421', 'agia paraskevi-nea filadelfia', 'BUS'),"
		                + "('B9', 'metamorfwsi-athina', 'BUS'),"
		                + "('3', 'aerodromio-syntagma', 'SUBWAY');",

		        "INSERT INTO LineStation (line_id, station_id, station_index) VALUES "
		                + "(1, 1, 0),"
		                + "(1, 2, 1),"
		                + "(1, 3, 2),"
		                + "(1, 4, 3),"

		                + "(2, 3, 0),"
		                + "(2, 5, 1),"
		                + "(2, 6, 2),"

		                + "(3, 7, 0),"
		                + "(3, 6, 1),"
		                + "(3, 8, 2),"

		                + "(4, 9, 0),"
		                + "(4, 3, 1),"
		                + "(4, 4, 2),"
		                + "(4, 8, 3),"
		                + "(4, 10, 4);",

		        "INSERT INTO LineTimetable (line_id, departure_time) VALUES "
		                + "(1, '12:00'), (1, '13:00'), (1, '14:00'), (1, '15:00'), (1, '16:00'), (1, '17:00'), (1, '18:00'),"
		                + "(2, '9:00'), (2, '9:30'), (2, '10:00'), (2, '10:30'), (2, '11:00'), (2, '11:30'), (2, '12:00'),"
		                + "(3, '17:00'), (3, '18:00'), (3, '19:00'), (3, '20:00'), (3, '21:00'), (3, '22:00'), (3, '23:00'),"
		                + "(4, '5:00'), (4, '8:00'), (4, '11:00'), (4, '14:00'), (4, '17:00'), (4, '20:00'), (4, '23:00');",
		};

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/oasaripoff",
		        "root", "localhostMVCMy$QL")) {
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
