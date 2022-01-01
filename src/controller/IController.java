package controller;

import entity.ELine;
import entity.EStation;
import entity.ETown;
import requirement.util.Requirements;

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

	void getAllLines();

	void getAllTowns();

	void getStationsByTown(ETown town);

	void getLinesByTown(ETown town);

	void getStationsByLine(ELine line);

	void getTimetablesByLine(ELine line);

	void getTownsByLine(ELine line);

	void getLinesByStation(EStation station);

	void insertTown(Requirements reqs);

	void insertLine(Requirements reqs);

	void insertStation(Requirements reqs);

	void insertStationToLine(Requirements reqs);

	void insertTimetableToLine(Requirements reqs);
}
