package controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import entity.LineType;
import entity.Position;
import model.IModel;
import requirement.util.Requirements;
import view.IView;

/**
 * An implementation of the {@link IController} interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
@SuppressWarnings("nls")
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
		List<ETimetable> timetables = line.getTimetables();
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
	public void insertTown(Requirements reqs) {
		ETown newTown = new ETown(-1, reqs.getValue("name", String.class));
		try {
			model.insertTown(newTown);
		} catch (SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void insertLine(Requirements reqs) {
		final String   lineNo      = reqs.getValue("lineNo", String.class);
		final LineType type        = reqs.getValue("type", LineType.class);
		final String   description = reqs.getValue("description", String.class);

		final ELine newLine = new ELine(-1, lineNo, type, description, null, null);
		try {
			model.insertLine(newLine);
		} catch (SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void insertStation(Requirements reqs) {
		final Function<String, Double> dtoi = Double::parseDouble;

		final double   x_coord  = dtoi.apply(reqs.getValue("x coord", String.class));
		final double   y_coord  = dtoi.apply(reqs.getValue("y coord", String.class));
		final Position position = new Position(x_coord, y_coord);

		EStation newStation = new EStation(-1,
		        reqs.getValue("name", String.class),
		        position,
		        reqs.getValue("city id", ETown.class));
		try {
			model.insertStation(newStation);
		} catch (SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void insertStationToLine(Requirements reqs) {
		final Function<String, Integer> stoi = Integer::parseInt;

		final ELine    line    = reqs.getValue("line", ELine.class);
		final EStation station = reqs.getValue("station", EStation.class);
		final int      index   = stoi.apply(reqs.getValue("index", String.class));

		try {
			model.insertStationToLine(line, station, index);
		} catch (SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void insertTimetableToLine(Requirements reqs) {
		final Function<String, Integer> stoi = Integer::parseInt;

		final ELine      line      = reqs.getValue("line", ELine.class);
		final int        hours     = stoi.apply(reqs.getValue("hours", String.class));
		final int        minutes   = stoi.apply(reqs.getValue("minutes", String.class));
		final ETimetable timetable = new ETimetable(hours, minutes);

		try {
			model.insertTimetableToLine(line, timetable);
		} catch (SQLException e) {
			view.updateViewWithError(e);
		}
	}
	
	@Override 
	public Image loadImage(String name, int maxWidth, int maxHeight) {
		BufferedImage img;
		try {
			img = model.loadImage(name);
		} catch(IOException ioe) {
			img = new BufferedImage(54, 54, BufferedImage.TYPE_INT_RGB); //create empty image
		}
		
		if(img.getHeight() > maxHeight || img.getWidth() > maxWidth)
			return img.getScaledInstance(maxWidth, maxHeight,  java.awt.Image.SCALE_SMOOTH);
		else
			return img;
			
	}
}
