package controller;

import java.awt.Image;

import entity.LineType;
import entity.MissingSpriteException;
import model.IImageModel;

/**
 * An interface for the {@code Controller} responsible for loading images from
 * the disk. This controller communicates with the {@link IImageModel} to scale
 * and return the requested Images.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public interface IImageController {

	/**
	 * Loads an Image from the {@link IImageModel} and resizes it if necessary.
	 *
	 * @param name      the Image's name
	 * @param maxWidth  the maximum width of the Image
	 * @param maxHeight the maximum height of the Image
	 *
	 * @return the loaded Image, possibly resized
	 */
	Image loadImage(String name, int maxWidth, int maxHeight);

	/**
	 * Returns the Image for the specified type of public transport vehicle.
	 *
	 * @param type the vehicle type
	 *
	 * @throws MissingSpriteException if the Image couldn't be loaded
	 *
	 * @return the Image that represents that vehicle type
	 */
	Image getVehicleSprite(LineType type) throws MissingSpriteException;
}
