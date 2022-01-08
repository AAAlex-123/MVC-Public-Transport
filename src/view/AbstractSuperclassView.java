package view;

import controller.IController;
import entity.ELine;
import entity.EStation;
import entity.ETown;

/**
 * An abstract implementation of the {@link IView} interface which defines
 * delegate methods to the View's registered {@link IController}, which the
 * concrete implementations will call.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 * @param <FactoryT> the type of the factory used
 */
public abstract class AbstractView<FactoryT> implements IView {
	
	private IController controller;

	/** The factory that constructs graphics for the Entities this View displays */
	protected final AbstractEntityGraphicFactory<FactoryT> factory;
	
	public AbstractView(AbstractEntityGraphicFactory<FactoryT> factory, IController controller) {
		this.factory = factory;
		this.controller = controller;
	}
	
	@Override
	public final void registerController(IController newController) {
		controller = newController;
	}
	
	/**
	 * A wrapper method for {@link IController#getStationsByTown(ETown)}.
	 *
	 * @param town the town from which the stations will be selected
	 *
	 */
	protected void getStationsByTown(ETown town) {
		controller.getStationsByTown(town);
	}

	/**
	 * A wrapper method for {@link IController#getLinesByTown(ETown)}.
	 *
	 * @param town the town from which the lines will be selected
	 */
	protected void getLinesByTown(ETown town) {
		controller.getLinesByTown(town);
	}

	/**
	 * A wrapper method for {@link IController#getStationsByLine(ELine)}.
	 *
	 * @param line the line from which the stations will be selected
	 */
	protected void getStationsByLine(ELine line) {
		controller.getStationsByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getTimetablesByLine(ELine)}.
	 *
	 * @param line the line from which the timetables will be selected
	 */
	protected void getTimetablesByLine(ELine line) {
		controller.getTimetablesByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getTownsByLine(ELine)}.
	 *
	 * @param line the line from which the timetables will be selected
	 */
	protected void getTownsByLine(ELine line) {
		controller.getTownsByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getLinesByStation(EStation)}.
	 *
	 * @param station the station from which the timetables will be selected
	 */
	protected void getLinesByStation(EStation station) {
		controller.getLinesByStation(station);
	}

	/**
	 * A wrapper method for {@link IController#getAllTowns()}.
	 */
	protected void getAllTowns() {
		controller.getAllTowns();
	}

	/**
	 * A wrapper method for {@link IController#getAllLines()}.
	 */
	protected void getAllLines() {
		controller.getAllLines();
	}

}