package entity;

import java.util.Collections;
import java.util.List;

/**
 * The ELine class represents a public transport line. 
 * It contains details about the line as well as the stations
 * and timetables associated with it.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas 
 */
public class ELine {

	private final String           	ID;
	private final LineType      	type;
	private final String        	name;
	private final List<EStation> 	stations;
	private final List<HourlyTime> 		startTimes;

	public ELine(String lineNo, LineType type, String name, 
			List<EStation> stations, List<HourlyTime> startTimes) {
		this.ID = lineNo;
		this.type = type;
		this.name = name;
		this.stations = Collections.unmodifiableList(stations);
		this.startTimes = Collections.unmodifiableList(startTimes);
	}
	
	/**
	 * Get the line's ID number. Keep in mind this is unique
	 * ONLY to each {@link LineType}.
	 * 
	 * For example Line 3 (Subway) is different than the Trolley-line 3
	 * despite having the same IDs.
	 * 
	 * @return The line's ID number
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Get the  {@link LineType type} of the public
	 * transport vehicle operating on this line.
	 * 
	 * @return the type of the line
	 */
	public LineType getType() {
		return type;
	}

	/**
	 * @return The name of the line
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the {@link EStations stations} on which this line is assigned.
	 * 
	 * @return an immutable list containing the stations
	 */
	public List<EStation> getStations() {
		return stations;
	}

	/**
	 * Get the timetables for this line, that is
	 * the times a transport vehicle departs from its
	 * starting station.
	 * 
	 * @return an immutable list containing the {@link HourlyTime departure times}
	 * for this line. 
	 */
	public List<HourlyTime> getTimeTables() {
		return startTimes;
	}	
}
