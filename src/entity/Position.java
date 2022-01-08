package entity;

import java.util.Objects;

/**
 * The Position class describes objects that hold an immutable pair of x and y
 * coordinates representing a geographical location. The base class implements
 * limited functionality and users are encouraged to subclass it.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public class Position {

	private final double x, y;

	/**
	 * Construct a new position using x-y coordinates.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the euclidean distance between this position and the given
	 * position.
	 *
	 * @param other the other position
	 *
	 * @return the euclidean distance between the 2 positions
	 */
	public double distanceFrom(Position other) {
		return Math.hypot(getX() - other.getX(), getY() - other.getY());
	}

	/**
	 * Returns the x coordinate of this Position.
	 *
	 * @return the x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y coordinate of this Position.
	 *
	 * @return the y coordinate
	 */
	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return String.format("(%f, %f)", getX(), getY());
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Position))
			return false;
		Position other = (Position) obj;
		return (Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x))
		        && (Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y));
	}
}
