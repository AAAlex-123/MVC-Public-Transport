package model;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.List;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import entity.LineType;
import entity.MissingSpriteException;
import requirement.util.Requirements;

/**
 * An interface for the {@code Model} of the {@code MVC architecture}. The Model
 * communicates with the database to perform select and update operations on the
 * data. It receives database requests from the {@link controller.IController
 * Controller}, and returns the results (if any) in the form of
 * {@link entity.Entity Entity} objects.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public interface IModel {

	/**
	 * Get all {@link ETown towns} in the system.
	 *
	 * @param req additional search parameters
	 *
	 * @throw SQLException if the database can't satisfy the request
	 *
	 * @return a list of all towns
	 */
	List<ETown> getTowns(Requirements req) throws SQLException;

	/**
	 * Get all {@link ELine lines} registered in the system.
	 *
	 * @param req additional search parameters
	 *
	 * @throw SQLException if the database can't satisfy the request
	 *
	 * @return a list of all lines
	 */
	List<ELine> getLines(Requirements req) throws SQLException;

	/**
	 * Get all {@link EStation stations} registered in the system.
	 *
	 * @param req additional search parameters
	 *
	 * @throw SQLException if the database can't satisfy the request
	 *
	 * @return a list of all stations
	 */
	List<EStation> getStations(Requirements req) throws SQLException;

	/**
	 * Get the image for the specified public transport vehicle.
	 *
	 * @param type the vehicle type
	 *
	 * @throw MissingSpriteException if the image couldn't be loaded
	 *
	 * @return a {@link BufferedImage image} representing the vehicle
	 */
	BufferedImage getVehicleSprite(LineType type) throws MissingSpriteException;

	/**
	 * Insert a new {@link ELine line} in the database.
	 *
	 * @param line the line to be added
	 *
	 * @throws SQLException if the database couldn't be updated
	 */
	void insertLine(ELine line) throws SQLException;

	/**
	 * Insert a new {@link ETown town} in the database.
	 *
	 * @param town the town to be added
	 *
	 * @throws SQLException if the database couldn't be updated
	 */
	void insertTown(ETown town) throws SQLException;

	/**
	 * Insert a new {@link EStation station} in the database.
	 *
	 * @param station the station to be added
	 *
	 * @throws SQLException if the database couldn't be updated
	 */
	void insertStation(EStation station) throws SQLException;

	/**
	 * Insert a {@link ETimetable timetable} into an existing {@link ELine line}.
	 *
	 * @param line      the line to which the timetable will be added
	 * @param timetable the timetable to be added
	 *
	 * @throws SQLException if the database couldn't be updated
	 */
	void insertTimetable(ELine line, ETimetable timetable) throws SQLException;

	/**
	 * Delete a town from the database.
	 *
	 * @param town the town to be updated
	 *
	 * @throws SQLException if the object doesn't exist or if the database couldn't
	 *                      be updated
	 */
	void deleteTown(ETown town) throws SQLException;

	/**
	 * Delete a station from the database.
	 *
	 * @param station the station to be updated
	 *
	 * @throws SQLException if the object doesn't exist or if the database couldn't
	 *                      be updated
	 */
	void deleteStation(EStation station) throws SQLException;

	/**
	 * Delete a {@link ELine line} from the database.
	 *
	 * @param line      the line to be updated
	 * @param timetable the timetable to be deleted
	 *
	 * @throws SQLException if the object doesn't exist or if the database couldn't
	 *                      be updated
	 */
	void deleteLine(ELine line, ETimetable timetable) throws SQLException;

	/**
	 * Delete a {@link ETimetable timetable} from a {@link ELine line} in the
	 * database.
	 *
	 * @param line      the line to be updated
	 * @param timetable the timetable to be deleted
	 *
	 * @throws SQLException if the object doesn't exist or if the database couldn't
	 *                      be updated
	 */
	void deleteTimeTable(ELine line, ETimetable timetable) throws SQLException;
}
