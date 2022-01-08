package view;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.CImageLoader;
import controller.Controller;
import controller.IController;
import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import entity.LineType;
import entity.Position;
import model.IModel;
import model.MLocalImageSource;
import model.Model;

public class FactoryTest {

	public static void main(String[] args) {
		IModel      model      = new Model();
		AbstractGUIView       view       = new OASAView(new CImageLoader(new MLocalImageSource()));
		IController controller = new Controller(model, view);

		view.registerController(controller);

		AbstractEntityGraphicFactory<String> f = new StringEntityGraphicFactory();
		System.out.println(f.getELineGraphic(new ELine(45, "A8", LineType.BUS, "Marousi-Hrakleio-Polytechnio",  new LinkedList<EStation>(), new LinkedList<ETimetable>())));
		System.out.println(f.getETownGraphic(new ETown(56, "Dimos Spaton-Metamorphosis")));
		System.out.println(f.getEStationGraphic(new EStation(23, "Fournos", new Position(56,67), new ETown(23, "Spata"))));
		System.out.println(f.getETimetableGraphic(new ETimetable(23, 50)));

	}

}
