package entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * An enum describing the different types of public
 * transport vehicles.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public enum LineType {
	
	//actual real life averages
	BUS("Bus",  20.5, "bus"),
	METRO("Subway", 28.0, "metro"),
	TROLLEY("Trolley", 20.5, "trolley"),
	TRAM("Tram", 16 , "tram");

	private final String        name;
	private final BufferedImage sprite;
	private final double        averageSpeed;

	private LineType(String name, double averageSpeed, String SPRITE_NAME) {
		final String spriteName = SPRITE_NAME + ".png";

		this.name = name;
		this.sprite = loadImage(spriteName);
		this.averageSpeed = averageSpeed;
	}
	
	//should this go to its own Model class since the files are presumably saved locally?
	private static final BufferedImage loadImage(String imageFileName) { 
		BufferedImage img  = null;
		File          file = null;

		try {
			file = new File(imageFileName);
			img = ImageIO.read(file);
		} catch (final IOException e) {
			//should this simply consume the exception and not load anything?
			throw new MissingSpriteException(file);
		}

		return img;
	}

	/**
	 * Get the name of the vehicle type.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get an image representation of this vehicle type.
	 * 
	 * @return the sprite associated with the vehicle
	 */
	public BufferedImage getSprite() {
		return sprite;
	}

	/**
	 * Get the average speed of the vehicle.
	 * Used in arrival time calculations.
	 * 
	 * @return the average speed of the vehicle
	 */
	public double getAverageSpeed() {
		return averageSpeed;
	}
}
