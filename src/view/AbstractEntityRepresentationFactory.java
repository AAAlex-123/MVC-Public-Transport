package view;

import entity.ELine;
import entity.EStation;
import entity.ETimestamp;
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
	 * Returns a detailed Representation for an {@link ELine} consisting
	 * additionally of arrival information about a specific Station.
	 *
	 * @param line    the Line
	 * @param station the Station for which to provide detailed information
	 *
	 * @return the detailed Representation for the Line
	 */
	E getDetailedELineRepresentation(ELine line, EStation station);

	/**
	 * Returns a Representation for an {@link EStation}.
	 *
	 * @param station the Station
	 *
	 * @return the Representation for the Station
	 */
	E getEStationRepresentation(EStation station);

	/**
	 * Returns a Representation for an {@link ETimestamp}.
	 *
	 * @param timetable the Timetable
	 *
	 * @return the Representation for the Timetable
	 */
	E getETimestampRepresentation(ETimestamp timetable);
}
