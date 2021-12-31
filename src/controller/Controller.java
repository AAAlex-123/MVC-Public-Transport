package controller;

import java.sql.SQLException;
import java.util.List;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import model.IModel;
import view.IView;

/**
 * An implementation of the {@link IController} interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
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

	private void getTowns(ELine line) {
		try {
			List<ETown> towns = model.getTowns(line);
			view.updateViewWithTowns(towns);
		} catch (SQLException e) {
			view.updateViewWithError(e);
		}
	}

	private void getLines(ETown town, EStation station) {
		try {
			List<ELine> lines = model.getLines(town, station);
			view.updateViewWithLines(lines);
		} catch (SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void getAllLines() {
		getLines(null, null);
	}

	@Override
	public void getAllTowns() {
		getTowns(null);
	}

	@Override
	public void getStationsByTown(ETown town) {
		try {
			List<EStation> stations = model.getStations(town);
			view.updateViewWithStations(stations);
		} catch (SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void getLinesByTown(ETown town) {
		getLines(town, null);
	}

	@Override
	public void getStationsByLine(ELine line) {
		List<EStation> stations = line.getStations();
		view.updateViewWithStations(stations);
	}

	@Override
	public void getTimetablesByLine(ELine line) {
		List<ETimetable> timetables = line.getTimeTables();
		view.updateViewWithTimetables(timetables);
	}

	@Override
	public void getTownsByLine(ELine line) {
		getTowns(line);
	}

	@Override
	public void getLinesByStation(EStation station) {
		getLines(null, station);
	}

	@Override
	public void insertTown(ETown town) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertLine(ELine line) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertStation(EStation station) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertTimetable(ETimetable timetable) {
		// TODO Auto-generated method stub

	}
}
