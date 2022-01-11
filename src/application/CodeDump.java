package application;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.Controller;
import entity.ELine;
import entity.ETown;
import model.IModel;
import requirement.requirements.StringType;
import view.IView;

@SuppressWarnings({ "nls", "javadoc", "null", "resource", "unused", "static-method", "hiding" })
abstract class CodeDump {

	// OASAView.java
	public void updateViewWithTowns(List<ETown> towns) {
		final JPanel contentPanel = getContentPanel();

		final JPanel townPanel = getDisplayPanel();
		for (final ETown town : towns)
			townPanel.add(factory.getETownGraphic(town));

		contentPanel.add(getCenteredLabel("Towns"), BorderLayout.NORTH);
		contentPanel.add(getJSPForPanel(townPanel), BorderLayout.CENTER);

		this.contentPanel = contentPanel;
	}

	protected JMenuBar constructJMenuBar() {
		final JMenuBar menuBar = new JMenuBar();

		final JMenu     m_insert;
		final JMenuItem home, i_town;

		home = new JMenuItem("Home");

		m_insert = new JMenu("Insert");
		i_town = new JMenuItem("Town");
		m_insert.add(i_town);
		// ... other JMenu Items

		menuBar.add(home);
		menuBar.add(m_insert);
		// ... other JMenus

		home.addActionListener(e -> OASAView.super.changeToHomePanel());
		i_town.addActionListener(e -> OASAView.super.insertTown());
		// ... other JButtons

		return menuBar;
	}

	// AbstractView.java
	protected void AVgetAllTowns() {
		controller.CgetAllTowns();
	}

	protected final void AVinsertTown() {
		Parameters params = controller.CgetInsertTownRequirements();
		AVfulfillParameters(params, "Insert Town Parameters");
		if (params.fulfilled())
			controller.CinsertTown(params);
	}

	// maybe not include this ???
	protected abstract void AVfulfillParameters(Parameters params, String prompt);

	// Controller.java
	public void CgetTowns(ELine line) {
		try {
			final List<ETown> towns = model.getTowns(line);
			view.updateViewWithTowns(towns);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	public void CinsertTown(Parameters params) {
		final ETown newTown = new ETown(-1, params.getValue("Name", String.class));
		try {
			model.insertTown(newTown);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	public Parameters CgetInsertTownRequirements() {
		final Parameters params = new Parameters();
		params.add("name", StringType.NON_EMPTY);
		return params;
	}

	// Model.java
	public List<ETown> MgetTowns(ELine line) throws SQLException {
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
			final String id = String.valueOf(line.getId());
			qFinalisedQuery = qSelectTownsByLine.replaceAll("@1", id)
			        + " ORDER BY C.name;";
		}

		final List<ETown> townsFromDatabase = new LinkedList<>();

		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			rs = stmt.executeQuery(qFinalisedQuery);
			while (rs.next())
				townsFromDatabase.add(new ETown(rs.getInt("C.id"), rs.getString("C.name")));

		} finally {
			rs.close(); stmt.close(); conn.close();
		}

		return townsFromDatabase;
	}

	public void MinsertTown(ETown town) throws SQLException {

		final String qInsertToCity = "INSERT INTO City(name) VALUES ('@2')";

		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			stmt.execute(qInsertToCity.replace("@2", town.getName()));

		} finally {
			rs.close(); stmt.close(); conn.close();
		}
	}

	// AbstractEntity.java
	abstract class AbstractEntity {

		private final int id;

		protected AbstractEntity(int id) {
			this.id = id;
		}

		public final int getId() {
			return id;
		}
	}

	// ETown.java
	public class Town extends AbstractEntity {

		private final String name;

		public Town(int id, String name) {
			super(id);
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private String url, user, password;
	private IModel model;
	private IView       view;
	private MyController controller = new MyController(model, view);

	private JPanel  contentPanel;
	private Factory factory;

	private static JPanel getContentPanel() { return null; }
	private static JPanel getDisplayPanel() { return null; }
	private static JLabel getCenteredLabel(String text) { return null; }
	private static JScrollPane getJSPForPanel(JPanel p) { return null; }

	private class Factory {
		public JPanel getETownGraphic(ETown town) { return null; }
	}

	private static class MyController extends Controller {
		public MyController(IModel model, IView view) {
			super(model, view);
		}

		private void CinsertTown(Parameters params) {}

		private Parameters CgetInsertTownRequirements() { return null; }

		private void CgetAllTowns() {}
	}

	private static class Parameters {
		private <T> T getValue(String key, Class<T> clazz) { return null; }

		private void add(String key, StringType type) {}

		private boolean fulfilled() { return false; }
	}

}
