package application;

import controller.Controller;
import controller.IController;
import model.IModel;
import model.Model;
import view.IView;
import view.OASAView;

/**
 * The entry point of the program. Constructs and runs an {@link view.IView
 * View}.
 *
 * @author Alex Mandelias
 */
public class App {

	/**
	 * Constructs and runs an Application.
	 *
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {

		IModel      model      = new Model();
		IView       view       = new OASAView();
		IController controller = new Controller(model, view);

		view.registerController(controller);

		view.start();
	}
}
