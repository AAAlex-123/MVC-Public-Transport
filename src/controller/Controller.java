package controller;

import entity.ELine;
import entity.EStation;
import entity.ETown;
import model.IModel;
import view.IView;

/**
 * An implementation of the {@link IController} interface.
 *
 * @author Alex Mandelias
 */
public class Controller implements IController {

	private final IModel model;
	private final IView  view;

	/**
	 * Constructs a Controller that communicates with a Model to serve events from a
	 * View.
	 *
	 * @param model the Model associated with this Controller
	 * @param view  the View associated with this Controller
	 */
	public Controller(IModel model, IView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void getStationsByTown(ETown town) {
		// TODO Auto-generated method stub

		/*
		Example implementation of this method:

		@foff
		List<EStation> stations = new LinkedList<>();
		for (char c = 'a'; c < 'f'; c++)
			stations.add(new EStation(String.valueOf(c), new Position(c, c), town));

		view.updateViewWithStations(stations);
		@on
		*/
	}

	@Override
	public void getLinesByTown(ETown town) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getStationsByLine(ELine line) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getTimetablesByLine(ELine line) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getTownsByLine(ELine line) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLinesByStation(EStation station) {
		// TODO Auto-generated method stub

	}
}
