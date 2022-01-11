package controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import entity.LineType;
import model.IImageModel;

/**
 * An implementation of the {@link IImageController} interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class CLocalImageController implements IImageController {

	private static class ImageInfo {
		public final String name;
		public final int    width, height;

		/**
		 * Constructs an ImageInfo record using the necessary data.
		 *
		 * @param name   the Image's filename
		 * @param width  the Image's width
		 * @param height the Image's height
		 */
		public ImageInfo(String name, int width, int height) {
			this.name = name;
			this.width = width;
			this.height = height;
		}

		@Override
		public int hashCode() {
			return Objects.hash(height, name, width);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof ImageInfo))
				return false;
			final ImageInfo other = (ImageInfo) obj;
			return (height == other.height) && Objects.equals(name, other.name)
			        && (width == other.width);
		}
	}

	private final IImageModel model;

	private final Map<ImageInfo, Image> imageCache;

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
			throw new IllegalArgumentException("name can't be null"); //$NON-NLS-1$

		if (name.isEmpty())
			throw new IllegalArgumentException("name can't be empty"); //$NON-NLS-1$

		if ((maxWidth <= 0) && (maxHeight <= 0))
			throw new IllegalArgumentException("maxWidth and maxHeight must be positive"); //$NON-NLS-1$

		if (maxWidth <= 0)
			throw new IllegalArgumentException("maxWidth must be positive"); //$NON-NLS-1$

		if (maxHeight <= 0)
			throw new IllegalArgumentException("maxHeight must be positive"); //$NON-NLS-1$

		final ImageInfo iinfo = new ImageInfo(name, maxWidth, maxHeight);

		final Image cachedSprite = imageCache.get(iinfo);
		if (cachedSprite != null)
			return cachedSprite;

		BufferedImage img;
		try {
			img = model.loadImage(name);
		} catch (final IOException ioe) {
			// create empty image
			img = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
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

		imageCache.put(iinfo, loadedImage);
		return loadedImage;
	}

	@Override
	public Image getVehicleSprite(LineType type) {
		if (type == null)
			throw new IllegalArgumentException("type can't be null"); //$NON-NLS-1$

		return loadImage(type.getSpriteName(), Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
}
