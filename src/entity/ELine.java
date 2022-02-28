package entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import localisation.Languages;

/**
 * The ELine class represents a public transport Line. It contains details about
 * the line as well as the {@link EStation Stations} and {@link ETimestamp
 * Departure Times} associated with it.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class ELine extends AbstractEntity {

	private final String           name;
	private final LineType         type;
	private final String           description;
	private final List<EStation>   stations;
	private final List<ETimestamp> startTimes;

	/**
	 * Constructs a Line.
	 *
	 * @param id          the id of this Line
	 * @param name        the name of this Line
	 * @param type        the type of this Line
	 * @param description the description of this Line
	 * @param stations    the list of Stations this Line goes through
	 * @param startTimes  the list of Times when this Line departs
	 */
	public ELine(int id, String name, LineType type, String description,
	        List<EStation> stations, List<ETimestamp> startTimes) {
		super(id);
		this.name = name;
		this.type = type;
		this.description = description;
		this.stations = (stations == null) ? Collections.emptyList()
		        : Collections.unmodifiableList(stations);
		this.startTimes = (startTimes == null) ? Collections.emptyList()
		        : Collections.unmodifiableList(startTimes);
	}

	/**
	 * Returns this Line's name. This should be unique to each {@link LineType}. For
	 * example, Subway line 3 is distinguishable from Bus Line 3 despite having the
	 * same name.
	 *
	 * @return this Line's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns this Line's {@link LineType type}, the type of the public transport
	 * vehicle operating on this Line.
	 *
	 * @return this Line's type
	 */
	public LineType getType() {
		return type;
	}

	/**
	 * Returns this Line's description of its route.
	 *
	 * @return this Line's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the {@link EStation stations} that this Line passes through.
	 *
	 * @return an immutable list containing this Line's stations
	 */
	public List<EStation> getStations() {
		return stations;
	}

	/**
	 * Returns the departure times for this Line. These times are represented by
	 * {@link ETimestamp} objects.
	 *
	 * @return an immutable list containing this Line's departure times
	 */
	public List<ETimestamp> getTimetables() {
		return startTimes;
	}

	/**
	 * Returns the distance from the first Station of this Line to another Station.
	 *
	 * @param station the Station to which to calculate the distance
	 *
	 * @return the total distance, or {@code -1} if this line does not pass from the
	 *         given Station
	 */
	public double getDistanceToStation(EStation station) {

		if (!stations.contains(station))
			return -1;

		if ((stations.size() == 1) || station.equals(stations.get(0)))
			return 0;

		double totalDistance = 0;

		final Iterator<EStation> iter = stations.iterator();

		EStation prev = iter.next();

		for (EStation curr = iter.next();; prev = curr, curr = iter.next()) {
			totalDistance += curr.getCoordinates().greatCircleDistanceFrom(prev.getCoordinates());

			if (curr.equals(station))
				break;
		}

		return totalDistance;
	}

	/**
	 * Returns the next arrival time for this Line for a given Station.
	 *
	 * @param station   the Station for which to find the next arrival time
	 * @param startTime the time after which to search for arrivals
	 *
	 * @return the next arrival time or {@code null} if no such time exists
	 */
	public ETimestamp getNextArrival(EStation station, ETimestamp startTime) {
		final double distanceToStation = getDistanceToStation(station);
		if (distanceToStation == -1)
			return null;

		final double     timeToStation = (distanceToStation / type.getAverageSpeed());
		final ETimestamp travelTime    = ETimestamp.fromHours(timeToStation);

		for (final Iterator<ETimestamp> iter = startTimes.iterator(); iter.hasNext();) {
			final ETimestamp arrivalTime = iter.next().add(travelTime);
			if (arrivalTime.compareTo(startTime) > 0)
				return arrivalTime;
		}

		return null;
	}

	@Override
	public String toString() {
		return String.format(Languages.getString("ELine.0"), super.toString(), //$NON-NLS-1$
		        getName(), getType(), getDescription(), getStations(), getTimetables());
	}

	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = super.hashCode();
		result = (prime * result) + Objects.hash(name, description, startTimes, stations, type);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ELine))
			return false;
		ELine other = (ELine) obj;
		return Objects.equals(name, other.name) && Objects.equals(description, other.description)
		        && Objects.equals(startTimes, other.startTimes)
		        && Objects.equals(stations, other.stations) && (type == other.type);
	}
}
