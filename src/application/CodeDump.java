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
import localisation.Languages;
import model.IModel;
import requirement.requirements.StringType;
import view.IView;

@SuppressWarnings({ "nls", "javadoc", "null", "resource", "unused", "static-method", "hiding" })
abstract class CodeDump {

	public void updateViewWithTowns(List<ETown> towns) {
		if (towns.isEmpty())
			goToHomepage(TOWNS);

		printSource();

		final FormatBuffer buffer = new FormatBuffer("%s %s: %n", PLEASE_SELECT, //$NON-NLS-1$
		        A_TOWN);

		for (final ETown town : towns)
			buffer.format(factory.getETownRepresentation(town));

		out.print(buffer);

		final ETown selected = towns.get(getAnswer(1, towns.size()) - 1);

		sourceEntityDescription = factory.getETownRepresentation(selected);

		printMenu(new String[] {
		        String.format(Languages.getString("ConsoleView.6"), SHOW_ALL), //$NON-NLS-1$
		        String.format(Languages.getString("ConsoleView.7"), SHOW_ALL), //$NON-NLS-1$
		        RETURN_TO_HOME,
		}, String.format("%s %s: %s%n", SELECT_ACCESS, //$NON-NLS-1$
		        THE_TOWN, "%s"), selected.getName()); //$NON-NLS-1$

		doWithAnswer(() -> super.getLinesByTown(selected), () -> super.getStationsByTown(selected),
		        this::updateViewWithHomepage);
	}

	// OASAView.java
	public void updateViewWithTowns(List<ETown> towns) {
		final JPanel contentPanel = getContentPanel();

		final JPanel townPanel = getDisplayPanel();
		for (final ETown town : towns)
			townPanel.add(factory.getETownRepresentation(town));

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
	protected void getAllTowns() {
		controller.getAllTowns();
	}

	protected final void insertTown() {
		Requirements params = controller.getInsertTownRequirements();
		fulfillRequirements(params, "Insert Town Parameters");
		if (params.fulfilled())
			controller.insertTown(params);
	}

	// maybe not include this ???
	protected abstract void fulfillRequirements(Requirements params, String prompt);

	// Controller.java
	public void getTowns(ELine line) {
		try {
			final List<ETown> towns = model.getTowns(line);
			view.updateViewWithTowns(towns);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	public void insertTown(Requirements params) {
		final ETown newTown = new ETown(-1, params.getValue("Name", String.class));
		try {
			model.insertTown(newTown);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	public Requirements getInsertTownRequirements() {
		final Requirements params = new Requirements();
		params.add("Name", StringType.NON_EMPTY);
		return params;
	}

	// Model.java
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

	public void insertTown(ETown town) throws SQLException {

		final String qInsertToCity = "INSERT INTO City(name) VALUES ('@2')";

		Connection conn = null;
		Statement  stmt = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			stmt.execute(qInsertToCity.replace("@2", town.getName()));

		} finally {
			stmt.close();
			conn.close();
		}
	}

	public abstract class AbstractEntity {}

	// ETown.java
	public class Town extends AbstractEntity {

		private final int id;
		private final String name;

		public Town(int id, String name) {
			this.id = id;
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
		public <T> T getETownRepresentation(ETown town) {
			return null;
		}
	}

	private static class MyController extends Controller {
		public MyController(IModel model, IView view) {
			super(model, view);
		}

		private void insertTown(Requirements params) {}

		@Override
		private Requirements getInsertTownRequirements() { return null; }

		@Override
		private void getAllTowns() {}
	}

	private static class Requirements {
		private <T> T getValue(String key, Class<T> clazz) { return null; }

		private void add(String key, StringType type) {}

		private boolean fulfilled() { return false; }
	}

	private static class FormatBuffer {

		private final StringBuffer buffer;
		private int                count;

		public FormatBuffer() {
			this("");
		}

		public FormatBuffer(String initial) {
			buffer = new StringBuffer(System.lineSeparator() + initial);
			count = 1;
		}

		public FormatBuffer(String format, Object... args) {
			this(String.format(format, args));
		}

		public void format(String format, Object... args) {
			final String preparedString = String.format("%d: %s%n", count, format); //$NON-NLS-1$
			buffer.append(String.format(preparedString, args));
			count++;
		}

		@Override
		public String toString() {
			return buffer.toString();
		}
	}

}
