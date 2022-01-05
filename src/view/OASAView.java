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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

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
	public void updateViewWithHomepage() {
		JPanel home = new JPanel();
		home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));

		JPanel imagePanel = new JPanel();
		imagePanel.add(new JLabel(super.getImageIcon("oasa_home.jpg")));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 30, 30));

		JButton button1 = new JButton("Search for Lines");
		JButton button2 = new JButton("Search for Towns");

		button1.addActionListener((e) -> OASAView.super.getAllLines());
		button2.addActionListener((e) -> OASAView.super.getAllTowns());

		buttonPanel.add(button1);
		buttonPanel.add(button2);

		home.add(imagePanel);
		home.add(Box.createVerticalStrut(15));
		home.add(buttonPanel);

		super.updatePanel(home);
	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		JPanel contentPanel = getContentPanel();

		JPanel townPanel = getDisplayPanel();
		for (ETown town : towns)
			townPanel.add(factory.getETownGraphic(town));

		contentPanel.add(getCenteredLabel("Towns"), BorderLayout.NORTH);
		contentPanel.add(getJSPForPanel(townPanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		JPanel contentPanel = getContentPanel();

		JPanel linePanel = getDisplayPanel();
		for (ELine line : lines)
			linePanel.add(factory.getELineGraphic(line));

		contentPanel.add(getCenteredLabel("Lines"), BorderLayout.NORTH);
		contentPanel.add(getJSPForPanel(linePanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		JPanel contentPanel = getContentPanel();

		JPanel stationPanel = getDisplayPanel();
		for (EStation station : stations)
			stationPanel.add(factory.getEStationGraphic(station));

		contentPanel.add(getCenteredLabel("Stations"), BorderLayout.NORTH);
		contentPanel.add(getJSPForPanel(stationPanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		JPanel contentPanel = getContentPanel();

		JPanel timetablePanel = getDisplayPanel();
		for (ETimetable timetable : timetables)
			timetablePanel.add(factory.getETimetableGraphic(timetable));

		contentPanel.add(getCenteredLabel("Departures"), BorderLayout.NORTH);
		contentPanel.add(getJSPForPanel(timetablePanel), BorderLayout.CENTER);

		super.updatePanel(contentPanel);
	}

	@Override
	public void updateViewWithError(Exception e) {
		JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	// ---------- static factory methods ----------

	private static JPanel getContentPanel() {
		return new JPanel(new BorderLayout());
	}

	private static JPanel getDisplayPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		return panel;
	}

	private static JLabel getCenteredLabel(String text) {
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setFont(textFont);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		return label;
	}

	private static JScrollPane getJSPForPanel(JPanel panel) {
		JScrollPane jsp = new JScrollPane(panel);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp.setBorder(BorderFactory.createEmptyBorder());
		jsp.setWheelScrollingEnabled(true);

		return jsp;
	}
}
