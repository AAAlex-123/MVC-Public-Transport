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
import entity.ETimestamp;
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

	private static final int WINDOW_HEIGHT = 850;
	private static final int WINDOW_WIDTH  = 650;

	private static final Font globalFont = new Font(Font.SANS_SERIF, Font.PLAIN, 22);
	private static final Font labelFont  = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	private static final Font footerFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);

	private JPanel cachedHomepage;

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
		        i_timetable_to_line, /* p_settings, */ p_language;

		home = new JMenuItem(Languages.getString("OASAView.0")); //$NON-NLS-1$
		prev = new JMenuItem(Languages.getString("OASAView.1")); //$NON-NLS-1$
		next = new JMenuItem(Languages.getString("OASAView.2")); //$NON-NLS-1$

		home.setFont(globalFont);
		prev.setFont(globalFont);
		next.setFont(globalFont);

		m_insert = new JMenu(Languages.getString("OASAView.3"));  //$NON-NLS-1$
		i_town = new JMenuItem(Languages.getString("OASAView.4"));  //$NON-NLS-1$
		i_station = new JMenuItem(Languages.getString("OASAView.5"));  //$NON-NLS-1$
		i_line = new JMenuItem(Languages.getString("OASAView.6"));  //$NON-NLS-1$
		i_station_to_line = new JMenuItem(Languages.getString("OASAView.7"));  //$NON-NLS-1$
		i_timetable_to_line = new JMenuItem(Languages.getString("OASAView.8"));  //$NON-NLS-1$

		m_insert.setFont(globalFont);
		i_town.setFont(globalFont);
		i_station.setFont(globalFont);
		i_line.setFont(globalFont);
		i_station_to_line.setFont(globalFont);
		i_timetable_to_line.setFont(globalFont);

		m_insert.add(i_town);
		m_insert.add(i_station);
		m_insert.add(i_line);
		m_insert.add(i_station_to_line);
		m_insert.add(i_timetable_to_line);

		m_preferences = new JMenu(Languages.getString("OASAView.9"));  //$NON-NLS-1$
		// p_settings = new JMenuItem(Languages.getString("OASAView.10")); //$NON-NLS-1$
		p_language = new JMenuItem(Languages.getString("OASAView.10"));  //$NON-NLS-1$
		// m_preferences.add(p_settings);
		m_preferences.add(p_language);

		m_preferences.setFont(globalFont);
		// p_settings.setFont(globalFont);
		p_language.setFont(globalFont);

		menuBar.add(home);
		menuBar.add(prev);
		menuBar.add(next);
		menuBar.add(m_insert);
		menuBar.add(m_preferences);


		home.addActionListener(e -> OASAView.super.changeToHomePanel());
		prev.addActionListener(e -> OASAView.super.changeToPreviousPanel());
		next.addActionListener(e -> OASAView.super.changeToNextPanel());

		i_town.addActionListener(e -> OASAView.super.insertTown());
		i_station.addActionListener(e -> OASAView.super.insertStation());
		i_line.addActionListener(e -> OASAView.super.insertLine());
		i_station_to_line.addActionListener(e -> OASAView.super.insertStationToLine());
		i_timetable_to_line.addActionListener(e -> OASAView.super.insertTimetableToLine());

		p_language.addActionListener(e -> OASAView.this.changeLanguage());

		home.setIcon(getImageIcon("home_button.jpg", 25, 25));  //$NON-NLS-1$
		prev.setIcon(getImageIcon("prev_button_small.png", 25, 25));  //$NON-NLS-1$
		next.setIcon(getImageIcon("next_button_small.png", 25, 25));  //$NON-NLS-1$
		i_town.setIcon(getImageIcon("town.png", 18, 18));  //$NON-NLS-1$
		i_station.setIcon(getImageIcon("station.png", 18, 18));  //$NON-NLS-1$
		i_line.setIcon(getImageIcon("bus.png", 18, 18));  //$NON-NLS-1$
		p_language.setIcon(getImageIcon("language.png", 18, 18));  //$NON-NLS-1$

		return menuBar;
	}

	@Override
	protected void changeLanguage() {
		final String file = Languages.FILE;

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

		JLabel phoneLabel = getCenteredLabel(Languages.getString("OASAView.18"));  //$NON-NLS-1$
		phoneLabel.setFont(footerFont);
		JLabel emailLabel = getCenteredLabel(Languages.getString("OASAView.19"));  //$NON-NLS-1$
		emailLabel.setFont(footerFont);

		infoPanel.add(phoneLabel);
		infoPanel.add(emailLabel);

		return infoPanel;
	}

	@Override
	public void updateViewWithHomepage() {
		if (cachedHomepage == null) {

			final JPanel contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

			final JPanel imagePanel = new JPanel();
			imagePanel.add(new JLabel(super.getImageIcon("oasa_home.jpg")));  //$NON-NLS-1$

			final JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));
			buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 30, 30));

			final JButton button1 = new JButton(Languages.getString("OASAView.21"));  //$NON-NLS-1$
			final JButton button2 = new JButton(Languages.getString("OASAView.22"));  //$NON-NLS-1$

			button1.setFont(globalFont);
			button2.setFont(globalFont);

			button1.addActionListener(e -> OASAView.super.getAllLines());
			button2.addActionListener(e -> OASAView.super.getAllTowns());

			buttonPanel.add(button1);
			buttonPanel.add(button2);

			contentPanel.add(imagePanel);
			contentPanel.add(Box.createVerticalStrut(15));
			contentPanel.add(buttonPanel);

			cachedHomepage = contentPanel;
		}

		super.updatePanel(cachedHomepage);
	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel townPanel = OASAView.getDisplayPanel();
		for (final ETown town : towns)
			townPanel.add(factory.getETownRepresentation(town));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.23")),  //$NON-NLS-1$
		        BorderLayout.NORTH);
		contentPanel.add(OASAView.getJSPForPanel(townPanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel linePanel = OASAView.getDisplayPanel();
		for (final ELine line : lines)
			linePanel.add(factory.getELineRepresentation(line));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.24")),  //$NON-NLS-1$
		        BorderLayout.NORTH);
		contentPanel.add(OASAView.getJSPForPanel(linePanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel stationPanel = OASAView.getDisplayPanel();
		for (final EStation station : stations)
			stationPanel.add(factory.getEStationRepresentation(station));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.25")),  //$NON-NLS-1$
		        BorderLayout.NORTH);
		contentPanel.add(OASAView.getJSPForPanel(stationPanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithTimestamps(List<ETimestamp> timestamps) {
		final JPanel contentPanel = OASAView.getContentPanel();

		final JPanel timestampPanel = OASAView.getDisplayPanel();
		for (final ETimestamp timestamp : timestamps)
			timestampPanel.add(factory.getETimestampRepresentation(timestamp));

		contentPanel.add(OASAView.getCenteredLabel(Languages.getString("OASAView.26")),  //$NON-NLS-1$
		        BorderLayout.NORTH);
		contentPanel.add(OASAView.getJSPForPanel(timestampPanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithError(Exception e) {
		JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(),
		        Languages.getString("OASAView.27"),  //$NON-NLS-1$
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
		label.setFont(OASAView.labelFont);
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
			messageString = Languages.getString("OASAView.28");  //$NON-NLS-1$
			titleString = Languages.getString("OASAView.29");  //$NON-NLS-1$
			messageType = JOptionPane.INFORMATION_MESSAGE;

		} else {
			messageString = Languages.getString("OASAView.30");  //$NON-NLS-1$
			titleString = Languages.getString("OASAView.31");  //$NON-NLS-1$
			messageType = JOptionPane.ERROR_MESSAGE;
		}

		JOptionPane.showMessageDialog(frame, String.format(messageString, file),
		        titleString, messageType);
	}
}
