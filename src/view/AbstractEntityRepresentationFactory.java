package view;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

/**
 * An interface for the Factories which are responsible for creating
 * user-friendly representations for the Entities of the {@link entity} package.
 *
 * @param <E> the type of the representation of the Entities
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
interface AbstractEntityRepresentationFactory<E> {

	/**
	 * Returns a Graphic for a {@link ETown}.
	 *
	 * @param town the town
	 *
	 * @return a Graphic for the town
	 */
	E getETownGraphic(ETown town);

	/**
	 * Returns a Graphic for a {@link ELine}.
	 *
	 * @param line the town
	 *
	 * @return a Graphic for the line
	 */
	E getELineGraphic(ELine line);

	/**
	 * Returns a Graphic for a {@link EStation}.
	 *
	 * @param station the town
	 *
	 * @return a Graphic for the station
	 */
	E getEStationGraphic(EStation station);

	/**
	 * Returns a Graphic for a {@link ETimetable}.
	 *
	 * @param timetable the timetable
	 *
	 * @return a Graphic for the timetable
	 */
	E getETimetableGraphic(ETimetable timetable);
}
