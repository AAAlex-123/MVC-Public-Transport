package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.IOException;
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

import controller.IImageController;
import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import localisation.Languages;
import requirement.util.Requirements;

/**
 * A concrete implementation of the {@link IView} interface that extends the
 * {@link AbstractGUIView}. It provides the users with a Graphical Interface
 * with which to use the application.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class OASAView extends AbstractGUIView {

	private static final int WINDOW_HEIGHT = 750;
	private static final int WINDOW_WIDTH  = 500;

	private static final Font textFont   = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	private static final Font footerFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);

	/**
	 * Constructs a concrete OASAView and provides an instance of
	 * {@link OASAEntityRepresentationFactory} to the abstract super class.
	 *
	 * @param imageController this View's Image Controller
	 */
	public OASAView(IImageController imageController) {
		super(new OASAEntityRepresentationFactory<OASAView>(), imageController, WINDOW_WIDTH,
		        WINDOW_HEIGHT);

		((OASAEntityRepresentationFactory<AbstractGUIView>) factory).initializeView(this);
	}

	@Override
	protected JMenuBar constructJMenuBar() {
		final JMenuBar menuBar = new JMenuBar();

		final JMenu     m_insert, m_preferences;
		final JMenuItem home, prev, next, i_town, i_station, i_line, i_station_to_line,
		        i_timetable_to_line, p_settings, p_language;

		home = new JMenuItem(getImageIcon("home_button.jpg", 54, 54)); //$NON-NLS-1$
		prev = new JMenuItem(getImageIcon("prev_button_small.png", 54, 54)); //$NON-NLS-1$
		next = new JMenuItem(getImageIcon("next_button_small.png", 54, 54)); //$NON-NLS-1$

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

		p_language.addActionListener(e -> OASAView.this.changeLanguage());


		return menuBar;
	}

	@Override
	protected void changeLanguage() {
		final String file  = Languages.FILE;

		try {
			final boolean languageChanged = Languages.editAndWriteToFile(frame);
			if (languageChanged)
				OASAView.message(frame, file, null);
		} catch (final IOException e) {
			OASAView.message(frame, file, e);
		}
	}

	@Override
	protected void fulfilRequirements(Requirements reqs, String prompt) {
		reqs.fulfillWithDialog(frame, prompt);
	}

	@Override
	protected JPanel constructFooter() {

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(0, 1, 10, 0));
		infoPanel.setBackground(Color.CYAN);
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		infoPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT / 15));

		JLabel phoneLabel = getCenteredLabel(Languages.getString("OASAView.0")); //$NON-NLS-1$
		phoneLabel.setFont(footerFont);
		JLabel emailLabel = getCenteredLabel(Languages.getString("OASAView.1")); //$NON-NLS-1$
		emailLabel.setFont(footerFont);

		infoPanel.add(phoneLabel);
		infoPanel.add(emailLabel);

		return infoPanel;
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

		final JButton button1 = new JButton(Languages.getString("OASAView.13")); //$NON-NLS-1$
		final JButton button2 = new JButton(Languages.getString("OASAView.14")); //$NON-NLS-1$

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

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.15")), //$NON-NLS-1$
		        BorderLayout.NORTH);
		contentPanel.add(OASAView.getJSPForPanel(townPanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel linePanel = OASAView.getDisplayPanel();
		for (final ELine line : lines)
			linePanel.add(factory.getELineGraphic(line));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.16")), //$NON-NLS-1$
		        BorderLayout.NORTH);
		contentPanel.add(OASAView.getJSPForPanel(linePanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel stationPanel = OASAView.getDisplayPanel();
		for (final EStation station : stations)
			stationPanel.add(factory.getEStationGraphic(station));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.17")), //$NON-NLS-1$
		        BorderLayout.NORTH);
		contentPanel.add(OASAView.getJSPForPanel(stationPanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel timetablePanel = OASAView.getDisplayPanel();
		for (final ETimetable timetable : timetables)
			timetablePanel.add(factory.getETimetableGraphic(timetable));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.18")), //$NON-NLS-1$
		        BorderLayout.NORTH);
		contentPanel.add(OASAView.getJSPForPanel(timetablePanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithError(Exception e) {
		JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(),
		        Languages.getString("OASAView.19"), //$NON-NLS-1$
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

	private static void message(Frame frame, String file, Exception e) {
		final String messageString, titleString;
		final int    messageType;

		if (e == null) {
			messageString = Languages.getString("OASAView.20"); //$NON-NLS-1$
			titleString = Languages.getString("OASAView.21"); //$NON-NLS-1$
			messageType = JOptionPane.INFORMATION_MESSAGE;

		} else {
			messageString = Languages.getString("OASAView.22"); //$NON-NLS-1$
			titleString = Languages.getString("OASAView.23"); //$NON-NLS-1$
			messageType = JOptionPane.ERROR_MESSAGE;
		}

		JOptionPane.showMessageDialog(frame, String.format(messageString, file),
		        titleString, messageType);
	}
}
