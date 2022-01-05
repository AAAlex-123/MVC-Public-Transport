package view;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import controller.Controller;
import controller.IController;
import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import entity.LineType;
import entity.Position;
import model.IModel;
import model.Model;

public class FactoryTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame();

		IModel      model      = new Model();
		AbstractView       view       = new OASAView();
		IController controller = new Controller(model, view);

		view.registerController(controller);

		AbstractEntityGraphicFactory f = new OASAEntityGraphicFactory(view);
		frame.setSize(new Dimension(500, 500));
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(f.getELineGraphic(new ELine(45, "A8", LineType.BUS, "Marousi-Hrakleio-Polytechnio",  new LinkedList<EStation>(), new LinkedList<ETimetable>())));
		frame.add(f.getETownGraphic(new ETown(56, "Dimos Spaton-Metamorphosis")));
		frame.add(f.getEStationGraphic(new EStation(23, "Fournos", new Position(56,67), new ETown(23, "Spata"))));
		frame.add(f.getETimetableGraphic(new ETimetable(23, 50)));

		frame.setVisible(true);
	}

}
