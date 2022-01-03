package application;

import controller.Controller;
import controller.IController;
import model.IModel;
import model.Model;
import requirement.util.Requirements;
import view.IView;
import view.TestView;

public class Test {

	public static void main(String[] args) {
		IView       view  = new TestView();
		IModel      model = new Model();
		IController c     = new Controller(model, view);

		Requirements reqs = new Requirements();
		reqs.add("name");
		reqs.fulfil("name", "spata");
		c.insertTown(reqs);
		reqs.fulfil("name", "loutsa");
		c.insertTown(reqs);
		reqs.fulfil("name", "metamorfwsi");
		c.insertTown(reqs);

		c.getAllTowns();
	}
}
