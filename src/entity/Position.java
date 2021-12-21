package entity;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
public final class Position {

	private final double x, y;

	/**
	 * TODO
	 *
	 * @param x
	 * @param y
	 */
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * TODO
	 *
	 * @param other
	 *
	 * @return
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
