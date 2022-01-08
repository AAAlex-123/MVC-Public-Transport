package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import localisation.Languages;
import requirement.util.Requirements;

/**
 * A concrete implementation of the {@link IView} interface that extends the
 * {@link AbstractView}. More Concrete Views can be implemented to illustrate
 * different ways to use the {@link IView} interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class OASAView extends AbstractView {

	private static final Font textFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);

	/**
	 * Constructs a concrete OASAView and provides an instance of
	 * {@link OASAEntityGraphicFactory} to the abstract super class.
	 */
	public OASAView() {
		super(new OASAEntityGraphicFactory());
	}

	@Override
	protected JMenuBar constructJMenuBar() {
		final JMenuBar menuBar = new JMenuBar();

		final JMenu     m_insert, m_preferences;
		final JMenuItem home, prev, next, i_town, i_station, i_line, i_station_to_line,
		        i_timetable_to_line, p_settings, p_language;

		home = new JMenuItem("<HOME_ICON>"); //$NON-NLS-1$
		prev = new JMenuItem("<PREV_ICON>"); //$NON-NLS-1$
		next = new JMenuItem("<NEXT_ICON>"); //$NON-NLS-1$

		m_insert = new JMenu(Languages.getString("OASAView.3")); //$NON-NLS-1$
		i_town = new JMenuItem(Languages.getString("OASAView.4")); //$NON-NLS-1$
		i_station = new JMenuItem(Languages.getString("OASAView.5")); //$NON-NLS-1$
		i_line = new JMenuItem(Languages.getString("OASAView.6")); //$NON-NLS-1$
		i_station_to_line = new JMenuItem(Languages.getString("OASAView.7")); //$NON-NLS-1$
		i_timetable_to_line = new JMenuItem(Languages.getString("OASAView.8")); //$NON-NLS-1$
		m_insert.add(i_town);
		m_insert.add(i_station);
		m_insert.add(i_line);
		m_insert.add(i_station_to_line);
		m_insert.add(i_timetable_to_line);

		m_preferences = new JMenu(Languages.getString("OASAView.9")); //$NON-NLS-1$
		p_settings = new JMenuItem(Languages.getString("OASAView.10")); //$NON-NLS-1$
		p_language = new JMenuItem(Languages.getString("OASAView.11")); //$NON-NLS-1$
		m_preferences.add(p_settings);
		m_preferences.add(p_language);

		menuBar.add(home);
		menuBar.add(prev);
		menuBar.add(next);
		menuBar.add(m_insert);
		menuBar.add(m_preferences);


		home.addActionListener(e -> OASAView.this.changeToHomePanel());
		prev.addActionListener(e -> OASAView.this.changeToPreviousPanel());
		next.addActionListener(e -> OASAView.this.changeToNextPanel());

		i_town.addActionListener(e -> OASAView.this.insertTown());
		i_station.addActionListener(e -> OASAView.this.insertStation());
		i_line.addActionListener(e -> OASAView.this.insertLine());
		i_station_to_line.addActionListener(e -> OASAView.this.insertStationToLine());
		i_timetable_to_line.addActionListener(e -> OASAView.this.insertTimetableToLine());


		return menuBar;
	}

	@Override
	protected void fulfilRequirements(Requirements reqs, String prompt) {
		reqs.fulfillWithDialog(this, prompt);
	}

	@Override
	public void updateViewWithHomepage() {
		final JPanel home = new JPanel();
		home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));

		final JPanel imagePanel = new JPanel();
		imagePanel.add(new JLabel(super.getImageIcon("oasa_home.jpg"))); //$NON-NLS-1$

		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 30, 30));

		final JButton button1 = new JButton(Languages.getString("OASAView.12")); //$NON-NLS-1$
		final JButton button2 = new JButton(Languages.getString("OASAView.13")); //$NON-NLS-1$

		button1.addActionListener(e -> OASAView.super.getAllLines());
		button2.addActionListener(e -> OASAView.super.getAllTowns());

		buttonPanel.add(button1);
		buttonPanel.add(button2);

		home.add(imagePanel);
		home.add(Box.createVerticalStrut(15));
		home.add(buttonPanel);

		super.updatePanel(home);
	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel townPanel = OASAView.getDisplayPanel();
		for (final ETown town : towns)
			townPanel.add(factory.getETownGraphic(town));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.14")), BorderLayout.NORTH); //$NON-NLS-1$
		contentPanel.add(OASAView.getJSPForPanel(townPanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel linePanel = OASAView.getDisplayPanel();
		for (final ELine line : lines)
			linePanel.add(factory.getELineGraphic(line));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.15")), BorderLayout.NORTH); //$NON-NLS-1$
		contentPanel.add(OASAView.getJSPForPanel(linePanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel stationPanel = OASAView.getDisplayPanel();
		for (final EStation station : stations)
			stationPanel.add(factory.getEStationGraphic(station));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.16")), BorderLayout.NORTH); //$NON-NLS-1$
		contentPanel.add(OASAView.getJSPForPanel(stationPanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel timetablePanel = OASAView.getDisplayPanel();
		for (final ETimetable timetable : timetables)
			timetablePanel.add(factory.getETimetableGraphic(timetable));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.17")), BorderLayout.NORTH); //$NON-NLS-1$
		contentPanel.add(OASAView.getJSPForPanel(timetablePanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithError(Exception e) {
		JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), Languages.getString("OASAView.18"), //$NON-NLS-1$
		        JOptionPane.ERROR_MESSAGE);
	}

	// ---------- static factory methods ----------

	private static JPanel getContentPanel() {
		return new JPanel(new BorderLayout());
	}

	private static JPanel getDisplayPanel() {
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		return panel;
	}

	private static JLabel getCenteredLabel(String text) {
		final JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setFont(OASAView.textFont);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		return label;
	}

	private static JScrollPane getJSPForPanel(JPanel panel) {
		final JScrollPane jsp = new JScrollPane(panel);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp.setBorder(BorderFactory.createEmptyBorder());
		jsp.setWheelScrollingEnabled(true);

		return jsp;
	}
}
