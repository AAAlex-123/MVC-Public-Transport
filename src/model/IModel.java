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

/**
 * An interface for the {@code Model} of the {@code MVC architecture}. The Model
 * communicates with the database to perform select and update operations on the
 * data. It receives database requests from the {@link controller.IController
 * Controller}, and returns the results (if any) in the form of {@code Entity}
 * objects of the {@link entity} package.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public interface IModel {

	/**
	 * Get all {@link ETown towns} in the system.
	 *
	 * @param line the line by which to filter the towns ({@code null} if not
	 *             applicable).
	 *
	 * @throws SQLException if the database can't satisfy the request
	 *
	 * @return a list of all towns
	 */
	List<ETown> getTowns(ELine line) throws SQLException;

	/**
	 * Get all {@link ELine lines} registered in the system.
	 *
	 * @param town    the town by which to filter the lines ({@code null} if not
	 *                applicable).
	 * @param station the station by which to filter the lines ({@code null} if not
	 *                applicable).
	 *
	 * @throws SQLException if the database can't satisfy the request
	 *
	 * @return a list of all lines
	 */
	List<ELine> getLines(ETown town, EStation station) throws SQLException;

	/**
	 * Get all {@link EStation stations} registered in the system.
	 *
	 * @param town the town by which to filter the stations ({@code null} if not
	 *             applicable).
	 *
	 * @throws SQLException if the database can't satisfy the request
	 *
	 * @return a list of all stations
	 */
	List<EStation> getStations(ETown town) throws SQLException;

	/**
	 * Get the image for the specified public transport vehicle.
	 *
	 * @param type the vehicle type
	 *
	 * @throws MissingSpriteException if the image couldn't be loaded
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

}
