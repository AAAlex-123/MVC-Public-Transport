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
public class MLocalImageModel implements IImageModel {

	/** The directory inside which all image files are located */
	final String resourceDirectory;

	/**
	 * Constructs a Model that loads images from the
	 * {@code other_resources\resources} directory.
	 */
	public MLocalImageModel() {
		this("other_resources", "resources"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Constructs a Model that loads images from the given directory. The directory
	 * is formed by concatenating the user directory and all the sub-directories
	 * provided as arguments.
	 *
	 * @param subdirectories the sub-directories, starting from the user directory,
	 *                       where the images are located
	 */
	public MLocalImageModel(String... subdirectories) {
		final String curr_dir = System.getProperty("user.dir");       //$NON-NLS-1$
		final String fs       = System.getProperty("file.separator"); //$NON-NLS-1$
		resourceDirectory = curr_dir + fs + String.join(fs, subdirectories) + fs;
	}

	@Override
	public BufferedImage loadImage(String filename) throws IOException {
		final String pathToFile = resourceDirectory + filename;
		return ImageIO.read(new File(pathToFile));
	}
}
