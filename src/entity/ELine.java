package entity;

import java.util.Collections;
import java.util.List;

/**
 * The ELine class represents a public transport Line. It contains details about
 * the line as well as the {@link EStation Stations} and {@link ETimetable
 * timetables} associated with it.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class ELine {

	private final String           ID;
	private final LineType         type;
	private final String           name;
	private final List<EStation>   stations;
	private final List<ETimetable> startTimes;

	/**
	 * Constructs a Line.
	 *
	 * @param lineNo     the ID number of the Line
	 * @param type       the type of the Line
	 * @param name       the description of the Line
	 * @param stations   the list of Stations the Line goes through
	 * @param startTimes the list of Times when the Line departs
	 */
	public ELine(String lineNo, LineType type, String name,
	        List<EStation> stations, List<HourlyTime> startTimes) {
		ID = lineNo;
		this.type = type;
		this.name = name;
		this.stations = Collections.unmodifiableList(stations);
		this.startTimes = Collections.unmodifiableList(startTimes);
	}

	/**
	 * Returns the Line's ID number. Keep in mind this is unique ONLY to each
	 * {@link LineType}. For example Line 3 (Subway) is different than the
	 * Trolley-line 3 despite having the same IDs.
	 *
	 * @return the ID number
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Returns the {@link LineType type} of the public transport vehicle operating
	 * on this Line.
	 *
	 * @return the type
	 */
	public LineType getType() {
		return type;
	}

	/**
	 * Returns the name of this Line, a description of its route.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the {@link EStation stations} this Line passes through.
	 *
	 * @return an immutable list containing the stations
	 */
	public List<EStation> getStations() {
		return stations;
	}

	/**
	 * Returns the timetables for this Line, that is the times a transport vehicle
	 * departs from its starting station. These times are represented by
	 * {@link ETimetable} objects.
	 *
	 * @return an immutable list containing the departure times for this Line.
	 */
	public List<ETimetable> getTimeTables() {
		return startTimes;
	}
}
