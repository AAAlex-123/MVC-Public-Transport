package controller;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import model.IModel;
import requirement.util.Requirements;
import view.IView;

/**
 * An interface for the {@code Controller} of the {@code MVC architecture}. The
 * controller handles the communication between the {@link view.IView View} and
 * the underlying {@link model.IModel Model}. It receives requests from the
 * {@code View} using this interface, retrieves data using the {@code Model}'s
 * interface and returns them to the {@code View} using its interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public interface IController {

	/**
	 * Orders the {@link IView view} to display all
	 * lines registered in the system.
	 */
	void getAllLines();

	/**
	 * Orders the {@link IView view} to display all
	 * towns registered in the system.
	 */
	void getAllTowns();

	/**
	 * Orders the {@link IView view} to display all
	 * stations belonging to the given town.
	 *
	 * @param town the town whose stations will be displayed
	 */
	void getStationsByTown(ETown town);

	/**
	 * Orders the {@link IView view} to display all
	 * lines going through the given town.
	 *
	 * @param town the town whose lines will be displayed
	 */
	void getLinesByTown(ETown town);

	/**
	 * Orders the {@link IView view} to display all
	 * stations belonging to the given line.
	 *
	 * @param line the line whose stations will be displayed
	 */
	void getStationsByLine(ELine line);

	/**
	 * Orders the {@link IView view} to display all the
	 * time tables of the given line.
	 *
	 * @param line the line whose time tables will be displayed
	 */
	void getTimetablesByLine(ELine line);

	/**
	 * Orders the {@link IView view} to display all the
	 * towns which the given line services.
	 *
	 * @param line the line whose towns will be displayed
	 */
	void getTownsByLine(ELine line);

	/**
	 * Orders the {@link IView view} to display all the
	 * lines going through the given station.
	 *
	 * @param station the station whose lines will be displayed
	 */
	void getLinesByStation(EStation station);

	/**
	 * Inserts a new {@link ETown town} to the {@link IModel model}.
	 * This method should be supplied by the requirements constructed by #getInsertTownRequirements()
	 *
	 * @param reqs the <b>fulfilled</b> {@link Requirements requirements} for the creation of the town instance
	 * @see #getInsertTownRequirements()
	 */
	void insertTown(Requirements reqs);

	/**
	 * Inserts a new {@link ELine line} to the {@link IModel model}.
	 * This method should be supplied by the requirements constructed by #getInsertLineRequirements()
	 *
	 * @param reqs the <b>fulfilled</b> {@link Requirements requirements} for the creation of the line instance
	 * @see #getInsertLineRequirements()
	 */
	void insertLine(Requirements reqs);

	/**
	 * Inserts a new {@link EStation station} to the {@link IModel model}.
	 * This method should be supplied by the requirements constructed by #getInsertStationRequirements()
	 *
	 * @param reqs the <b>fulfilled</b> {@link Requirements requirements} for the creation of the station instance
	 * @see #getInsertStationRequirements()
	 */
	void insertStation(Requirements reqs);

	/**
	 * Assigns a pre-existing {@link EStation station} to a {@link ELine line} in the {@link IModel model}.
	 * This method should be supplied by the requirements constructed by #getInsertStationToLineRequirements()
	 *
	 * @param reqs the <b>fulfilled</b> {@link Requirements requirements} for the assignment of the station instance
	 * @see #getInsertStationToLineRequirements()
	 */
	void insertStationToLine(Requirements reqs);

	/**
	 * Assigns a pre-existing {@link ETimetable time table} to a {@link ELine line} in the {@link IModel model}.
	 * This method should be supplied by the requirements constructed by #getInsertTimetableToLineRequirements()
	 *
	 * @param reqs the <b>fulfilled</b> {@link Requirements requirements} for the assignment of the time table instance
	 * @see #getInsertTimetableToLineRequirements()
	 */
	void insertTimetableToLine(Requirements reqs);

	/**
	 * Get a {@link Requirements requirements} instance containing all the necessary fields
	 * for a {@link ETown town} instance to be created.
	 *
	 * Each {@link Requirement requirement} needs to be {@link Requirements#fulfil(String, Object) fulfilled}
	 * before being used in {@link #insertTown(Requirements)}
	 *
	 * @return a requirements instance with all the necessary fields
	 */
	Requirements getInsertTownRequirements();

	/**
	 * Get a {@link Requirements requirements} instance containing all the necessary fields
	 * for a {@link ELine line} instance to be created.
	 *
	 * Each {@link Requirement requirement} needs to be {@link Requirements#fulfil(String, Object) fulfilled}
	 * before being used in {@link #insertLine(Requirements)}
	 *
	 * @return a requirements instance with all the necessary fields
	 */
	Requirements getInsertLineRequirements();

	/**
	 * Get a {@link Requirements requirements} instance containing all the necessary fields
	 * for a {@link EStation station} instance to be created.
	 *
	 * Each {@link Requirement requirement} needs to be {@link Requirements#fulfil(String, Object) fulfilled}
	 * before being used in {@link #insertStation(Requirements)}
	 *
	 * @return a requirements instance with all the necessary fields
	 */
	Requirements getInsertStationRequirements();

	/**
	 * Get a {@link Requirements requirements} instance containing all the necessary fields
	 * for an existing {@link EStation station} instance to be assigned to an {@link ELine line}.
	 *
	 * Each {@link Requirement requirement} needs to be {@link Requirements#fulfil(String, Object) fulfilled}
	 * before being used in {@link #insertStationToLine(Requirements)}
	 *
	 * @return a requirements instance with all the necessary fields
	 */
	Requirements getInsertStationToLineRequirements();

	/**
	 * Get a {@link Requirements requirements} instance containing all the necessary fields
	 * for an existing {@link ETimetable time table} instance to be assigned to an {@link ELine line}.
	 *
	 * Each {@link Requirement requirement} needs to be {@link Requirements#fulfil(String, Object) fulfilled}
	 * before being used in {@link #insertTimetableToLine(Requirements)}
	 *
	 * @return a requirements instance with all the necessary fields
	 */
	Requirements getInsertTimetableToLineRequirements();
}
