package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import controller.IController;
import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import entity.LineType;
import entity.Position;

public class ViewTest {
	protected static final int WINDOW_HEIGHT = 750;
	protected static final int WINDOW_WIDTH = 1000;
	
	public static void main(String[] args) {
		IView       view       = new OASAView();
		IController controller = new TestController(view);
		view.registerController(controller);
		
		view.start();
		
		controller.getAllLines();
	}
	
	
	private static class TestController implements IController {
		
		private final IView view;
		
		public TestController(IView view) {
			this.view = view;
		}
		
		@Override
		public Image loadImage(String name, int maxWidth, int maxHeight) {
			BufferedImage img;
			try {
				File file = getResourcePath(name);
				img = ImageIO.read(file);
			} catch(IOException ioe) {
				img = new BufferedImage(54, 54, BufferedImage.TYPE_INT_RGB); //create empty image
			}
			
			if(img.getHeight() > maxHeight || img.getWidth() > maxWidth)
				return img.getScaledInstance(maxWidth, maxHeight,  java.awt.Image.SCALE_SMOOTH);
			else
				return img;
				
		}

		@Override
		public void getAllLines() {
			view.updateViewWithLines(getLines());
		}

		@Override
		public void getAllTowns() {
			view.updateViewWithTowns(getTowns());
		}

		@Override
		public void getStationsByTown(ETown town) {
			LinkedList<EStation> validStations = new LinkedList<EStation>();
			
			for(var station : getStations())
				if(station.getTown().getId() == town.getId())
					validStations.add(station);
			
			view.updateViewWithStations(validStations);
		}

		@Override
		public void getLinesByTown(ETown town) {
			//ok this is epic, lines dont have towns in them
			getAllLines();
		}

		@Override
		public void getStationsByLine(ELine line) {
			view.updateViewWithStations(line.getStations());
		}

		@Override
		public void getTimetablesByLine(ELine line) {
			view.updateViewWithTimetables(line.getTimeTables());
		}

		@Override
		public void getTownsByLine(ELine line) {
			getAllTowns();	
		}

		@Override
		public void getLinesByStation(EStation station) {
			view.updateViewWithStations(getStations());
		}

		@Override
		public void insertTown(ETown town) {}

		@Override
		public void insertLine(ELine line) {}

		@Override
		public void insertStation(EStation station) {}

		@Override
		public void insertTimetable(ETimetable timetable) {}
		
		private static List<ETown> getTowns() {
			ETown town1 = new ETown(23, "Spata");
			ETown town2 = new ETown(24, "Argyroupoli");
			ETown town3 = new ETown(25, "Metamorphosi");
			ETown town4 = new ETown(26, "Kentro");
			ETown town5 = new ETown(27, "Peuki-Lykobrisi");
			
			LinkedList<ETown> towns = new LinkedList<ETown>();
			towns.add(town1);
			towns.add(town2);
			towns.add(town3);
			towns.add(town4);
			towns.add(town5);
			
			return towns;
		}
		
		private static List<ETimetable> getTimetables() {
			ETimetable t1 = new ETimetable(23, 12, 34);
			ETimetable t2 = new ETimetable(24, 12, 36);
			ETimetable t3 = new ETimetable(25, 12, 40);
			ETimetable t4 = new ETimetable(26, 12, 50);
			ETimetable t5 = new ETimetable(27, 13, 00);
			ETimetable t6 = new ETimetable(28, 14, 25);
			ETimetable t7 = new ETimetable(29, 14, 55);
			ETimetable t8 = new ETimetable(30, 15, 02);
			ETimetable t9 = new ETimetable(31, 16, 34);
			
			LinkedList<ETimetable> times = new LinkedList<ETimetable>();
			times.add(t1);
			times.add(t2);
			times.add(t3);
			times.add(t4);
			times.add(t5);
			times.add(t6);
			times.add(t7);
			times.add(t8);
			times.add(t9);
			
			return times;
		}
		
		private static List<ELine> getLines() {
			List<ETimetable> times = getTimetables();
			List<EStation> stations = getStations();
			ELine l1 = new ELine(23, "A8", LineType.BUS, "Marousi-Nea Ionia-Polytechneio", stations.subList(0, 3), times.subList(0, 3));
			ELine l2 = new ELine(2, "Line 2", LineType.SUBWAY, "Anthoupoli-Elliniko", stations.subList(2, 4), times.subList(6, 8));
			ELine l3 = new ELine(16, "Line 6", LineType.TRAM, "Syntagma-Asklipeio Voulas", stations.subList(0, 5), times.subList(3, 7));
			
			LinkedList<ELine> lines = new LinkedList<ELine>();
			lines.add(l1);
			lines.add(l2);
			lines.add(l3);
			
			return lines;
		}
		
		private static List<EStation> getStations() {
			List<ETown> towns = getTowns();
			EStation s1 = new EStation(45, "Nea Makri", new Position(23, 45), towns.get(1));
			EStation s2 = new EStation(56, "Strofi Peukis", new Position(28, 52), towns.get(1));
			EStation s3 = new EStation(78, "Plateia Pindou", new Position(67, -67), towns.get(2));
			EStation s4 = new EStation(89, "Fournos", new Position(23, 45), towns.get(0));
			EStation s5 = new EStation(45, "Sxoleio", new Position(38, 89), towns.get(0));
			
			LinkedList<EStation> stations = new LinkedList<EStation>();
			stations.add(s1);
			stations.add(s2);
			stations.add(s3);
			stations.add(s4);
			stations.add(s5);
			
			return stations;
		}
		
		private static File getResourcePath(String spriteName) {
			Path path = Paths.get(System.getProperty("user.dir"));
			String resource_dir = path.toString() + File.separator + "other_resources" + File.separator + "resources";
			String icon_path = resource_dir + File.separator + spriteName;
			return new File(icon_path);
		}
		
	}
}