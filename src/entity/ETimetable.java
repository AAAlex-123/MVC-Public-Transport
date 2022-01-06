package entity;

/**
 * Describes a time stamp composed of pair of HOUR:MINUTES integers.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
@SuppressWarnings("nls")
public class ETimetable extends AbstractEntity {

	private final int hours;
	private final int minutes;

	/**
	 * Checks the validity of and creates a hour:minute time stamp.
	 *
	 * @param hours   the timestamp's hours
	 * @param minutes the timestamp's minutes
	 *
	 * @throws IllegalArgumentException if the hours or minutes are out of range
	 *                                  (e.g hours = -1 or minutes == 60)
	 */
	public ETimetable(int hours, int minutes) throws IllegalArgumentException {
		super(-1);

		if((hours < 0) || (hours >= 24))
			throw new IllegalArgumentException("Invalid hour parameter value :" + hours);

		if((minutes < 0) || (minutes >= 60))
			throw new IllegalArgumentException("Invalid minute parameter value :" + minutes);

		this.hours = hours;
		this.minutes = minutes;
	}

	/**
	 * Returns the time stamp's hours.
	 *
	 * @return the time stamp's hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * Returns the time stamp's minutes.
	 *
	 * @return the time stamp's minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * Returns the time in the HOURS::MINUTES format.
	 *
	 * @return the formatted time
	 */
	public String getFormattedTime() {
		return String.format("%d:%d", getHours(), getMinutes());
	}

	@Override
	public String toString() {
		return String.format("%s: %s", super.toString(), getFormattedTime());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ETimetable))
			return false;
		ETimetable other = (ETimetable) obj;
		return (hours == other.hours) && (minutes == other.minutes);
	}
}
