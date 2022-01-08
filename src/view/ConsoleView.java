package view;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import controller.IController;
import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

public class ConsoleView extends AbstractView<String> {
	private final PrintStream out;
	private final PrintStream err;
	private final Scanner in;
	
	private String sourceEntityDescription = "";

	public ConsoleView(IController controller, PrintStream out, PrintStream err, InputStream in) {
		super(new StringEntityGraphicFactory(), controller);
		this.out = new PrintStream(out);
		this.in = new Scanner(in);
		this.err = new PrintStream(err);
	}
	
	public ConsoleView(IController controller) {
		this(controller, System.out, System.err, System.in);
	}

	@Override
	public void start() {
		out.println("Welcome! Please select one of the options below:");
		updateViewWithHomepage();
	}

	@Override
	public void updateViewWithHomepage() {
		sourceEntityDescription = "";
		
		out.println("1. Show all Lines");
		out.println("2. Show all Towns");
		out.println("3. Exit");

		switch(getAnswer(1,3)) {
		case 1:
			super.getAllLines();
			break;
		case 2:
			super.getAllTowns();
			break;
		case 3:
			System.exit(0);
		}

	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		StringBuffer townString = new StringBuffer("Please select a town: \n");
		
		int count = 1;
		for(var town : towns) {
			townString.append(count);
			townString.append(": ");
			townString.append(factory.getETownGraphic(town));
			townString.append('\n');
			count++;
		}
		
		out.println(townString);
		
		ETown selected = towns.get(getAnswer(1, towns.size()) - 1);
		
		sourceEntityDescription = factory.getETownGraphic(selected);
		
		out.println("Select what you want to access from the town "+ selected.getName() +":");
		out.println("1. Show all lines going through this town");
		out.println("2. Show all stations in this town");
		out.println("3. Return to homepage");

		switch(getAnswer(1,3)) {
		case 1:
			super.getLinesByTown(selected);
			break;
		case 2:
			super.getStationsByTown(selected);
			break;
		case 3:
			updateViewWithHomepage();
			break;
		}
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		printSource();
		StringBuffer lineString = new StringBuffer("Please select a line: \n");
		
		int count = 1;
		for(var line : lines) {
			lineString.append(count);
			lineString.append(": ");
			lineString.append(factory.getELineGraphic(line));
			lineString.append('\n');
			count++;
		}
		
		out.println(lineString);
		
		ELine selected = lines.get(getAnswer(1, lines.size()) - 1);
		
		sourceEntityDescription = factory.getELineGraphic(selected);
		
		out.println("Select what you want to access from the line "+ selected.getLineNumber() +":");
		out.println("1. Show all towns serviced by this line");
		out.println("2. Show all stations serviced by this line");
		out.println("3. Show all arrival times for this line");
		out.println("4. Return to homepage");

		switch(getAnswer(1,4)) {
		case 1:
			super.getTownsByLine(selected);
			break;
		case 2:
			super.getStationsByLine(selected);
			break;
		case 3:
			super.getTimetablesByLine(selected);
			break;
		case 4:
			updateViewWithHomepage();
			break;
		}
	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		printSource();
		StringBuffer stationString = new StringBuffer("Please select a station: \n");
		
		int count = 1;
		for(var station : stations) {
			stationString.append(count);
			stationString.append(": ");
			stationString.append(factory.getEStationGraphic(station));
			stationString.append('\n');
			count++;
		}
		
		out.println(stationString);
		
		EStation selected = stations.get(getAnswer(1, stations.size())-1);
		
		sourceEntityDescription = factory.getEStationGraphic(selected);
		super.getLinesByStation(selected);
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		printSource();
		StringBuffer timeString = new StringBuffer("Please select a station: \n");
		
		int count = 1;
		for(var time : timetables) {
			timeString.append(count);
			timeString.append(": ");
			timeString.append(factory.getETimetableGraphic(time));
			timeString.append('\n');
			count++;
		}
		
		out.println(timeString);
		sourceEntityDescription = "";
		
		updateViewWithHomepage();
	}

	@Override
	public void updateViewWithError(Exception e) {
		err.println(e.getLocalizedMessage());
	}
	
	private int getAnswer(int bottomRange, int upperRange) {
		int answer = Integer.MIN_VALUE;
		do {
			
			try {
				answer = Integer.parseInt(in.nextLine());
			} catch(NumberFormatException ne) {
				answer = Integer.MIN_VALUE;
			}
			
			if(!(answer >= bottomRange && answer <= upperRange))
				updateViewWithError(new RuntimeException(
						String.format("Please provide a valid answer between %d and %d.", bottomRange, upperRange)));
			
		} while(!(answer >= bottomRange && answer <= upperRange));
		
		return answer;
	}
	
	private void printSource() {
		if(sourceEntityDescription != "")
			out.println("From " + sourceEntityDescription + ":\n");
	}
	
}
