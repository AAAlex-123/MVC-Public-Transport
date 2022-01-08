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
 * select operations to perform related to entities.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
class OASAEntityGraphicFactory implements AbstractEntityGraphicFactory {

	private static final String fontName        = "TimesRoman";                                   //$NON-NLS-1$
	private static final Font largeFont = new Font (fontName, Font.BOLD, 24);
	private static final Font standardFont = new Font (fontName, Font.BOLD | Font.ITALIC, 18);
	private static final Font smallFont = new Font (fontName, Font.PLAIN, 12);
	private static final Color textColor = Color.BLACK;
	private static final Color backgroundColor = Color.WHITE;
	private static final int ICON_SIZE = 54;

	private static final String FIND_TOWNS = "Find towns";
	private static final String FIND_LINES = "Find lines";
	private static final String FIND_TIMES    = "Find departure times";
	private static final String FIND_STATIONS = "Find stations";

	private AbstractView view;

	/** Constructs an OASAEntityGraphicFactory */
	public OASAEntityGraphicFactory() {
		view = null;
	}

	@Override
	public void initializeView(AbstractView newView) {
		if (view == null)
			view = newView;
	}

	@Override
	public JPanel getETownGraphic(ETown town) {
		JPanel graphic = prepareGraphic();

		ImageIcon icon = view.getImageIcon("town.png", ICON_SIZE, ICON_SIZE); //$NON-NLS-1$

		graphic.add(new JLabel(icon));
		graphic.add(Box.createHorizontalBox());

		JLabel nameLabel = new JLabel(town.getName());
		nameLabel.setFont(standardFont);
		nameLabel.setForeground(textColor);
		nameLabel.setBackground(backgroundColor);

		graphic.add(nameLabel);

		graphic.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String msg = String.format("What do you wish to look for in the town of %s?", town.getName());
				String title = "Town Options";
				String[] options = new String[]{ FIND_LINES, FIND_STATIONS} ;
				String   initial = FIND_LINES;
                String   res     = (String) JOptionPane.showInputDialog(view.getContentPane(),
				        msg, title, JOptionPane.QUESTION_MESSAGE, icon, options, initial);

                switch (res) {
                case FIND_LINES:
                    view.getLinesByTown(town);
                    break;
                case FIND_STATIONS:
                    view.getStationsByTown(town);
                    break;
				default:
					break;
                }
			}
		});

		return graphic;
	}

	@Override
	public JPanel getELineGraphic(ELine line) {
		JPanel graphic = prepareGraphic();

		ImageIcon icon = view.getImageIcon(line.getType().getSpriteName(), ICON_SIZE, ICON_SIZE);

		graphic.add(new JLabel(icon));
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
				String   msg            = String
				        .format("What do you wish to look for in the line %s?", line.getName());
				String   title          = "Line Options";
				String[] options        = new String[] { FIND_TIMES, FIND_STATIONS, FIND_TOWNS };
				String   initial = FIND_TIMES;

                String   res     = (String) JOptionPane.showInputDialog(view.getContentPane(),
				        msg, title, JOptionPane.QUESTION_MESSAGE, icon, options, initial);

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
				default:
					break;
                }
			}
		});

		return graphic;
	}

	@Override
	public JPanel getEStationGraphic(EStation station) {
		JPanel graphic = prepareGraphic();

		graphic.add(new JLabel(view.getImageIcon("station.png", ICON_SIZE, ICON_SIZE))); //$NON-NLS-1$
		graphic.add(Box.createHorizontalBox());

		JPanel namePanel = new JPanel();
		namePanel.setBackground(backgroundColor);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

		JLabel numLabel = new JLabel(station.getName());
		numLabel.setFont(largeFont);
		numLabel.setForeground(textColor);

		JLabel descrLabel = new JLabel(String.format("in %s", station.getTown().getName()));
		descrLabel.setFont(smallFont);
		descrLabel.setForeground(textColor);

		namePanel.add(numLabel);
		namePanel.add(descrLabel);

		graphic.add(namePanel);

		graphic.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String msg    = String.format(
				        "Would you like to view the lines servicing the station %s?",
				        station.getName());
				int    answer = JOptionPane.showConfirmDialog(view.getContentPane(), msg);

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
