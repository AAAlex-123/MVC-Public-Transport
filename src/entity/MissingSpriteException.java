package entity;

import java.io.File;

import localisation.Languages;

/**
 * Thrown when a {@code LineType} couldn't load its sprite from a file.
 *
 * @author Alex Mandelias
 */
public final class MissingSpriteException extends RuntimeException {

	/**
	 * Constructs the Exception with a {@code file}.
	 *
	 * @param file the image file that contains the sprite
	 */
	public MissingSpriteException(File file) {
		super(String.format(Languages.getString("MissingSpriteException.0"), file)); //$NON-NLS-1$
	}
}
