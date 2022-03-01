package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JComponent getELineRepresentation(ELine line) {
		// TODO Auto-generated method stub
		return null;
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
				g.setColor(Color.BLACK);
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
	public JComponent getETimestampRepresentation(ETimestamp timetable) {
		// TODO Auto-generated method stub
		return null;
	}
}
