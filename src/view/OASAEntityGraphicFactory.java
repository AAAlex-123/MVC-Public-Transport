package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

/**
 * A Factory producing self-describing graphics which open pop-up windows to 
 * select operations.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
class OASAEntityGraphicFactory implements AbstractEntityGraphicFactory {
	
	private static final Font largeFont = new Font ("TimesRoman", Font.BOLD, 24);
	private static final Font standardFont = new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 18);
	private static final Font smallFont = new Font ("TimesRoman", Font.PLAIN, 12);
	private static final Color textColor = Color.BLACK;
	private static final Color backgroundColor = Color.WHITE;
	private static final int ICON_SIZE = 54;
	
	private static final String FIND_TOWNS = "Find towns";
	private static final String FIND_LINES = "Find lines";
	private static final String FIND_TIMES = "Find arrival times";
	private static final String FIND_STATIONS = "Find stations";
	
	private AbstractView view;	
	
	public OASAEntityGraphicFactory(AbstractView view) {
		this.view = view;
	}
	
	public OASAEntityGraphicFactory() {
		this.view = null;
	}
	
	@Override
	public void initializeView(AbstractView view) {
		if(this.view == null)
			this.view = view;
	}
			
	@Override
	public JPanel getETownGraphic(ETown town) {
		JPanel graphic = prepareGraphic();
		
		graphic.add(new JLabel(new ImageIcon(view.loadImage("town.png", ICON_SIZE, ICON_SIZE))));
		graphic.add(Box.createHorizontalBox());
		
		JLabel nameLabel = new JLabel(town.getName());
		nameLabel.setFont(standardFont);
		nameLabel.setForeground(textColor);
		nameLabel.setBackground(backgroundColor);
		
		graphic.add(nameLabel);
		
		graphic.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseClicked(MouseEvent e) {
                String   res     = (String) JOptionPane.showInputDialog(view.getContentPane(), 
                		"What do you wish to look for in the town of " + town.getName() + "?", "Town Options", 
                		JOptionPane.QUESTION_MESSAGE,
                		new ImageIcon(view.loadImage("town.png", ICON_SIZE, ICON_SIZE)), 
                		new String[]{ FIND_LINES, FIND_STATIONS}, FIND_LINES);
                
                switch (res) {
                case FIND_LINES:
                    view.getLinesByTown(town);
                    break;
                case FIND_STATIONS:
                    view.getStationsByTown(town);
                    break;
                }
			}
		});
		
		return graphic;
	}

	@Override
	public JPanel getELineGraphic(ELine line) {
		JPanel graphic = prepareGraphic();
		
		graphic.add(new JLabel(new ImageIcon(view.loadImage(line.getType().getSpriteName(), ICON_SIZE, ICON_SIZE))));
		graphic.add(Box.createHorizontalBox());
		
		JPanel namePanel = new JPanel();
		namePanel.setBackground(backgroundColor);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
		
		JLabel numLabel = new JLabel(line.getLineNumber());
		numLabel.setFont(largeFont);
		numLabel.setForeground(textColor);
		
		JLabel descrLabel = new JLabel(line.getName());
		descrLabel.setFont(standardFont);
		descrLabel.setForeground(textColor);
		
		namePanel.add(numLabel);
		namePanel.add(descrLabel);
		
		graphic.add(namePanel);
		
		graphic.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
                String   res     = (String) JOptionPane.showInputDialog(view.getContentPane(), 
                		"What do you wish to look for in the line" + line.getName() + "?", "Line Options", 
                		JOptionPane.QUESTION_MESSAGE,
                		new ImageIcon(view.loadImage("trolley.png", ICON_SIZE, ICON_SIZE)), 
                		new String[]{ FIND_TIMES, FIND_STATIONS, FIND_TOWNS}, FIND_TIMES);
                
                switch (res) {
                case FIND_TIMES:
                    view.getTimetablesByLine(line);
                    break;
                case FIND_STATIONS:
                    view.getStationsByLine(line);
                    break;
                case FIND_TOWNS:
                	view.getTownsByLine(line);
                	break;
                }
			}
		});
		
		return graphic;
	}

	@Override
	public JPanel getEStationGraphic(EStation station) {
		JPanel graphic = prepareGraphic();
		
		graphic.add(new JLabel(new ImageIcon(view.loadImage("station.png", ICON_SIZE, ICON_SIZE))));
		graphic.add(Box.createHorizontalBox());
		
		JPanel namePanel = new JPanel();
		namePanel.setBackground(backgroundColor);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
		
		JLabel numLabel = new JLabel(station.getName());
		numLabel.setFont(largeFont);
		numLabel.setForeground(textColor);
		
		JLabel descrLabel = new JLabel("in " + station.getTown().getName());
		descrLabel.setFont(smallFont);
		descrLabel.setForeground(textColor);
		
		namePanel.add(numLabel);
		namePanel.add(descrLabel);
		
		graphic.add(namePanel);
		
		graphic.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
               int answer = JOptionPane.showConfirmDialog(view.getContentPane(), 
            		   "Would you like to view the lines servicing the station " + station.getName() + "?");
               
               if(answer == JOptionPane.OK_OPTION)
            	   view.getLinesByStation(station);
			}
		});
		
		return graphic;
	}

	@Override
	public JPanel getETimetableGraphic(ETimetable timetable) {
		JPanel graphic = prepareGraphic();
		
		JLabel nameLabel = new JLabel(timetable.toString());
		nameLabel.setFont(largeFont);
		nameLabel.setForeground(textColor);
		nameLabel.setBackground(backgroundColor);
		
		graphic.add(nameLabel);
		
		return graphic;
	}
	
	private static JPanel prepareGraphic() {
		JPanel graphic = new JPanel();
		graphic.setLayout(new BoxLayout(graphic, BoxLayout.X_AXIS));
		graphic.setBackground(backgroundColor);
		graphic.setBorder(BorderFactory.createLineBorder(textColor, 2));
		graphic.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		return graphic;
	}

}
