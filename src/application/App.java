package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import controller.CLocalImageController;
import controller.Controller;
import controller.IController;
import controller.IImageController;
import entity.Coordinates;
import entity.EStation;
import localisation.Languages;
import model.IImageModel;
import model.IModel;
import model.MLocalImageModel;
import model.Model;
import view.IView;
import view.MapView;

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
		boolean oasa, console, reset = false;
		String  ip = "localhost";            //$NON-NLS-1$

		if (args.length == 0) {
			fail(String.format(Languages.getString("App.3"), USE_COMMAND), OASA, CONSOLE); //$NON-NLS-1$
			System.exit(1);
		}

		oasa = args[0].equals(OASA);
		console = args[0].equals(CONSOLE);
		if (oasa == console)
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

		// IView view = oasa ? new OASAView(imageController) : new ConsoleView();
		IView view = new MapView(imageController);

		IModel model;
		if (ip.equals("localhost")) //$NON-NLS-1$
			model = Model.forRoot();
		else
			model = Model.forHost(ip, "cant_drop_db", "pass"); //$NON-NLS-1$ //$NON-NLS-2$

		IController controller = new Controller(model, view);

		view.registerController(controller);

		view.start();

		double[][] poss1 = {
		        { 37.96813736609794, 23.909875351189413 },
		        { 37.9624440657025, 23.92416521532049 },
		};

		double[][] poss2 = {
		        { 37.962013754831936, 23.914131124507147 },
		        { 37.959483801430416, 23.9208231190715 },
		};

		double[][] poss3 = {
				{37.96648720107333, 23.908642493664612},
				{37.95880806712703, 23.906399341828426},
				{37.96817805399748, 23.90598612964807},
		};

		double[][] USE = poss3;

		List<EStation> stations = new LinkedList<>();
		for (double[] pos : USE) {
			stations.add(new EStation(-1, null, new Coordinates(pos[0], pos[1]), null));
		}

		view.updateViewWithStations(stations);
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
		        "CREATE TABLE IF NOT EXISTS City ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(31));",
		        "CREATE TABLE IF NOT EXISTS Station ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(63), latitude DOUBLE, longitude DOUBLE, city_id INT, FOREIGN KEY (city_id) REFERENCES City (id));",
		        "CREATE TABLE IF NOT EXISTS Line ( id INT AUTO_INCREMENT PRIMARY KEY, lineNo VARCHAR(7), description VARCHAR(255), type VARCHAR(15));",
		        "CREATE TABLE IF NOT EXISTS LineStation ( line_id INT, station_id INT, station_index INT, PRIMARY KEY (line_id, station_id), FOREIGN KEY (line_id) REFERENCES Line(id), FOREIGN KEY (station_id) REFERENCES Station(id));",
		        "CREATE TABLE IF NOT EXISTS LineTimetable ( line_id INT, departure_time TIME, PRIMARY KEY (line_id, departure_time), FOREIGN KEY (line_id) REFERENCES Line(id));",
		};

		final String[] qInsertIntoTables = {
		        "INSERT INTO City (name) VALUES ('spata'), ('agia paraskevi'), ('nea filadelfia'), ('metamorfwsh'), ('athina');",

		        "INSERT INTO Station (name, latitude, longitude, city_id) VALUES "
		                + "('spata', 18, 5, 1),"
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
		                + "('305', 'spata-nomismatokopeio', 'BUS'),"
		                + "('421', 'agia paraskevi-nea filadelfia', 'BUS'),"
		                + "('B9', 'metamorfwsi-athina', 'BUS'),"
		                + "('3', 'aerodromio-syntagma', 'SUBWAY');",

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
