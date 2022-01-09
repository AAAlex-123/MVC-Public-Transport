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
import localisation.Languages;

/**
 * A Factory producing self-describing graphics which open pop-up windows to
 * select operations to perform related to entities.
 *
 * @param <V> the type of the View associated with this factory
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
class OASAEntityRepresentationFactory<V extends AbstractGUIView>
        extends AbstractGraphicalEntityRepresentationFactory<V> {

	private static final String fontName        = "TimesRoman";                                   //$NON-NLS-1$
	private static final Font   largeFont       = new Font(fontName, Font.BOLD, 24);
	private static final Font   standardFont    = new Font(fontName, Font.BOLD | Font.ITALIC, 18);
	private static final Font   smallFont       = new Font(fontName, Font.PLAIN, 12);
	private static final Color  textColor       = Color.BLACK;
	private static final Color  backgroundColor = Color.WHITE;
	private static final int    ICON_SIZE       = 54;

	private static final String FIND_TOWNS    = Languages.getString("OASAEntityGraphicFactory.0"); //$NON-NLS-1$
	private static final String FIND_LINES    = Languages.getString("OASAEntityGraphicFactory.1"); //$NON-NLS-1$
	private static final String FIND_TIMES    = Languages.getString("OASAEntityGraphicFactory.2"); //$NON-NLS-1$
	private static final String FIND_STATIONS = Languages.getString("OASAEntityGraphicFactory.3"); //$NON-NLS-1$

	private V view;

	/** Constructs an OASAEntityRepresentationFactory */
	public OASAEntityRepresentationFactory() {}

	@Override
	public void initializeView(V newView) {
		if (view == null)
			view = newView;
	}

	@Override
	public JPanel getETownGraphic(ETown town) {
		final JPanel graphic = prepareGraphic();

		final ImageIcon icon = view.getImageIcon("town.png", ICON_SIZE, ICON_SIZE); //$NON-NLS-1$

		graphic.add(new JLabel(icon));
		graphic.add(Box.createHorizontalBox());

		final JLabel nameLabel = new JLabel(town.getName());
		nameLabel.setFont(standardFont);
		nameLabel.setForeground(textColor);
		nameLabel.setBackground(backgroundColor);

		graphic.add(nameLabel);

		graphic.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				final String   msg     = String
				        .format(Languages.getString("OASAEntityGraphicFactory.4"), town.getName()); //$NON-NLS-1$
				final String   title   = Languages.getString("OASAEntityGraphicFactory.5"); //$NON-NLS-1$
				final String[] options = { FIND_LINES, FIND_STATIONS };
				final String   initial = FIND_LINES;

				final String   res     = (String) JOptionPane.showInputDialog(view.frame,
				        msg, title, JOptionPane.QUESTION_MESSAGE, icon, options, initial);

				if (res.equals(FIND_LINES))
					view.getLinesByTown(town);
				else if (res.equals(FIND_STATIONS))
					view.getStationsByTown(town);
			}
		});

		return graphic;
	}

	@Override
	public JPanel getELineGraphic(ELine line) {
		final JPanel graphic = OASAEntityRepresentationFactory.prepareGraphic();

		final ImageIcon icon = view.getImageIcon(line.getType().getSpriteName(), ICON_SIZE,
		        ICON_SIZE);

		graphic.add(new JLabel(icon));
		graphic.add(Box.createHorizontalBox());

		final JPanel namePanel = new JPanel();
		namePanel.setBackground(backgroundColor);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

		final JLabel numLabel = new JLabel(line.getName());
		numLabel.setFont(largeFont);
		numLabel.setForeground(textColor);

		final JLabel descrLabel = new JLabel(line.getDescription());
		descrLabel.setFont(standardFont);
		descrLabel.setForeground(textColor);

		namePanel.add(numLabel);
		namePanel.add(descrLabel);

		graphic.add(namePanel);

		graphic.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				final String   msg     = String
				        .format(Languages.getString("OASAEntityGraphicFactory.6"), line.getDescription()); //$NON-NLS-1$
				final String   title   = Languages.getString("OASAEntityGraphicFactory.7"); //$NON-NLS-1$
				final String[] options = { FIND_TIMES, FIND_STATIONS, FIND_TOWNS };
				final String   initial = FIND_TIMES;

				final String res = (String) JOptionPane.showInputDialog(view.frame,
				        msg, title, JOptionPane.QUESTION_MESSAGE, icon, options, initial);

				if (res.equals(FIND_TIMES))
					view.getTimetablesByLine(line);
				else if (res.equals(FIND_STATIONS))
					view.getStationsByLine(line);
				else if (res.equals(FIND_TOWNS))
					view.getTownsByLine(line);
			}
		});

		return graphic;
	}

	@Override
	public JPanel getEStationGraphic(EStation station) {
		final JPanel graphic = OASAEntityRepresentationFactory.prepareGraphic();

		graphic.add(new JLabel(view.getImageIcon("station.png", ICON_SIZE, ICON_SIZE))); //$NON-NLS-1$
		graphic.add(Box.createHorizontalBox());

		final JPanel namePanel = new JPanel();
		namePanel.setBackground(backgroundColor);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

		final JLabel numLabel = new JLabel(station.getName());
		numLabel.setFont(largeFont);
		numLabel.setForeground(textColor);

		final JLabel descrLabel = new JLabel(String.format(Languages.getString("OASAEntityGraphicFactory.8"), station.getTown().getName())); //$NON-NLS-1$
		descrLabel.setFont(smallFont);
		descrLabel.setForeground(textColor);

		namePanel.add(numLabel);
		namePanel.add(descrLabel);

		graphic.add(namePanel);

		graphic.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				final String msg = String.format(
				        Languages.getString("OASAEntityGraphicFactory.9"), //$NON-NLS-1$
				        station.getName());

				final int answer = JOptionPane.showConfirmDialog(view.frame, msg);

				if (answer == JOptionPane.OK_OPTION)
					view.getLinesByStation(station);
			}
		});

		return graphic;
	}

	@Override
	public JPanel getETimetableGraphic(ETimetable timetable) {
		final JPanel graphic = OASAEntityRepresentationFactory.prepareGraphic();

		final JLabel nameLabel = new JLabel(timetable.toString());
		nameLabel.setFont(largeFont);
		nameLabel.setForeground(textColor);
		nameLabel.setBackground(backgroundColor);

		graphic.add(nameLabel);

		return graphic;
	}

	private static JPanel prepareGraphic() {
		final JPanel graphic = new JPanel();
		graphic.setLayout(new BoxLayout(graphic, BoxLayout.X_AXIS));
		graphic.setBackground(backgroundColor);
		graphic.setBorder(BorderFactory.createLineBorder(textColor, 2));
		graphic.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		return graphic;
	}
}
