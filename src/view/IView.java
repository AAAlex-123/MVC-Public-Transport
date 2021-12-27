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

	void start();

	void registerController(IController controler);

	void updateViewWithTowns(List<ETown> towns);

	void updateViewWithLines(List<ELine> lines);

	void updateViewWithStations(List<EStation> stations);

	void updateViewWithTimetables(List<ETimetable> timetables);

	void updateViewWithError(Exception e);

}
