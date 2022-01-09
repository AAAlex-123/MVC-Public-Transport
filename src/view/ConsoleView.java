package view;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import requirement.requirements.AbstractRequirement;
import requirement.requirements.ListRequirement;
import requirement.requirements.StringRequirement;
import requirement.util.Requirements;

/**
 * A concrete implementation of the {@link IView} interface that extends the
 * {@link ConsoleView}. It provides the users with a Console Interface with
 * which to use the application.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
@SuppressWarnings("nls")
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
		out.println("3. Insert data");
		out.println("4. Exit");

		doWithAnswer(
		        () -> super.getAllLines(),
		        () -> super.getAllTowns(),
		        () -> showInsertMenu(),
		        () -> System.exit(0));
	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		printSource();
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

		doWithAnswer(() -> super.getLinesByTown(selected), () -> super.getStationsByTown(selected),
		        () -> updateViewWithHomepage());
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

		doWithAnswer(
		        () -> super.getTownsByLine(selected),
		        () -> super.getStationsByLine(selected),
		        () -> super.getTimetablesByLine(selected),
		        () -> updateViewWithHomepage());
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

		out.printf("Select what you want to access from the station %s:%n", selected.getName());
		out.println("1. Show all lines going through this station");
		out.println("2. Return to homepage");

		doWithAnswer(
		        () -> super.getLinesByStation(selected),
		        () -> updateViewWithHomepage());
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		printSource();
		StringBuffer timetableString = new StringBuffer();

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

		for (AbstractRequirement req : reqs) {
			if (req instanceof ListRequirement) {
				out.println("Please select one of the following options: ");

				@SuppressWarnings("unchecked")
				List<Object> options      = ((ListRequirement<Object>) req).getOptions();
				StringBuffer optionBuffer = new StringBuffer();

				int count = 1;
				for (Object option : options) {
					optionBuffer.append(String.format("%d: %s%n", count, option.toString()));
					count++;
				}

				out.println(optionBuffer);

				int answer = getAnswer(1, options.size()) - 1;
				while (!req.finalise(options.get(answer))) {
					answer = getAnswer(1, options.size()) - 1;
					System.out.println("lmao can this even happen?");
				}

			} else if (req instanceof StringRequirement) {
				StringRequirement stringReq = (StringRequirement) req;
				String            key       = stringReq.key();

				out.printf("Give a value for: %s%n", key);

				while (!req.finalise(in.nextLine()))
					updateViewWithError(new RuntimeException(
					        String.format("Invalid value for key '%s'. Please provide a(n): %s%n",
					                stringReq.stringType.getDescription())));
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

	private void doWithAnswer(Runnable... runnables) {
		final int answer = getAnswer(1, runnables.length);
		runnables[answer - 1].run();
	}

	private void showInsertMenu() {
		out.println("1. Insert a new Line");
		out.println("2. Insert a new Town");
		out.println("3. Insert a new Station");
		out.println("4. Insert an existing Station to a Line");
		out.println("5. Insert a new Timetable to a Line");
		out.println("6. Return to homepage");

		doWithAnswer(
			() -> super.insertLine(),
			() -> super.insertTown(),
			() -> super.insertStation(),
			() -> super.insertStationToLine(),
			() -> super.insertTimetableToLine()
		// ignore 6 - return to homepage
		);

		updateViewWithHomepage();
	}

	private void printSource() {
		if (!sourceEntityDescription.isEmpty())
			out.printf("From %s:%n%n", sourceEntityDescription);
	}
}
