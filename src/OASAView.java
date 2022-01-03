package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import controller.IController;
import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

/**
 * One Concrete View that extends the {@link AbstractView}. More Concrete Views
 * can be constructed to illustrate different ways to use the {@link IView}
 * interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class OASAView extends AbstractView {
	
	private static final Font textFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);

	/**
	 * TODO
	 */
	public OASAView(IController c) {
		super(new JPanel(), new OASAEntityGraphicFactory(new ImageLoader(c)));
	}
	
	public OASAView() {
		this(null);
	}
	
	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JLabel text = new JLabel("Showing all towns:");
		text.setFont(textFont);
		mainPanel.add(text);
		
		JScrollPane scrollPanel = getScrolledPanel(new Dimension(WINDOW_WIDTH *4/5, WINDOW_HEIGHT/3));
		for(var town : towns)
			scrollPanel.add(factory.getETownGraphic(town));
		mainPanel.add(scrollPanel);
		
		super.updatePanel(mainPanel);
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		JPanel mainPanel = new JPanel();
		
		//add optional graphic for station?
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JLabel text = new JLabel("Available Lines:");
		text.setFont(textFont);
		mainPanel.add(text);		
		
		JScrollPane scrollPanel = getScrolledPanel(new Dimension(WINDOW_WIDTH *4/5, WINDOW_HEIGHT/3));
		for(var line : lines)
			scrollPanel.add(factory.getELineGraphic(line));
		mainPanel.add(scrollPanel);
		
		super.updatePanel(mainPanel);
	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		JPanel mainPanel = new JPanel();
		
		//add optional graphic for line?
		
		JLabel text = new JLabel("Available Stations:");
		text.setFont(textFont);
		mainPanel.add(text);
		
		JScrollPane scrollPanel = getScrolledPanel(new Dimension(WINDOW_WIDTH *4/5, WINDOW_HEIGHT/3));
		for(var station : stations)
			scrollPanel.add(factory.getEStationGraphic(station));
		mainPanel.add(scrollPanel);
		
		super.updatePanel(mainPanel);
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		JPanel mainPanel = new JPanel();
		
		//add optional graphic for line/station?
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JLabel text = new JLabel("TimeTables to Arrival:");
		text.setFont(textFont);
		mainPanel.add(text);
		
		JScrollPane scrollPanel = getScrolledPanel(new Dimension(WINDOW_WIDTH *4/5, WINDOW_HEIGHT/3));
		for(var timetable : timetables)
			scrollPanel.add(factory.getETimetableGraphic(timetable));
		mainPanel.add(scrollPanel);
		
		super.updatePanel(mainPanel);
	}

	@Override
	public void updateViewWithError(Exception e) {
		JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void updateViewWithHomepage() {
		JPanel home = new JPanel();
		home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));
		
		JPanel imagePanel = new JPanel();
		imagePanel.add(new JLabel(new ImageIcon(controller.loadImage("oasa_home.jpg", WINDOW_HEIGHT, WINDOW_WIDTH))));
		home.add(imagePanel);
		home.add(Box.createVerticalStrut(15));
		
		JPanel buttonWrapperPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,1));
		
		Dimension buttonDimensions = new Dimension(WINDOW_WIDTH*4/5, 100);
		buttonPanel.setPreferredSize(buttonDimensions);
		
		JButton button1 = new JButton("Find Routes and Lines");
		button1.setSize(buttonDimensions);
		buttonPanel.add(button1);
		JButton button2 = new JButton("Search Towns and Stations");
		button2.setSize(buttonDimensions);
		buttonPanel.add(button2);
		JButton button3 = new JButton("Find Lines by Vehicle Type");
		button3.setSize(buttonDimensions);
		buttonPanel.add(button3);
		
		buttonWrapperPanel.add(buttonPanel);
		
		home.add(buttonWrapperPanel);
		home.add(Box.createVerticalStrut(15));
		
		super.updatePanel(home);
	}
	
	private static JScrollPane getScrolledPanel(Dimension size) {
		JPanel mainPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(mainPanel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(size);
		
		return scrollPane;
		
	}
}
