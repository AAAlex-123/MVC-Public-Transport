package controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import model.IImageModel;

/**
 * An implementation of the {@link IImageController} interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class CLocalImageController implements IImageController {

	private final IImageModel model;

	/**
	 * Constructs an ImageLoader Controller that communicates with an ImageModel to
	 * load the requested images.
	 *
	 * @param model the Model associated with this Controller
	 */
	public CLocalImageController(IImageModel model) {
		this.model = model;
	}

	@Override
	public Image loadImage(String name, int maxWidth, int maxHeight) {
		BufferedImage img;
		try {
			img = model.loadImage(name);
		} catch (IOException ioe) {
			img = new BufferedImage(54, 54, BufferedImage.TYPE_INT_RGB); //create empty image
		}

		final int currHeight = img.getHeight();
		final int currWidth  = img.getWidth();

		if ((currHeight > maxHeight) && (currWidth > maxWidth))
			return img.getScaledInstance(maxWidth, maxHeight, java.awt.Image.SCALE_SMOOTH);

		else if (currHeight > maxHeight)
			return img.getScaledInstance(currWidth, maxHeight, java.awt.Image.SCALE_SMOOTH);

		else if (currWidth > maxWidth)
			return img.getScaledInstance(maxWidth, currHeight, java.awt.Image.SCALE_SMOOTH);

		return img;
	}
}
