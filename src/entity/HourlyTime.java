package entity;

/**
 * Describes a time stamp composed of an <Hour, Minute> pair.
 *
 * @author Dimitris Tsirmpas
 */
public class HourlyTime {

	private final int HOURS;
	private final int MINUTES;

	/**
	 * Constructs a new HourlyTime object, a time stamp.
	 *
	 * @param hours   the timestamp's hours
	 * @param minutes the timestamp's minutes
	 *
	 * @throws IllegalArgumentException if the hours are outside of [0, 23] or the
	 *                                  minutes are outside of [0, 59]
	 */
	public HourlyTime(int hours, int minutes) throws IllegalArgumentException {
		if ((hours < 0) || (hours > 23))
			throw new IllegalArgumentException("Invalid hour value :" + hours);

		if ((minutes < 0) || (minutes > 59))
			throw new IllegalArgumentException("Invalid minute value :" + minutes);

		this.HOURS = hours;
		this.MINUTES = minutes;
	}

	/**
	 * Returns this time stamp's hour.
	 *
	 * @return this time stamp's hour
	 */
	public int getHour() {
		return HOURS;
	}

	/**
	 * Returns this time stamp's minutes.
	 *
	 * @return this time stamp's minutes
	 */
	public int getMinutes() {
		return MINUTES;
	}

	@Override
	public String toString() {
		return String.format("%d:%d", HOURS, MINUTES);
	}
}
