package view;

import java.util.List;

import controller.IController;
import controller.IImageController;
import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

/**
 * An interface for the {@code View} of the {@code MVC architecture}. The View
 * displays to the end-user data in a user-friendly manner. It gathers that data
 * in a suitable format using the {@link controller.IController Controller}
 * interface, which then returns them using this interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public interface IView {

	/** Makes the view accessible to the user and start processing user input */
	void start();

	/**
	 * Sets the Controller of this View to a new one
	 *
	 * @param newController the new controller
	 */
	void registerController(IController newController);

	/**
	 * Sets the Image Controller of this View to a new one
	 *
	 * @param newImageController the new controller
	 */
	void registerImageController(IImageController newImageController);

	/** Request that the view switched to the homepage */
	void updateViewWithHomepage();

	/**
	 * Request that the view switched to show a list of towns.
	 *
	 * @param towns the list of towns
	 */
	void updateViewWithTowns(List<ETown> towns);

	/**
	 * Request that the view switched to show a list of lines.
	 *
	 * @param lines the list of lines
	 */
	void updateViewWithLines(List<ELine> lines);

	/**
	 * Request that the view switched to show a list of stations.
	 *
	 * @param stations the list of stations
	 */
	void updateViewWithStations(List<EStation> stations);

	/**
	 * Request that the view switched to show a list of timetables.
	 *
	 * @param timetables the list of timetables
	 */
	void updateViewWithTimetables(List<ETimetable> timetables);

	/**
	 * Request that the view switched to show an error.
	 *
	 * @param e the error
	 */
	void updateViewWithError(Exception e);
}
