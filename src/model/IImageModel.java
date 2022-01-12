package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * An interface for the Model responsible for loading images from the disk.
 *
 * @author Dimitris Tsirmpas
 */
public interface IImageModel {

	/**
	 * Loads an Image from a file.
	 *
	 * @param filename the Image's filename
	 *
	 * @return a BufferedImage containing the Image from that file
	 *
	 * @throws IOException if the Image couldn't be loaded
	 */
	BufferedImage loadImage(String filename) throws IOException;
}
