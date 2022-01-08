package controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import entity.LineType;
import model.IImageModel;

/**
 * An implementation of the {@link IImageController} interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class CLocalImageController implements IImageController {

	private final IImageModel model;

	private final Map<String, Image> imageCache;

	/**
	 * Constructs an ImageLoader Controller that communicates with an ImageModel to
	 * load the requested images.
	 *
	 * @param model the Model associated with this Controller
	 */
	public CLocalImageController(IImageModel model) {
		this.model = model;
		imageCache = new HashMap<>();
	}

	@Override
	public Image loadImage(String name, int maxWidth, int maxHeight) {
		if (name == null)
			throw new IllegalArgumentException("name can't be null");

		if (name.isEmpty())
			throw new IllegalArgumentException("name can't be empty");

		if ((maxWidth <= 0) && (maxHeight <= 0))
			throw new IllegalArgumentException("maxWidth and maxHeight must be positive");

		if (maxWidth <= 0)
			throw new IllegalArgumentException("maxWidth must be positive");

		if (maxHeight <= 0)
			throw new IllegalArgumentException("maxHeight must be positive");

		final Image cachedSprite = imageCache.get(name);
		if (cachedSprite != null)
			return cachedSprite;

		BufferedImage img;
		try {
			img = model.loadImage(name);
		} catch (final IOException ioe) {
			// create empty image
			img = new BufferedImage(54, 54, BufferedImage.TYPE_INT_RGB);
		}

		final int currHeight = img.getHeight();
		final int currWidth  = img.getWidth();

		Image loadedImage = img;

		if ((currHeight > maxHeight) && (currWidth > maxWidth))
			loadedImage = img.getScaledInstance(maxWidth, maxHeight, java.awt.Image.SCALE_SMOOTH);

		else if (currHeight > maxHeight)
			loadedImage = img.getScaledInstance(currWidth, maxHeight, java.awt.Image.SCALE_SMOOTH);

		else if (currWidth > maxWidth)
			loadedImage = img.getScaledInstance(maxWidth, currHeight, java.awt.Image.SCALE_SMOOTH);

		imageCache.put(name, loadedImage);
		return loadedImage;
	}

	@Override
	public Image getVehicleSprite(LineType type) {
		if (type == null)
			throw new IllegalArgumentException("type can't be null"); //$NON-NLS-1$

		return loadImage(type.getSpriteName(), Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
}
