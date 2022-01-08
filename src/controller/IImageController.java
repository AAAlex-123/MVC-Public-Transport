package controller;

import java.awt.Image;
import java.awt.image.BufferedImage;

import entity.LineType;
import entity.MissingSpriteException;
import model.IImageModel;

/**
 * An interface for the {@code Controller} responsible for loading images from
 * the disk. This controller communicates with the {@link model.IImageModel} to
 * scale and return the requested Images.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public interface IImageController {

	/**
	 * Loads an image from the {@link IImageModel} and resizes it if necessary.
	 *
	 * @param name      the image's name
	 * @param maxWidth  the maximum width of the image
	 * @param maxHeight the maximum height of the image
	 *
	 * @return a possibly resized, loaded Image
	 */
	Image loadImage(String name, int maxWidth, int maxHeight);

	/**
	 * Get the image for the specified public transport vehicle.
	 *
	 * @param type the vehicle type
	 *
	 * @throws MissingSpriteException if the image couldn't be loaded
	 *
	 * @return a {@link BufferedImage} representing the vehicle
	 */
	Image getVehicleSprite(LineType type) throws MissingSpriteException;
}
