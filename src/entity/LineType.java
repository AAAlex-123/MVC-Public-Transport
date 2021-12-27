package entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import localisation.Languages;

/**
 * An enum describing the different types of public transport vehicles.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public enum LineType {

    //actual real life averages
	/** Constant for Bus */
	BUS(Languages.getString("LineType.0"), 20.5, "bus"), //$NON-NLS-1$ //$NON-NLS-2$

	/** Constant for Subway */
	SUBWAY(Languages.getString("LineType.2"), 28.0, "metro"), //$NON-NLS-1$ //$NON-NLS-2$

	/** Constant for Trolley */
	TROLLEY(Languages.getString("LineType.4"), 20.5, "trolley"), //$NON-NLS-1$ //$NON-NLS-2$

	/** Constant for Tram */
	TRAM(Languages.getString("LineType.6"), 16, "tram"); //$NON-NLS-1$ //$NON-NLS-2$

	private final String        name;
	private final BufferedImage sprite;
	private final double        averageSpeed;

	LineType(String name, double averageSpeed, String SPRITE_NAME) {
		final String spriteName = SPRITE_NAME + ".png"; //$NON-NLS-1$

		this.name = name;
		sprite = LineType.loadImage(spriteName);
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
	 * Returns the name of this vehicle type.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns an image representation of this vehicle type.
	 *
	 * @return the sprite associated with this vehicle
	 */
	public BufferedImage getSprite() {
		return sprite;
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
