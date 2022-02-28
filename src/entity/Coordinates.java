package entity;

import java.util.Objects;

/**
 * Represents geographical coordinates, that is latitude-longitude pairs.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public class Coordinates {

	private static final double VOLUMETRIC_MEAN_RADIUS = 6_371_000;

	/** The latitude of these Coordinates */
	public final double latitude;

	/** The longitude of these Coordinates */
	public final double longitude;

	private final String repr;

	/**
	 * Construct a new Coordinates object from latitude and longitude.
	 *
	 * @param latitude  the latitude
	 * @param longitude the longitude
	 */
	public Coordinates(double latitude, double longitude) {
		final double absLat = Math.abs(latitude);
		final double absLon = Math.abs(longitude);

		if (absLat > 90)
			throw new IllegalArgumentException("Latitude has to be between -90 and 90"); //$NON-NLS-1$
		if (absLon > 180)
			throw new IllegalArgumentException("Longitude has to be between -180 and 180"); //$NON-NLS-1$

		this.latitude = latitude;
		this.longitude = longitude;

		final char   NS  = latitude >= 0 ? 'N' : 'S';
		final char   EW  = longitude >= 0 ? 'E' : 'W';
		final String fmt = "(%8.5f%c, %9.5f%c)";      //$NON-NLS-1$

		this.repr = String.format(fmt, absLat, NS, absLon, EW);
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
		        this.latitude, this.longitude, other.latitude, other.longitude);
	}

	@Override
	public String toString() {
		return repr;
	}

	@Override
	public int hashCode() {
		return Objects.hash(latitude, longitude);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Coordinates))
			return false;
		Coordinates other = (Coordinates) obj;
		return (Double.doubleToLongBits(latitude) == Double.doubleToLongBits(other.latitude))
		        && (Double.doubleToLongBits(longitude) == Double.doubleToLongBits(other.longitude));
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
