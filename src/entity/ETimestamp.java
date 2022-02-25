package entity;

import java.util.Objects;

/**
 * Represents an immutable Timestamp as an HOURS:MINUTES integer pair, that is
 * bound by the valid times of day.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class ETimestamp extends AbstractEntity implements Comparable<ETimestamp> {

	private final int hours, minutes;

	/**
	 * Constructs a Timestamp from a number of minutes. For example, 70 minutes will
	 * be converted to 1 hour and 10 minutes.
	 *
	 * @param minutes the minutes from which to create the Timestamp
	 *
	 * @return the Timestamp
	 */
	public static ETimestamp fromMinutes(int minutes) {
		return new ETimestamp(minutes / 60, minutes % 60);
	}

	/**
	 * Constructs a Timestamp from a number of hours. For example, 1.5 hours will be
	 * converted to 90 minutes, which correspond to 1 hour and 30 minutes.
	 *
	 * @param hours the hours from which to create the Timestamp, truncated to the
	 *              whole minute.
	 *
	 * @return the Timestamp
	 */
	public static ETimestamp fromHours(double hours) {
		return fromMinutes((int) (hours * 60));
	}

	/**
	 * Checks the validity of and creates a hour:minute Timestamp.
	 *
	 * @param hours   the hours of this Timestamp
	 * @param minutes the minutes of this Timestamp
	 *
	 * @throws IllegalArgumentException if the hours or minutes are out of range
	 *                                  (e.g hours = -1 or minutes == 60)
	 */
	public ETimestamp(int hours, int minutes) throws IllegalArgumentException {
		super(-1);

		if((hours < 0) || (hours >= 24))
			throw new IllegalArgumentException(String.format("Invalid hour value : %d", hours)); //$NON-NLS-1$

		if((minutes < 0) || (minutes >= 60))
			throw new IllegalArgumentException(String.format("Invalid minute value : %d", minutes)); //$NON-NLS-1$

		this.hours = hours;
		this.minutes = minutes;
	}

	/**
	 * Returns this Timestamp's hours.
	 *
	 * @return this Timestamp's hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * Returns this Timestamp's minutes.
	 *
	 * @return this Timestamp's minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	@Override
	public int compareTo(ETimestamp o) {
		if (this.hours == o.hours)
			return this.minutes - o.minutes;

		return this.hours - o.hours;
	}

	/**
	 * Adds another Timestamp to this one, and returns a new Timestamp with the
	 * resulting time. If the resulting time is over 24 hours, it wraps around.
	 *
	 * @param o the Timetable to add
	 *
	 * @return the resulting Timestamp
	 */
	public ETimestamp add(ETimestamp o) {
		final int newMinutes = this.minutes + o.minutes;
		final int newHours   = this.hours + o.hours + (newMinutes / 60);

		return new ETimestamp(newHours % 24, newMinutes % 60);
	}

	/**
	 * Subtracts another Timestamp from this, and returns a new Timestamp with the
	 * resulting time. If the resulting time is under 0 hours, it wraps around.
	 *
	 * @param o the Timetable to subtract
	 *
	 * @return the resulting Timestamp
	 */
	public ETimestamp sub(ETimestamp o) {
		final int newMinutes = this.minutes - o.minutes;
		final int newHours   = (this.hours - o.hours) + (((newMinutes + 60) / 60) - 1);

		return new ETimestamp((newHours + 24) % 24, (newMinutes + 60) % 60);
	}

	/**
	 * Returns this Timestamp's time in the HOURS::MINUTES format.
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
		if (!(obj instanceof ETimestamp))
			return false;
		ETimestamp other = (ETimestamp) obj;
		return (hours == other.hours) && (minutes == other.minutes);
	}
}
