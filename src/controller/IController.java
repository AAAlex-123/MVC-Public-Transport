package controller;

import entity.ELine;
import entity.EStation;
import entity.ETown;

/**
 * An interface for the {@code Controller} of the {@code MVC architecture}. The
 * controller handles the communication between the {@link view.IView View} and
 * the underlying {@link model.IModel Model}. It receives requests from the
 * {@code View} using this interface, retrieves data using the {@code Model}'s
 * interface and returns them to the {@code View} using its interface.
 *
 * @author Alex Mandelias
 */
public interface IController {

	void getStationsByTown(ETown town);

	void getLinesByTown(ETown town);

	void getStationsByLine(ELine line);

	void getTimetablesByLine(ELine line);

	void getTownsByLine(ELine line);

	void getLinesByStation(EStation station);
}
