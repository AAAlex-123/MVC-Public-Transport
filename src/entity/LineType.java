package entity;

import localisation.Languages;

/**
 * An enum describing the different types of public transport vehicles.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public enum LineType {

	/** Constant for Bus */
	BUS(Languages.getString("LineType.0"), 20.5, "bus"),

	/** Constant for Subway */
	SUBWAY(Languages.getString("LineType.2"), 28.0, "metro"),

	/** Constant for Trolley */
	TROLLEY(Languages.getString("LineType.4"), 20.5, "trolley"),

	/** Constant for Tram */
	TRAM(Languages.getString("LineType.6"), 16, "tram");

	private final String name;
	private final String spriteName;
	private final double averageSpeed;

	LineType(String name, double averageSpeed, String spriteName) {
		this.name = name;
		this.spriteName = spriteName + ".png";
		this.averageSpeed = averageSpeed;
	}

	/**
	 * Returns the name of this vehicle type.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the file name for the sprite of this vehicle.
	 *
	 * @return the file name
	 */
	public String getSpriteName() {
		return spriteName;
	}

	/**
	 * Returns the average speed of this vehicle. Used in arrival time calculations.
	 *
	 * @return the average speed of this vehicle
	 */
	double getAverageSpeed() {
		return averageSpeed;
	}
}
