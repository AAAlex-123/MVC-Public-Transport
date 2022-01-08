package view;

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
interface AbstractEntityGraphicFactory<T> {
	
	/**
	 * Set the view of the factory, if not already set.
	 * 
	 * @param view the new view of the factory
	 */
	void initializeView(AbstractGUIView view);
	
	/**
	 * Returns a Graphic for a {@link ETown}.
	 *
	 * @param town the town
	 *
	 * @return a Graphic for the town
	 */
	T getETownGraphic(ETown town);

	/**
	 * Returns a Graphic for a {@link ELine}.
	 *
	 * @param line the town
	 *
	 * @return a Graphic for the line
	 */
	T getELineGraphic(ELine line);

	/**
	 * Returns a Graphic for a {@link EStation}.
	 *
	 * @param station the town
	 *
	 * @return a Graphic for the station
	 */
	T getEStationGraphic(EStation station);

	/**
	 * Returns a Graphic for a {@link ETimetable}.
	 *
	 * @param timetable the timetable
	 *
	 * @return a Graphic for the timetable
	 */
	T getETimetableGraphic(ETimetable timetable);
}
