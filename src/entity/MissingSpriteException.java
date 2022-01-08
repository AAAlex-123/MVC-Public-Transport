package entity;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import localisation.Languages;

/**
 * Thrown when a {@code LineType} couldn't load its sprite from a file.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public final class MissingSpriteException extends UncheckedIOException {

	/**
	 * Constructs the Exception with a {@code file}.
	 *
	 * @param file the image file that contains the sprite
	 */
	public MissingSpriteException(File file) {
		super(new IOException(
		        String.format(Languages.getString("MissingSpriteException.0"), file))); //$NON-NLS-1$
	}
}
