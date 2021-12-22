package entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Test class to check database functionality
 *
 * @author Alex Mandelias
 */
@SuppressWarnings("nls")
public class Main {

	private static final String URL  = "jdbc:mysql://localhost/pets";
	private static final String USER = "***";
	private static final String PASS = "***";

	private static final String QUERY = "SELECT * FROM Cats";

	/**
	 * Runs the program that checks database functionality.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection(Main.URL, Main.USER, Main.PASS);
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(Main.QUERY);) {
			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				System.out.print("ID: " + rs.getInt("id"));
				System.out.print(", name: " + rs.getString("name"));
				System.out.print(", owner: " + rs.getString("owner"));
				System.out.println(", birth: " + rs.getDate("birth"));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}
}
