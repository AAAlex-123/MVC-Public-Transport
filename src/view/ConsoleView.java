package view;

import static localisation.ConsoleViewStrings.A_LINE;
import static localisation.ConsoleViewStrings.A_STATION;
import static localisation.ConsoleViewStrings.A_TOWN;
import static localisation.ConsoleViewStrings.EMPTY;
import static localisation.ConsoleViewStrings.LINES;
import static localisation.ConsoleViewStrings.PLEASE_SELECT;
import static localisation.ConsoleViewStrings.RETURN_TO_HOME;
import static localisation.ConsoleViewStrings.SELECT_ACCESS;
import static localisation.ConsoleViewStrings.SHOW_ALL;
import static localisation.ConsoleViewStrings.STATIONS;
import static localisation.ConsoleViewStrings.THE_LINE;
import static localisation.ConsoleViewStrings.THE_STATION;
import static localisation.ConsoleViewStrings.THE_TOWN;
import static localisation.ConsoleViewStrings.TIMETABLES;
import static localisation.ConsoleViewStrings.TOWNS;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import localisation.Languages;
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
public class ConsoleView extends AbstractView<String> {

	private final Scanner     in;
	private final PrintStream out, err;

	private String sourceEntityDescription = EMPTY;

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
		out.println(Languages.getString("ConsoleView.0")); //$NON-NLS-1$
		updateViewWithHomepage();
	}

	@Override
	protected void changeLanguage() {
		Requirements reqs = Languages.getLanguageRequirements();
		if (reqs == null) {
			err.println(Languages.getString("Languages.2")); //$NON-NLS-1$
			err.printf(Languages.getString("Languages.1"), //$NON-NLS-1$
			        Languages.LANGUAGES_DIRECTORY);
			return;
		}

		fulfilRequirements(reqs, Languages.getString("Languages.3")); //$NON-NLS-1$

		final String file  = Languages.FILE;

		try {
			final boolean languageChanged = Languages.updateLanguageWithReqs(reqs);
			if (languageChanged)
				message(file, null);
		} catch (final IOException e) {
			message(file, e);
		}
	}

	@Override
	public void updateViewWithHomepage() {
		sourceEntityDescription = EMPTY;

		printMenu(new String[] {
		        String.format("%s %s", SHOW_ALL, LINES), //$NON-NLS-1$
		        String.format("%s %s", SHOW_ALL, TOWNS), //$NON-NLS-1$
		        Languages.getString("ConsoleView.3"), //$NON-NLS-1$
		        Languages.getString("ConsoleView.1"), //$NON-NLS-1$
		        Languages.getString("ConsoleView.4"), //$NON-NLS-1$
		}, EMPTY);

		doWithAnswer(
		        super::getAllLines,
		        super::getAllTowns,
		        this::showInsertMenu,
		        this::changeLanguage,
		        () -> System.exit(0));
	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		if (towns.isEmpty())
			goToHomepage(TOWNS);

		printSource();

		final FormatBuffer buffer = new FormatBuffer("%s %s: %n", PLEASE_SELECT, //$NON-NLS-1$
		        A_TOWN);

		for (final ETown town : towns)
			buffer.format(factory.getETownGraphic(town));

		out.print(buffer);

		final ETown selected = towns.get(getAnswer(1, towns.size()) - 1);

		sourceEntityDescription = factory.getETownGraphic(selected);

		printMenu(new String[] {
		        String.format(Languages.getString("ConsoleView.6"), SHOW_ALL), //$NON-NLS-1$
		        String.format(Languages.getString("ConsoleView.7"), SHOW_ALL), //$NON-NLS-1$
		        RETURN_TO_HOME,
		}, String.format("%s %s: %s%n", SELECT_ACCESS, //$NON-NLS-1$
		        THE_TOWN), selected.getName());

		doWithAnswer(() -> super.getLinesByTown(selected), () -> super.getStationsByTown(selected),
		        this::updateViewWithHomepage);
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		if (lines.isEmpty())
			goToHomepage(LINES);

		printSource();
		final FormatBuffer buffer = new FormatBuffer("%s %s: %n", PLEASE_SELECT, //$NON-NLS-1$
		        A_LINE);

		for (final ELine line : lines)
			buffer.format(factory.getELineGraphic(line));

		out.print(buffer);

		final ELine selected = lines.get(getAnswer(1, lines.size()) - 1);

		sourceEntityDescription = factory.getELineGraphic(selected);

		printMenu(new String[] {
		        String.format(Languages.getString("ConsoleView.10"), SHOW_ALL), //$NON-NLS-1$
		        String.format(Languages.getString("ConsoleView.11"), SHOW_ALL), //$NON-NLS-1$
		        String.format(Languages.getString("ConsoleView.12"), SHOW_ALL), //$NON-NLS-1$
		        RETURN_TO_HOME,
		}, String.format("%s %s: %s%n", SELECT_ACCESS, //$NON-NLS-1$
		        THE_LINE), selected.getName());

		doWithAnswer(
		        () -> super.getTownsByLine(selected),
		        () -> super.getStationsByLine(selected),
		        () -> super.getTimetablesByLine(selected),
		        this::updateViewWithHomepage);
	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		if (stations.isEmpty())
			goToHomepage(STATIONS);

		printSource();
		final FormatBuffer buffer = new FormatBuffer("%s %s: %n", PLEASE_SELECT, //$NON-NLS-1$
		        A_STATION);

		for (final EStation station : stations)
			buffer.format(factory.getEStationGraphic(station));

		out.print(buffer);

		final EStation selected = stations.get(getAnswer(1, stations.size()) - 1);

		sourceEntityDescription = factory.getEStationGraphic(selected);

		printMenu(new String[] {
		        String.format(Languages.getString("ConsoleView.15"), SHOW_ALL), //$NON-NLS-1$
		        RETURN_TO_HOME,
		}, String.format("%s %s: %s%n", SELECT_ACCESS, //$NON-NLS-1$
		        THE_STATION), selected.getName());

		doWithAnswer(
		        () -> super.getLinesByStation(selected),
		        this::updateViewWithHomepage);
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		if (timetables.isEmpty())
			goToHomepage(TIMETABLES);

		printSource();
		final FormatBuffer buffer = new FormatBuffer();

		for (final ETimetable timetable : timetables)
			buffer.format(factory.getETimetableGraphic(timetable));

		out.print(buffer);

		sourceEntityDescription = EMPTY;

		updateViewWithHomepage();
	}

	@Override
	public void updateViewWithError(Exception e) {
		err.println(e.getLocalizedMessage());
	}

	@Override
	protected void fulfilRequirements(Requirements reqs, String prompt) {
		out.println(System.lineSeparator() + prompt);

		for (final AbstractRequirement req : reqs)
			if (req instanceof ListRequirement) {
				out.println(Languages.getString("ConsoleView.17")); //$NON-NLS-1$

				@SuppressWarnings("unchecked")
				final List<Object> options = ((ListRequirement<Object>) req).getOptions();
				final FormatBuffer buffer  = new FormatBuffer();

				for (final Object option : options)
					buffer.format(option.toString());

				out.print(buffer);

				int answer = getAnswer(1, options.size()) - 1;
				while (!req.finalise(options.get(answer)))
					answer = getAnswer(1, options.size()) - 1;
				// System.out.println("lmao can this even happen?");

			} else if (req instanceof StringRequirement) {
				final StringRequirement stringReq = (StringRequirement) req;
				final String            key       = stringReq.key();

				out.printf(Languages.getString("ConsoleView.18"), key); //$NON-NLS-1$

				while (!req.finalise(in.nextLine()))
					err.printf(Languages.getString("ConsoleView.19"), //$NON-NLS-1$
					        stringReq.stringType.getDescription());
			}
	}

	private static class FormatBuffer {

		private final StringBuffer buffer;
		private int                count;

		public FormatBuffer() {
			this(EMPTY);
		}

		public FormatBuffer(String initial) {
			buffer = new StringBuffer(System.lineSeparator() + initial);
			count = 1;
		}

		public FormatBuffer(String format, Object... args) {
			this(String.format(format, args));
		}

		public void format(String format, Object... args) {
			final String preparedString = String.format("%d: %s%n", count, format); //$NON-NLS-1$
			buffer.append(String.format(preparedString, args));
			count++;
		}

		@Override
		public String toString() {
			return buffer.toString();
		}
	}

	private void goToHomepage(String reason) {
		out.printf(Languages.getString("ConsoleView.21"), reason); //$NON-NLS-1$
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
				out.print("> "); //$NON-NLS-1$
				answer = Integer.parseInt(in.nextLine());
			} catch (final NumberFormatException ne) {
				answer = bottomRange - 1;
			}

			if ((bottomRange <= answer) && (answer <= upperRange))
				return answer;

			err.printf(Languages.getString("ConsoleView.22"), //$NON-NLS-1$
			        bottomRange, upperRange);
		}
	}

	private void showInsertMenu() {
		printMenu(new String[] {
		        Languages.getString("ConsoleView.23"), //$NON-NLS-1$
		        Languages.getString("ConsoleView.24"), //$NON-NLS-1$
		        Languages.getString("ConsoleView.25"), //$NON-NLS-1$
		        Languages.getString("ConsoleView.26"), //$NON-NLS-1$
		        Languages.getString("ConsoleView.27"), //$NON-NLS-1$
		        RETURN_TO_HOME,
		}, EMPTY);

		doWithAnswer(
		        super::insertLine,
		        super::insertTown,
		        super::insertStation,
		        super::insertStationToLine,
		        super::insertTimetableToLine,
		        this::updateViewWithHomepage);

		updateViewWithHomepage();
	}

	private void printSource() {
		if (!sourceEntityDescription.isEmpty())
			out.printf(Languages.getString("ConsoleView.28"), sourceEntityDescription); //$NON-NLS-1$
	}

	private void message(String file, Exception e) {
		final String      messageString, titleString;
		final PrintStream output;

		if (e == null) {
			messageString = Languages.getString("OASAView.20"); //$NON-NLS-1$
			titleString = Languages.getString("OASAView.21"); //$NON-NLS-1$
			output = out;
		} else {
			messageString = Languages.getString("OASAView.22"); //$NON-NLS-1$
			titleString = Languages.getString("OASAView.23"); //$NON-NLS-1$
			output = err;
		}

		output.println(titleString);
		output.println(messageString);
	}
}
