package controller;

import entity.ELine;
import entity.EStation;
import entity.ETown;
import model.IModel;
import requirement.util.Requirements;
import view.IView;

/**
 * An interface for the {@code Controller} of the {@code MVC architecture}. The
 * controller handles the communication between the {@link IView} and the
 * underlying {@link IModel}. It receives requests from the {@code View} using
 * this interface, retrieves data using the {@code Model}'s interface and
 * returns them to the {@code View} using its interface.
 * <p>
 * Classes of the {@link entity} package are used to transfer the requested data
 * and {@link Requirements} objects are used to encapsulate that request.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public interface IController {

	/**
	 * Fetches all Line data from the {@code IModel} and requests that the
	 * {@code IView} display it. If an error occurs, the {@code IView} displays that
	 * instead.
	 */
	void getAllLines();

	/**
	 * Fetches all Town data from the {@code IModel} and requests that the
	 * {@code IView} display it. If an error occurs, the {@code IView} displays that
	 * instead.
	 */
	void getAllTowns();

	/**
	 * Fetches all Station data from the {@code IModel}, filtered by Town, and
	 * requests that the {@code IView} display it. If an error occurs, the
	 * {@code IView} displays that instead.
	 *
	 * @param town the Town whose Stations will be displayed
	 */
	void getStationsByTown(ETown town);

	/**
	 * Fetches all Station data from the {@code IModel}, filtered by Town, and
	 * requests that the {@code IView} display it. If an error occurs, the
	 * {@code IView} displays that instead.
	 *
	 * @param town the Town whose Stations will be displayed
	 */
	void getLinesByTown(ETown town);

	/**
	 * Fetches all Station data from the {@code IModel}, filtered by Line, and
	 * requests that the {@code IView} display it. If an error occurs, the
	 * {@code IView} displays that instead.
	 *
	 * @param line the Line whose Stations will be displayed
	 */
	void getStationsByLine(ELine line);

	/**
	 * Fetches all Timetable data from the {@code IModel}, filtered by Line, and
	 * requests that the {@code IView} display it. If an error occurs, the
	 * {@code IView} displays that instead.
	 *
	 * @param line the Line whose Timetables will be displayed
	 */
	void getTimetablesByLine(ELine line);

	/**
	 * Fetches all Town data from the {@code IModel}, filtered by Line, and requests
	 * that the {@code IView} display it. If an error occurs, the {@code IView}
	 * displays that instead.
	 *
	 * @param line the Line whose Towns will be displayed
	 */
	void getTownsByLine(ELine line);

	/**
	 * Fetches all Line data from the {@code IModel}, filtered by Station, and
	 * requests that the {@code IView} display it. If an error occurs, the
	 * {@code IView} displays that instead.
	 *
	 * @param station the Station whose Lines will be displayed
	 */
	void getLinesByStation(EStation station);

	/**
	 * Inserts a new Town to the {@code IModel}. If the Requirements are not
	 * fulfilled, the {@code IView} is updated with an error.
	 *
	 * @param reqs the Requirements for the creation of the Town, ideally obtained
	 *             from the {@link #getInsertTownRequirements()} method.
	 */
	void insertTown(Requirements reqs);

	/**
	 * Inserts a new Line to the {@code IModel}. If the Requirements are not
	 * fulfilled, the {@code IView} is updated with an error.
	 *
	 * @param reqs the Requirements for the creation of the Line, ideally obtained
	 *             from the {@link #getInsertLineRequirements()} method.
	 */
	void insertLine(Requirements reqs);

	/**
	 * Inserts a new Station to the {@code IModel}. If the Requirements are not
	 * fulfilled, the {@code IView} is updated with an error.
	 *
	 * @param reqs the Requirements for the creation of the Station, ideally
	 *             obtained from the {@link #getInsertStationRequirements()} method.
	 */
	void insertStation(Requirements reqs);

	/**
	 * Assigns an existing Station to a Line in the {@code IModel}. If the
	 * Requirements are not fulfilled, the {@code IView} is updated with an error.
	 *
	 * @param reqs the Requirements for the creation of the Town, ideally obtained
	 *             from the {@link #getInsertStationToLineRequirements()} method.
	 */
	void insertStationToLine(Requirements reqs);

	/**
	 * Assigns a new Timetable to a Line in the {@code IModel}. If the Requirements
	 * are not fulfilled, the {@code IView} is updated with an error.
	 *
	 * @param reqs the Requirements for the creation of the Town, ideally obtained
	 *             from the {@link #getInsertStationToLineRequirements()} method.
	 */
	void insertTimetableToLine(Requirements reqs);

	/**
	 * Returns a {@code Requirements} object containing all the necessary fields for
	 * a {@code Town} to be inserted. It must be fulfilled before being used to
	 * insert a Town.
	 *
	 * @return a Requirements object with all the necessary fields
	 *
	 * @see #insertTown(Requirements)
	 * @see Requirements#fulfil(String, Object)
	 */
	Requirements getInsertTownRequirements();

	/**
	 * Returns a {@code Requirements} object containing all the necessary fields for
	 * a {@code Line} to be inserted. It must be fulfilled before being used to
	 * insert a Town.
	 *
	 * @return a Requirements object with all the necessary fields
	 *
	 * @see #insertLine(Requirements)
	 * @see Requirements#fulfil(String, Object)
	 */
	Requirements getInsertLineRequirements();

	/**
	 * Returns a {@code Requirements} object containing all the necessary fields for
	 * a {@code Station} to be inserted. It must be fulfilled before being used to
	 * insert a Town.
	 *
	 * @return a Requirements object with all the necessary fields
	 *
	 * @see #insertStation(Requirements)
	 * @see Requirements#fulfil(String, Object)
	 */
	Requirements getInsertStationRequirements();

	/**
	 * Returns a {@code Requirements} object containing all the necessary fields for
	 * a {@code Station} to be inserted in a Line. It must be fulfilled before being
	 * used to insert a Town.
	 *
	 * @return a Requirements object with all the necessary fields
	 *
	 * @see #insertStationToLine(Requirements)
	 * @see Requirements#fulfil(String, Object)
	 */
	Requirements getInsertStationToLineRequirements();

	/**
	 * Returns a {@code Requirements} object containing all the necessary fields for
	 * a {@code Timetable} to be inserted in a Line. It must be fulfilled before
	 * being used to insert a Town.
	 *
	 * @return a Requirements object with all the necessary fields
	 *
	 * @see #insertTimetableToLine(Requirements)
	 * @see Requirements#fulfil(String, Object)
	 */
	Requirements getInsertTimetableToLineRequirements();
}
