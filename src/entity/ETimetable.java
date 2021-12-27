package entity;


/**
 * Describes a time stamp composed of pair of HOUR::MINUTES integers.
 *
 * @author Dimitris Tsirmpas
 */
public class ETimetable extends AbstractEntity {

	private final int HOURS;
	private final int MINUTES;

	/**
	 * Checks the validity of and creates a hour::minute time stamp.
	 *
	 * @param id      the timestamp's id
	 * @param hours   the timestamp's hour
	 * @param minutes the timestamp's minutes
	 *
	 * @throws IllegalArgumentException if the hours or minutes are out of range
	 *                                  (e.g hours = -1 or minutes == 60)
	 */
	public ETimetable(int id, int hours, int minutes) throws IllegalArgumentException {
		super(id);

		if((hours < 0) || (hours >= 24))
			throw new IllegalArgumentException("Invalid hour parameter value :" + hours);

		if((minutes < 0) || (minutes >= 60))
			throw new IllegalArgumentException("Invalid minute parameter value :" + minutes);

		HOURS = hours;
		MINUTES = minutes;
	}
	/**
	 * Get the time stamp's hour.
	 * @return the time stamp's hour
	 */
	public int getHour() {
		return HOURS;
	}

	/**
	 * Get the time stamp's minutes.
	 * @return the time stamp's minutes
	 */
	public int getMinutes() {
		return MINUTES;
	}

	@Override
	public String toString() {
		return String.format("%d:%d", HOURS, MINUTES);
  }
}
