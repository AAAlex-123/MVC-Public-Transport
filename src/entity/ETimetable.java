package entity;

import java.util.Objects;

/**
 * Describes a time stamp composed of pair of HOUR:MINUTES integers.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class ETimetable extends AbstractEntity {

	private final int hours, minutes;

	/**
	 * Checks the validity of and creates a hour:minute time-stamp.
	 *
	 * @param hours   the hours of this time-stamp
	 * @param minutes the minutes of this time-stamp
	 *
	 * @throws IllegalArgumentException if the hours or minutes are out of range
	 *                                  (e.g hours = -1 or minutes == 60)
	 */
	public ETimetable(int hours, int minutes) throws IllegalArgumentException {
		super(-1);

		if((hours < 0) || (hours >= 24))
			throw new IllegalArgumentException(String.format("Invalid hour value : %d", hours)); //$NON-NLS-1$

		if((minutes < 0) || (minutes >= 60))
			throw new IllegalArgumentException(String.format("Invalid minute value : %d", minutes)); //$NON-NLS-1$

		this.hours = hours;
		this.minutes = minutes;
	}

	/**
	 * Returns this time-stamp's hours.
	 *
	 * @return this time-stamp's hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * Returns this time-stamp's minutes.
	 *
	 * @return this time-stamp's minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * Returns this time-stamp's time in the HOURS::MINUTES format.
	 *
	 * @return the formatted time
	 */
	public String getFormattedTime() {
		return String.format("%02d:%02d", getHours(), getMinutes()); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return String.format("%s: %s", super.toString(), getFormattedTime()); //$NON-NLS-1$
	}

	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = super.hashCode();
		result = (prime * result) + Objects.hash(hours, minutes);
		return result;
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
