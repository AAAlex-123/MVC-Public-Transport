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

		printMenu(new String[] {
		        "Show all Lines",
		        "Show all Towns",
		        "Insert data",
		        "Exit",
		}, "");

		doWithAnswer(
		        () -> super.getAllLines(),
		        () -> super.getAllTowns(),
		        () -> showInsertMenu(),
		        () -> System.exit(0));
	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		if (towns.isEmpty()) {
			goToHomepage("towns");
		}

		printSource();

		FormatBuffer buffer = new FormatBuffer("Please select a town: \n");

		for (ETown town : towns) {
			buffer.format(factory.getETownGraphic(town));
		}

		out.print(buffer);

		ETown selected = towns.get(getAnswer(1, towns.size()) - 1);

		sourceEntityDescription = factory.getETownGraphic(selected);

		printMenu(new String[] {
		        "Show all lines going through this town",
		        "Show all stations in this town",
		        "Return to homepage",
		},
		        "Select what you want to access from the town: %s%n", selected.getName());

		doWithAnswer(() -> super.getLinesByTown(selected), () -> super.getStationsByTown(selected),
		        () -> updateViewWithHomepage());
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		if (lines.isEmpty()) {
			goToHomepage("lines");
		}

		printSource();
		FormatBuffer buffer = new FormatBuffer("Please select a line:\n");

		for (ELine line : lines) {
			buffer.format(factory.getELineGraphic(line));
		}

		out.print(buffer);

		ELine selected = lines.get(getAnswer(1, lines.size()) - 1);

		sourceEntityDescription = factory.getELineGraphic(selected);

		printMenu(new String[] {
		        "Show all towns serviced by this line",
		        "Show all stations serviced by this line",
		        "Show all arrival times for this line",
		        "Return to homepage",
		}, "Select what you want to access from the line %s:%n", selected.getName());

		doWithAnswer(
		        () -> super.getTownsByLine(selected),
		        () -> super.getStationsByLine(selected),
		        () -> super.getTimetablesByLine(selected),
		        () -> updateViewWithHomepage());
	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		if (stations.isEmpty()) {
			goToHomepage("stations");
		}

		printSource();
		FormatBuffer buffer = new FormatBuffer("Please select a station:\n");

		for (EStation station : stations) {
			buffer.format(factory.getEStationGraphic(station));
		}

		out.print(buffer);

		EStation selected = stations.get(getAnswer(1, stations.size()) - 1);

		sourceEntityDescription = factory.getEStationGraphic(selected);

		printMenu(new String[] {
		        "Show all lines going through this station",
		        "Return to homepage",
		}, "Select what you want to access from the station %s:%n", selected.getName());

		doWithAnswer(
		        () -> super.getLinesByStation(selected),
		        () -> updateViewWithHomepage());
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		if (timetables.isEmpty()) {
			goToHomepage("timetables");
		}

		printSource();
		FormatBuffer buffer = new FormatBuffer();

		for (ETimetable timetable : timetables) {
			buffer.format(factory.getETimetableGraphic(timetable));
		}

		out.print(buffer);

		sourceEntityDescription = "";

		updateViewWithHomepage();
	}

	@Override
	public void updateViewWithError(Exception e) {
		err.println(e.getLocalizedMessage());
	}

	@Override
	protected void fulfilRequirements(Requirements reqs, String prompt) {
		out.println(System.lineSeparator() + prompt);

		for (AbstractRequirement req : reqs) {
			if (req instanceof ListRequirement) {
				out.println("Please select one of the following options:");

				@SuppressWarnings("unchecked")
				final List<Object> options = ((ListRequirement<Object>) req).getOptions();
				final FormatBuffer buffer  = new FormatBuffer();

				for (Object option : options)
					buffer.format(option.toString());

				out.print(buffer);

				int answer = getAnswer(1, options.size()) - 1;
				while (!req.finalise(options.get(answer))) {
					answer = getAnswer(1, options.size()) - 1;
					System.out.println("lmao can this even happen?");
				}

			} else if (req instanceof StringRequirement) {
				final StringRequirement stringReq = (StringRequirement) req;
				final String            key       = stringReq.key();

				out.printf("Give a value for: %s%n", key);

				while (!req.finalise(in.nextLine()))
					err.printf("Invalid value for key '%s'. Please provide a(n): %s%n",
					        stringReq.stringType.getDescription());
			}
		}
	}

	private static class FormatBuffer {

		private final StringBuffer buffer;
		private int                count;

		public FormatBuffer() {
			this("");
		}

		public FormatBuffer(String initial) {
			buffer = new StringBuffer(System.lineSeparator() + initial);
			count = 1;
		}

		public void format(String format, Object... args) {
			final String preparedString = String.format("%d: %s%n", count, format);
			buffer.append(String.format(preparedString, args));
			count++;
		}

		@Override
		public String toString() {
			return buffer.toString();
		}
	}

	private void goToHomepage(String reason) {
		out.printf("%nNo %s to show%n", reason);
		updateViewWithHomepage();
	}

	private void printMenu(String[] menuItems, String headerFormat, Object... headerArgs) {
		final FormatBuffer buffer = new FormatBuffer(String.format(headerFormat, headerArgs));
		for (final String menuItem : menuItems)
			buffer.format(menuItem);

		out.print(buffer);
	}

	private void doWithAnswer(Runnable... runnables) {
		final int answer = getAnswer(1, runnables.length);
		runnables[answer - 1].run();
	}

	private int getAnswer(int bottomRange, int upperRange) {
		while (true) {
			int answer;
			try {
				out.print("> ");
				answer = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException ne) {
				answer = bottomRange - 1;
			}

			if ((bottomRange <= answer) && (answer <= upperRange))
				return answer;

			err.printf("Please provide an answer between %d and %d%n%n",
			        bottomRange, upperRange);
		}
	}

	private void showInsertMenu() {
		printMenu(new String[] {
		        "Insert a new Line",
		        "Insert a new Town",
		        "Insert a new Station",
		        "Insert an existing Station to a Line",
		        "Insert a new Timetable to a Line",
		        "Return to homepage",
		}, "");

		doWithAnswer(
			() -> super.insertLine(),
			() -> super.insertTown(),
			() -> super.insertStation(),
			() -> super.insertStationToLine(),
			() -> super.insertTimetableToLine()
		// ignore 6 - return to homepage, it always happens
		);

		updateViewWithHomepage();
	}

	private void printSource() {
		if (!sourceEntityDescription.isEmpty())
			out.printf("%nFrom %s:%n", sourceEntityDescription);
	}
}
