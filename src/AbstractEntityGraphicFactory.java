package view;

import javax.swing.JPanel;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

/**
 * TODO
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
interface AbstractEntityGraphicFactory {
	
	void setLoader(AbstractView.ImageLoader newLoader);

	/**
	 * Returns a Graphic for a {@link ETown}.
	 *
	 * @param town the town
	 *
	 * @return a Graphic for the town
	 */
	JPanel getETownGraphic(ETown town);

	/**
	 * Returns a Graphic for a {@link ELine}.
	 *
	 * @param line the town
	 *
	 * @return a Graphic for the line
	 */
	JPanel getELineGraphic(ELine line);

	/**
	 * Returns a Graphic for a {@link EStation}.
	 *
	 * @param station the town
	 *
	 * @return a Graphic for the station
	 */
	JPanel getEStationGraphic(EStation station);

	/**
	 * Returns a Graphic for a {@link ETimetable}.
	 *
	 * @param timetable the timetable
	 *
	 * @return a Graphic for the timetable
	 */
	JPanel getETimetableGraphic(ETimetable timetable);
}
