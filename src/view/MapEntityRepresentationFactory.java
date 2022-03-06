package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import entity.ELine;
import entity.EStation;
import entity.ETimestamp;
import entity.ETown;
import localisation.Languages;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
public class MapEntityRepresentationFactory
        extends AbstractGraphicalEntityRepresentationFactory<MapView> {

	private static final String FIND_TOWNS    = Languages.getString("OASAEntityGraphicFactory.0"); //$NON-NLS-1$
	private static final String FIND_LINES    = Languages.getString("OASAEntityGraphicFactory.1"); //$NON-NLS-1$
	private static final String FIND_TIMES    = Languages.getString("OASAEntityGraphicFactory.2"); //$NON-NLS-1$
	private static final String FIND_STATIONS = Languages.getString("OASAEntityGraphicFactory.3"); //$NON-NLS-1$
	private static final int    ICON_SIZE     = 54;

	private MapView view;

	/**
	 * TODO
	 *
	 */
	public MapEntityRepresentationFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	void initializeView(MapView newView) {
		if (view == null)
			view = newView;
	}

	@Override
	public JComponent getETownRepresentation(ETown town) {
		final int size = 20;

		final JComponent panel = new JComponent() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.GREEN);
				g.fillOval(0, 0, size, size);
			}
		};


		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				final ImageIcon icon    = view.getImageIcon("town.png", ICON_SIZE, ICON_SIZE);      //$NON-NLS-1$
				final String   msg     = String
				        .format(Languages.getString("OASAEntityGraphicFactory.4"), town.getName()); //$NON-NLS-1$
				final String   title   = Languages.getString("OASAEntityGraphicFactory.5");         //$NON-NLS-1$
				final String[] options = { FIND_LINES, FIND_STATIONS };
				final String   initial = FIND_LINES;

				final String res = (String) JOptionPane.showInputDialog(view.frame,
				        msg, title, JOptionPane.QUESTION_MESSAGE, icon, options, initial);

				if (res == null)
					;
				else if (res.equals(FIND_LINES))
					view.getLinesByTown(town);
				else if (res.equals(FIND_STATIONS))
					view.getStationsByTown(town);
			}
		});
		panel.setSize(size, size);

		return panel;
	}

	@Override
	public JComponent getELineRepresentation(ELine line) {
		final int size = 20;

		final JComponent panel = new JComponent() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.CYAN);
				g.fillOval(0, 0, size, size);
			}
		};

		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				final ImageIcon icon    = view.getImageIcon(line.getType().getSpriteName(),
				        ICON_SIZE,
				        ICON_SIZE);
				final String    msg     = String
				        .format(Languages.getString("OASAEntityGraphicFactory.6"),           //$NON-NLS-1$
				                line.getDescription());
				final String    title   = Languages.getString("OASAEntityGraphicFactory.7"); //$NON-NLS-1$
				final String[]  options = { FIND_TIMES, FIND_STATIONS, FIND_TOWNS };
				final String    initial = FIND_TIMES;

				final String res = (String) JOptionPane.showInputDialog(view.frame,
				        msg, title, JOptionPane.QUESTION_MESSAGE, icon, options, initial);

				if (res == null)
					;
				else if (res.equals(FIND_TIMES))
					view.getTimetablesByLine(line);
				else if (res.equals(FIND_STATIONS))
					view.getStationsByLine(line);
				else if (res.equals(FIND_TOWNS))
					view.getTownsByLine(line);
			}
		});
		panel.setSize(size, size);

		return panel;
	}

	@Override
	public JComponent getDetailedELineRepresentation(ELine line, EStation station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JComponent getEStationRepresentation(EStation station) {
		final int size = 20;

		final JComponent panel = new JComponent() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.RED);
				g.fillOval(0, 0, size, size);
			}
		};

		panel.addMouseListener(new MouseAdapter() {

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

		panel.setSize(size, size);

		return panel;
	}

	@Override
	public JComponent getETimestampRepresentation(ETimestamp timestamp) {
		return new OASAEntityRepresentationFactory<>()
		        .getETimestampRepresentation(timestamp);
	}
}
