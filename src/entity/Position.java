package entity;

/**
 * The Position class describes objects that hold an immutable pair of x and y
 * coordinates representing a geographical location. The base class implements
 * limited functionality and users are encouraged to subclass it.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public class Position {

	/**
	 * The X coordinate of this Position expressed in the Cartesian coordinate
	 * system.
	 */
	private final double x;

	/**
	 * The Y coordinate of this Position expressed in the Cartesian coordinate
	 * system.
	 */
	private final double y;

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
	 * @return the distance between the 2 positions
	 */
	public double distanceFrom(Position other) {
		return Math.hypot(getX() - other.getX(), getY() - other.getY());
	}

	/**
	 * Returns the x.
	 *
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y.
	 *
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return String.format("(%f, %f)", getX(), getY()); //$NON-NLS-1$
	}
}
