package view;

import controller.IController;
import controller.IImageController;
import entity.ELine;
import entity.EStation;
import entity.ETown;
import requirement.util.Requirements;

/**
 * An abstract implementation of the {@link IView} interface which defines
 * delegate methods to the View's registered {@link IController}, which the
 * concrete implementations will call.
 *
 * @param <EntityType> the type of the representation of the Entities
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
abstract class AbstractView<EntityType> implements IView {

	private IController controller;

	/** The factory that constructs graphics for the Entities this View displays */
	protected final AbstractEntityRepresentationFactory<EntityType, ? extends AbstractView<? extends EntityType>> factory;

	/**
	 * Constructs the view initialising its UI and providing a factory with which to
	 * construct its graphics. The factory can be changed to provide different
	 * graphics while maintaining the same layout.
	 *
	 * @param factory the factory that will be used to construct its graphics
	 */
	public AbstractView(
	        AbstractEntityRepresentationFactory<EntityType, ? extends AbstractView<? extends EntityType>> factory) {
		this.factory = factory;
	}

	@Override
	public final void registerController(IController newController) {
		controller = newController;
	}

	@Override
	public void registerImageController(IImageController newImageController) {}

	// ---------- AbstractView Commands ---------- //

	/**
	 * A wrapper method for {@link IController#getStationsByTown(ETown)} that also
	 * updates the source panel of the view.
	 *
	 * @param town the town from which the stations will be selected
	 */
	protected void getStationsByTown(ETown town) {
		controller.getStationsByTown(town);
	}

	/**
	 * A wrapper method for {@link IController#getLinesByTown(ETown)} that also
	 * updates the source panel of the view.
	 *
	 * @param town the town from which the lines will be selected
	 */
	protected void getLinesByTown(ETown town) {
		controller.getLinesByTown(town);
	}

	/**
	 * A wrapper method for {@link IController#getStationsByLine(ELine)} that also
	 * updates the source panel of the view.
	 *
	 * @param line the line from which the stations will be selected
	 */
	protected void getStationsByLine(ELine line) {
		controller.getStationsByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getTimetablesByLine(ELine)} that also
	 * updates the source panel of the view.
	 *
	 * @param line the line from which the timetables will be selected
	 */
	protected void getTimetablesByLine(ELine line) {
		controller.getTimetablesByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getTownsByLine(ELine)} that also
	 * updates the source panel of the view.
	 *
	 * @param line the line from which the timetables will be selected
	 */
	protected void getTownsByLine(ELine line) {
		controller.getTownsByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getLinesByStation(EStation)} that
	 * also updates the source panel of the view.
	 *
	 * @param station the station from which the timetables will be selected
	 */
	protected void getLinesByStation(EStation station) {
		controller.getLinesByStation(station);
	}

	/**
	 * A wrapper method for {@link IController#getAllTowns()} that also resets the
	 * source panel of the view.
	 */
	protected void getAllTowns() {
		controller.getAllTowns();
	}

	/**
	 * A wrapper method for {@link IController#getAllLines()} that also resets the
	 * source panel of the view.
	 */
	protected void getAllLines() {
		controller.getAllLines();
	}

	protected final void insertTown() {
		Requirements reqs = controller.getInsertTownRequirements();
		fulfilRequirements(reqs, "Insert Town Parameters");
		if (reqs.fulfilled())
			controller.insertTown(reqs);
	}

	protected final void insertLine() {
		Requirements reqs = controller.getInsertLineRequirements();
		fulfilRequirements(reqs, "Insert Line Parameters");
		if (reqs.fulfilled())
			controller.insertLine(reqs);
	}

	protected final void insertStation() {
		Requirements reqs = controller.getInsertStationRequirements();
		fulfilRequirements(reqs, "Insert Station Parameters");
		if (reqs.fulfilled())
			controller.insertStation(reqs);
	}

	protected final void insertStationToLine() {
		Requirements reqs = controller.getInsertStationToLineRequirements();
		fulfilRequirements(reqs, "Insert Station to Line Parameters");
		if (reqs.fulfilled())
			controller.insertStationToLine(reqs);
	}

	protected final void insertTimetableToLine() {
		Requirements reqs = controller.getInsertTimetableToLineRequirements();
		fulfilRequirements(reqs, "Insert Timetable to Line Parameters");
		if (reqs.fulfilled())
			controller.insertTimetableToLine(reqs);
	}

	/**
	 * Allows each subclass to define how to fulfil a {@link Requirements}.
	 *
	 * @param reqs   the Requirements object to fulfil
	 * @param prompt the prompt for the fulfilment, which may be displayed to the
	 *               user
	 */
	protected abstract void fulfilRequirements(Requirements reqs, String prompt);
}
