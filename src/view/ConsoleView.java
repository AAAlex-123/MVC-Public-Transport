package view;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import requirement.util.Requirements;

/**
 * A concrete implementation of the {@link IView} interface that extends the
 * {@link ConsoleView}. It provides the users with a Console Interface with
 * which to use the application.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class ConsoleView extends AbstractView<String> {

	private final Scanner     in;
	private final PrintStream out, err;

	private String sourceEntityDescription = "";

	public ConsoleView() {
		this(System.in, System.out, System.err);
	}

	public ConsoleView(InputStream in, PrintStream out, PrintStream err) {
		super(new StringEntityRepresentationFactory());
		this.in = new Scanner(in);
		this.out = new PrintStream(out);
		this.err = new PrintStream(err);
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
		out.println("3. Insert data into the system");
		out.println("4. Exit");

		switch(getAnswer(1,4)) {
		case 1:
			super.getAllLines();
			break;
		case 2:
			super.getAllTowns();
			break;
		case 3:
			showInsertMenu();
			break;
		case 4:
			System.exit(0);
		}

	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		StringBuffer townString = new StringBuffer("Please select a town: \n");

		int count = 1;
		for (ETown town : towns) {
			final String graphic = factory.getETownGraphic(town);
			townString.append(String.format("%d: %s%n", count, graphic));
			count++;
		}

		out.println(townString);

		ETown selected = towns.get(getAnswer(1, towns.size()) - 1);

		sourceEntityDescription = factory.getETownGraphic(selected);

		out.printf("Select what you want to access from the town: %s%n", selected.getName());
		out.println("1. Show all lines going through this town");
		out.println("2. Show all stations in this town");
		out.println("3. Return to homepage");

		switch (getAnswer(1, 3)) {
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
		for (ELine line : lines) {
			final String graphic = factory.getELineGraphic(line);
			lineString.append(String.format("%d: %s%n", count, graphic));
			count++;
		}

		out.println(lineString);

		ELine selected = lines.get(getAnswer(1, lines.size()) - 1);

		sourceEntityDescription = factory.getELineGraphic(selected);

		out.printf("Select what you want to access from the line %s:%n", selected.getName());
		out.println("1. Show all towns serviced by this line");
		out.println("2. Show all stations serviced by this line");
		out.println("3. Show all arrival times for this line");
		out.println("4. Return to homepage");

		switch (getAnswer(1, 4)) {
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
		for (EStation station : stations) {
			final String graphic = factory.getEStationGraphic(station);
			stationString.append(String.format("%d: %s%n", count, graphic));
			count++;
		}

		out.println(stationString);

		EStation selected = stations.get(getAnswer(1, stations.size()) - 1);

		sourceEntityDescription = factory.getEStationGraphic(selected);
		super.getLinesByStation(selected);
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		printSource();
		StringBuffer timetableString = new StringBuffer("Please select a station: \n");

		int count = 1;
		for (ETimetable timetable : timetables) {
			final String graphic = factory.getETimetableGraphic(timetable);
			timetableString.append(String.format("%d: %s%n", count, graphic));
			count++;
		}

		out.println(timetableString);
		sourceEntityDescription = "";

		updateViewWithHomepage();
	}

	@Override
	public void updateViewWithError(Exception e) {
		err.println(e.getLocalizedMessage());
	}
	
	@Override
	protected void fulfilRequirements(Requirements reqs, String prompt) {
		out.println(prompt);
		
		for (var req : reqs) {
		    if (req instanceof ListRequirement) {
		    	out.println("Please select one of the following options: ");
		    	
		    	@SuppressWarnings("unchecked")
				List<Object> options = ((ListRequirement<Object>) req).getOptions();
		    	StringBuffer optionBuffer = new StringBuffer();
				
				int count = 1;
				for(var option : options) {
					optionBuffer.append(count);
					optionBuffer.append(": ");
					optionBuffer.append(option.toString());
					optionBuffer.append('\n');
					count++;
				}
				
				out.println(optionBuffer);
		        
		       int answer = getAnswer(1, options.size()) - 1;
		       while (!req.finalise(options.get(answer))) {
		           answer = getAnswer(1, options.size()) - 1;
		       }
		        
		    } else if (req instanceof StringRequirement) {
		    	String key = ((StringRequirement) req).key();
		        out.println("give value for: " + key);
		        
		        while(!req.finalise(in.nextLine()))
		        	updateViewWithError(new RuntimeException("This is not a valid value for " + key));
		        	
		    }
		}
	}

	private int getAnswer(int bottomRange, int upperRange) {
		while (true) {
			int answer;
			try {
				answer = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException ne) {
				answer = bottomRange - 1;
			}

			if ((bottomRange <= answer) && (answer <= upperRange))
				return answer;

			updateViewWithError(new RuntimeException(
			        String.format("Please provide a valid answer between %d and %d%n",
			                bottomRange, upperRange)));
		}
	}

	private void showInsertMenu() {
		out.println("1. Register a new line");
		out.println("2. Register a new town");
		out.println("3. Register a new station");
		out.println("4. Register a new station to an existing line");
		out.println("5. Register a new timetable to an existing line");
		out.println("6. Return to homepage");
		
		switch(getAnswer(1,6)) {
		case 1:
			super.insertLine();
			break;
		case 2:
			super.insertTown();
			break;
		case 3:
			super.insertStation();
			break;
		case 4:
			super.insertStationToLine();
			break;
		case 5:
			super.insertTimetableToLine();
			break;
		case 6:
			updateViewWithHomepage();
		}
	
		updateViewWithHomepage();
	}

	private void printSource() {
		if (!sourceEntityDescription.isEmpty())
			out.printf("From %s:%n%n", sourceEntityDescription);
	}
}
