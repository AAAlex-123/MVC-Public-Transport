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
	 * Returns a Representation for an {@link ETown}.
	 *
	 * @param town the Town
	 *
	 * @return the Representation for the Town
	 */
	E getETownRepresentation(ETown town);

	/**
	 * Returns a Representation for an {@link ELine}.
	 *
	 * @param line the Line
	 *
	 * @return the Representation for the Line
	 */
	E getELineRepresentation(ELine line);

	/**
	 * Returns a Representation for an {@link EStation}.
	 *
	 * @param station the Station
	 *
	 * @return the Representation for the Station
	 */
	E getEStationRepresentation(EStation station);

	/**
	 * Returns a Representation for an {@link ETimetable}.
	 *
	 * @param timetable the Timetable
	 *
	 * @return the Representation for the Timetable
	 */
	E getETimetableRepresentatino(ETimetable timetable);
}
