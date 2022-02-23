package model;

import java.sql.SQLException;
import java.util.List;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

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
	 * Returns all {@link ETown} data from the database.
	 *
	 * @param line the Line by which to filter the Towns ({@code null} if not
	 *             applicable).
	 *
	 * @throws SQLException if an SQLException is thrown
	 *
	 * @return a List of all the Towns in the database
	 */
	List<ETown> getTowns(ELine line) throws SQLException;

	/**
	 * Returns all {@link ELine} data from the database.
	 *
	 * @param town    the Town by which to filter the Towns ({@code null} if not
	 *                applicable).
	 * @param station the Station by which to filter the Towns ({@code null} if not
	 *                applicable).
	 *
	 * @throws SQLException if an SQLException is thrown
	 *
	 * @return a List of all the Lines in the database
	 */
	List<ELine> getLines(ETown town, EStation station) throws SQLException;

	/**
	 * Returns all {@link EStation} data from the database.
	 *
	 * @param town the Town by which to filter the Towns ({@code null} if not
	 *             applicable).
	 *
	 * @throws SQLException if an SQLException is thrown
	 *
	 * @return a List of all the Stations in the database
	 */
	List<EStation> getStations(ETown town) throws SQLException;


	/**
	 * Inserts a new {@link ELine} in the database.
	 *
	 * @param line the Line to be added
	 *
	 * @throws SQLException if an SQLException is thrown
	 */
	void insertLine(ELine line) throws SQLException;

	/**
	 * Inserts a new {@link ETown} in the database.
	 *
	 * @param town the Town to be added
	 *
	 * @throws SQLException if an SQLException is thrown
	 */
	void insertTown(ETown town) throws SQLException;

	/**
	 * Inserts a new {@link EStation} in the database.
	 *
	 * @param station the Station to be added
	 *
	 * @throws SQLException if an SQLException is thrown
	 */
	void insertStation(EStation station) throws SQLException;

	/**
	 * Inserts an existing {@link EStation} to an existing {@link ELine}.
	 *
	 * @param line    the Line to which the Station will be added
	 * @param station the Station to be added
	 * @param index   the index of the Station in that Line
	 *
	 * @throws SQLException if an SQLException is thrown
	 */
	void insertStationToLine(ELine line, EStation station, int index) throws SQLException;

	/**
	 * Inserts a new {@link ETimetable} to an existing {@link ELine}.
	 *
	 * @param line      the Line to which the Timetable will be added
	 * @param timetable the Timetable to be added
	 *
	 * @throws SQLException if an SQLException is thrown
	 */
	void insertTimetableToLine(ELine line, ETimetable timetable) throws SQLException;
}
