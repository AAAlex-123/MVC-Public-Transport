package view;

import java.util.List;

import controller.IController;
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
	
	/**
	 * Makes the view accessible to the user and begins user input 
	 * processing.
	 */
	void start();
	
	/**
	 * Switch the {@link IController controller} of this view.
	 * 
	 * @param controller the new controller
	 */
	void registerController(IController controller);

	/**
	 * Request that the view switches to the homepage.
	 */
	void updateViewWithHomepage();
	
	/**
	 * Request that the view shows a list of towns.
	 * 
	 * @param towns a list with towns to be displayed
	 */
	void updateViewWithTowns(List<ETown> towns);
	
	/**
	 * Request that the view shows a list of lines
	 * 
	 * @param lines a list with lines to be displayed
	 */
	void updateViewWithLines(List<ELine> lines);
	
	/**
	 * Request that the view shows a list of stations.
	 * 
	 * @param stations a list with stations to be displayed
	 */
	void updateViewWithStations(List<EStation> stations);
	
	/**
	 * Request that the view shows a list of timetables.
	 * 
	 * @param lines a list with timetables to be displayed
	 */
	void updateViewWithTimetables(List<ETimetable> timetables);
	
	/**
	 * Notify the user about an error.
	 * 
	 * @param e the source of the error
	 */
	void updateViewWithError(Exception e);
	

}
