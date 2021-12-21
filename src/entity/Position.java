package entity;

/**
 * The Position class describes objects that hold
 * an immutable pair of x and y coordinates representing 
 * a geographical location.
 *
 * The base class implements limited functionality and so
 * users are encouraged to subclass it.
 * 
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public class Position {

	protected final double x, y;

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
	 * Calculates the euclidean distance between this position and the given position.
	 *
	 * @param other the 2nd position
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
}
