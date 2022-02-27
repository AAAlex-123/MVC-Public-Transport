package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

import entity.ELine;
import entity.EStation;
import entity.ETimestamp;
import entity.ETown;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
public class MapEntityRepresentationFactory
        extends AbstractGraphicalEntityRepresentationFactory<MapView> {

	/**
	 * TODO
	 *
	 */
	public MapEntityRepresentationFactory() {
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub

		final int size = 20;

		JPanel p = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.YELLOW);
				g.fillOval(size / 2, size / 2, size, size);
				System.out.printf("drawn at: %s%n", this.getLocation());
			}
		};

		p.setSize(size, size);

		return p;
	}

	@Override
	public JComponent getETimestampRepresentation(ETimestamp timetable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void initializeView(MapView newView) {
		// TODO Auto-generated method stub

	}

}
