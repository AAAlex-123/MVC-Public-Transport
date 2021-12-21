package entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
public enum LineType {

	BUS("Bus", 50),
	METRO("Metro", 80);

	public final String        name;
	public final BufferedImage sprite;
	public final double        averageSpeed;

	LineType(String name, double averageSpeed) {
		final String spriteName = name.toLowerCase() + ".png";

		this.name = name;
		this.sprite = loadImage(spriteName);
		this.averageSpeed = averageSpeed;
	}

	private static final BufferedImage loadImage(String imageFileName) {
		BufferedImage img  = null;
		File          file = null;

		try {
			file = new File(imageFileName);
			img = ImageIO.read(file);
		} catch (final IOException e) {
			throw new MissingSpriteException(file);
		}

		return img;
	}
}
