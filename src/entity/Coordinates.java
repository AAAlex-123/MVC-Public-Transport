package entity;

import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.function.Function;

/**
 * Represents geographical coordinates, that is latitude-longitude pairs.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public class Coordinates extends Point2D {

	private static final double VOLUMETRIC_MEAN_RADIUS = 6_371_000;

	private double latitude, longitude;

	/**
	 * Construct a new Coordinates object from latitude and longitude.
	 *
	 * @param latitude  the latitude
	 * @param longitude the longitude
	 */
	public Coordinates(double latitude, double longitude) {
		super();
		setLocation(longitude, latitude);
	}

	/** Constructs a new Coordinates object at [0, 0] */
	public Coordinates() {
		this(0.0, 0.0);
	}

	/**
	 * Calculates the great-circle distance between these and the given Coordinates.
	 *
	 * @param other the other Coordinates
	 *
	 * @return the great-circle distance between the 2 Coordinates
	 */
	public double greatCircleDistanceFrom(Coordinates other) {
		return Coordinates.sphereDistanceInMeters(Coordinates.VOLUMETRIC_MEAN_RADIUS,
		        this.getLatitude(), this.getLongitude(), other.getLatitude(), other.getLongitude());
	}

	/**
	 * Returns this Coordinate's latitude.
	 *
	 * @return this Coordinate's latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets this Coordinate's latitude to latitude.
	 *
	 * @param latitude the new value
	 */
	public void setLatitude(double latitude) {
		if ((latitude < -90) || (latitude > 90))
			throw new IllegalArgumentException("Latitude has to be between -90 and 90"); //$NON-NLS-1$

		this.latitude = latitude;
	}

	/**
	 * Returns this Coordinate's longitude.
	 *
	 * @return this Coordinate's longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets this Coordinate's longitude to longitude.
	 *
	 * @param longitude the new value
	 */
	public void setLongitude(double longitude) {
		if ((longitude < -180) || (longitude > 180))
			throw new IllegalArgumentException("Longitude has to be between -180 and 180"); //$NON-NLS-1$

		this.longitude = longitude;
	}

	@Override
	public double getX() {
		return getLongitude();
	}

	@Override
	public double getY() {
		return getLatitude();
	}

	@Override
	public void setLocation(double x, double y) {
		setLatitude(y);
		setLongitude(x);
	}

	@Override
	public String toString() {
		final double absLat = Math.abs(latitude);
		final double absLon = Math.abs(longitude);

		final char   NS  = latitude >= 0 ? 'N' : 'S';
		final char   EW  = longitude >= 0 ? 'E' : 'W';
		final String fmt = "(%8.5f%c, %9.5f%c)";      //$NON-NLS-1$

		return String.format(fmt, absLat, NS, absLon, EW);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getLatitude(), getLongitude());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Coordinates))
			return false;
		Coordinates other = (Coordinates) obj;
		final Function<java.lang.Double, Long> dTLB  = java.lang.Double::doubleToLongBits;
		return (dTLB.apply(getLatitude()) == dTLB.apply(other.getLatitude()))
		        && (dTLB.apply(getLongitude()) == dTLB.apply(other.getLongitude()));
	}

	// https://en.wikipedia.org/wiki/Haversine_formula
	private static double sphereDistanceInMeters(double r, double lat1, double lon1, double lat2,
	        double lon2) {

		if (r < 0)
			throw new IllegalArgumentException("Radius cannot be negative"); //$NON-NLS-1$

		final double dlat = lat2 - lat1;
		final double dlon = lon2 - lon1;
		final double h    = hav(dlat) + ((1 - hav(-dlat) - hav(lat1 + lat2)) * hav(dlon));

		return r * 2 * Math.asin(Math.sqrt(h));
	}

	private static double hav(double theta) {
		return (1 - Math.cos(Math.toRadians(theta))) / 2;

		// alternative:
		// return Math.pow(Math.sin(Math.toRadians(theta) / 2), 2);
	}
}
