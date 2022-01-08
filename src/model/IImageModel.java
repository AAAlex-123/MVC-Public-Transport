package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * An interface for the Model responsible for loading images from the local
 * disk, and specifically from the resources file of the application.
 *
 * @author Dimitris Tsirmpas
 */
public interface IImageModel {

	/**
	 * Loads an image from a file.
	 *
	 * @param filename the name of the Image's file
	 *
	 * @return a BufferedImage containing the image from that file
	 *
	 * @throws IOException if the image couldn't be loaded
	 */
	BufferedImage loadImage(String filename) throws IOException;
}
