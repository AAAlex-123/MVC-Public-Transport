package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * An implementation of the {@link IImageModel} interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
@SuppressWarnings("nls")
public class MLocalImageModel implements IImageModel {

	final String resourceDirectory;

	/**
	 * Constructs a Model that loads images from a given directory.
	 *
	 * @param subdirectories the sub-directories, starting from the user directory,
	 *                       where the images are located
	 */
	public MLocalImageModel(String... subdirectories) {
		final String curr_dir = System.getProperty("user.dir");
		final String fs       = System.getProperty("file.separator");
		resourceDirectory = curr_dir + fs + String.join(fs, subdirectories) + fs;
	}

	@Override
	public BufferedImage loadImage(String filename) throws IOException {
		final String pathToFile = resourceDirectory + System.getProperty("file.separator")
		        + filename;
		return ImageIO.read(new File(pathToFile));
	}
}
