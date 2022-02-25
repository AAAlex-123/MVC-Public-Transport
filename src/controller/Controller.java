package controller;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import entity.ELine;
import entity.EStation;
import entity.ETimestamp;
import entity.ETown;
import entity.LineType;
import entity.Position;
import localisation.ControllerStrings;
import model.IModel;
import requirement.requirements.StringType;
import requirement.util.Requirements;
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
			final List<ETown> towns = model.getTowns(line);
			view.updateViewWithTowns(towns);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	private void getLines(ETown town, EStation station) {
		try {
			final List<ELine> lines = model.getLines(town, station);
			view.updateViewWithLines(lines);
		} catch (final SQLException e) {
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
			final List<EStation> stations = model.getStations(town);
			view.updateViewWithStations(stations);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void getLinesByTown(ETown town) {
		getLines(town, null);
	}

	@Override
	public void getStationsByLine(ELine line) {

		final List<ELine> lines;
		try {
			lines = model.getLines(null, null);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
			return;
		}

		for (ELine fetchedLine : lines)
			if (fetchedLine.getId() == line.getId()) {
				view.updateViewWithStations(fetchedLine.getStations());
				return;
			}

		view.updateViewWithError(new RuntimeException(
		        String.format(ControllerStrings.NO_LINE_FOUND, line.getName())));
	}

	@Override
	public void getTimetablesByLine(ELine line) {

		final List<ELine> lines;
		try {
			lines = model.getLines(null, null);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
			return;
		}

		for (ELine fetchedLine : lines)
			if (fetchedLine.getId() == line.getId()) {
				view.updateViewWithTimestamps(fetchedLine.getTimetables());
				return;
			}

		view.updateViewWithError(new RuntimeException(
		        String.format(ControllerStrings.NO_LINE_FOUND, line.getName())));
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
		if (!reqs.fulfilled()) {
			view.updateViewWithError(new RuntimeException(ControllerStrings.PLEASE_FILL));
			return;
		}

		final ETown newTown = new ETown(-1, reqs.getValue(ControllerStrings.NAME, String.class));
		try {
			model.insertTown(newTown);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void insertLine(Requirements reqs) {
		if (!reqs.fulfilled()) {
			view.updateViewWithError(new RuntimeException(ControllerStrings.PLEASE_FILL));
			return;
		}

		final String   name        = reqs.getValue(ControllerStrings.NAME, String.class);
		final LineType type        = reqs.getValue(ControllerStrings.TYPE, LineType.class);
		final String   description = reqs.getValue(ControllerStrings.DESCRIPTION, String.class);

		final ELine newLine = new ELine(-1, name, type, description, null, null);
		try {
			model.insertLine(newLine);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void insertStation(Requirements reqs) {
		if (!reqs.fulfilled()) {
			view.updateViewWithError(new RuntimeException(ControllerStrings.PLEASE_FILL));
			return;
		}

		final Function<String, Double> dtoi = Double::parseDouble;

		final double   x_coord  = dtoi
		        .apply(reqs.getValue(ControllerStrings.X_COORD, String.class));
		final double   y_coord  = dtoi
		        .apply(reqs.getValue(ControllerStrings.Y_COORD, String.class));
		final Position position = new Position(x_coord, y_coord);

		final EStation newStation = new EStation(-1,
		        reqs.getValue(ControllerStrings.NAME, String.class),
		        position,
		        reqs.getValue(ControllerStrings.CITY, ETown.class));
		try {
			model.insertStation(newStation);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void insertStationToLine(Requirements reqs) {
		if (!reqs.fulfilled()) {
			view.updateViewWithError(new RuntimeException(ControllerStrings.PLEASE_FILL));
			return;
		}

		final Function<String, Integer> stoi = Integer::parseInt;

		final ELine    line    = reqs.getValue(ControllerStrings.LINE, ELine.class);
		final EStation station = reqs.getValue(ControllerStrings.STATION, EStation.class);
		final int      index   = stoi.apply(reqs.getValue(ControllerStrings.INDEX, String.class));

		try {
			model.insertStationToLine(line, station, index);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public void insertTimetableToLine(Requirements reqs) {
		if (!reqs.fulfilled()) {
			view.updateViewWithError(new RuntimeException(ControllerStrings.PLEASE_FILL));
			return;
		}

		final Function<String, Integer> stoi = Integer::parseInt;

		final ELine      line      = reqs.getValue(ControllerStrings.LINE, ELine.class);
		final int        hours     = stoi
		        .apply(reqs.getValue(ControllerStrings.HOURS, String.class));
		final int        minutes   = stoi
		        .apply(reqs.getValue(ControllerStrings.MINUTES, String.class));
		final ETimestamp timetable = new ETimestamp(hours, minutes);

		try {
			model.insertTimetableToLine(line, timetable);
		} catch (final SQLException e) {
			view.updateViewWithError(e);
		}
	}

	@Override
	public Requirements getInsertTownRequirements() {
		final Requirements reqs = new Requirements();
		reqs.add(ControllerStrings.NAME, StringType.NON_EMPTY);
		return reqs;
	}

	@Override
	public Requirements getInsertLineRequirements() {
		final Requirements reqs = new Requirements();
		reqs.add(ControllerStrings.NAME, StringType.NON_EMPTY);
		reqs.add(ControllerStrings.TYPE, Arrays.asList(LineType.values()));
		reqs.add(ControllerStrings.DESCRIPTION, StringType.NON_EMPTY);
		return reqs;
	}

	@Override
	public Requirements getInsertStationRequirements() {
		List<ETown> availableTowns;
		try {
			availableTowns = model.getTowns(null);
		} catch (final SQLException e) {
			view.updateViewWithError(new RuntimeException(
			        String.format(ControllerStrings.CANT_FETCH, ControllerStrings.TOWNS), e));
			return null;
		}

		final Requirements reqs = new Requirements();
		reqs.add(ControllerStrings.NAME, StringType.NON_EMPTY);
		reqs.add(ControllerStrings.X_COORD, StringType.NON_NEG_INTEGER);
		reqs.add(ControllerStrings.Y_COORD, StringType.NON_NEG_INTEGER);
		reqs.add(ControllerStrings.CITY, availableTowns);
		return reqs;
	}

	@Override
	public Requirements getInsertStationToLineRequirements() {
		List<ELine>    lines;
		List<EStation> stations;

		try {
			lines = model.getLines(null, null);
		} catch (final SQLException e) {
			view.updateViewWithError(new RuntimeException(
			        String.format(ControllerStrings.CANT_FETCH, ControllerStrings.LINES), e));
			return null;
		}

		try {
			stations = model.getStations(null);
		} catch (final SQLException e) {
			view.updateViewWithError(new RuntimeException(
			        String.format(ControllerStrings.CANT_FETCH, ControllerStrings.STATIONS), e));
			return null;
		}

		final Requirements reqs = new Requirements();
		reqs.add(ControllerStrings.LINE, lines, l -> l.getName());
		reqs.add(ControllerStrings.STATION, stations, s -> s.getName());
		reqs.add(ControllerStrings.INDEX, StringType.NON_NEG_INTEGER);
		return reqs;
	}

	@Override
	public Requirements getInsertTimetableToLineRequirements() {
		List<ELine> lines;

		try {
			lines = model.getLines(null, null);
		} catch (final SQLException e) {
			view.updateViewWithError(new RuntimeException(
			        String.format(ControllerStrings.CANT_FETCH, ControllerStrings.LINES), e));
			return null;
		}

		final Requirements reqs = new Requirements();
		reqs.add(ControllerStrings.LINE, lines, l -> l.getName());
		reqs.add(ControllerStrings.HOURS, StringType.HOUR_24);
		reqs.add(ControllerStrings.MINUTES, StringType.MINUTE);
		return reqs;
	}
}
