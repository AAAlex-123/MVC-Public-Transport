package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link MLocalImageModel} class.
 *
 * @author Alex Mandelias
 */
@SuppressWarnings("nls")
class MLocalImageModelTest {

	private final MLocalImageModel model = new MLocalImageModel("other_resources", "resources");

	/**
	 * Test method for {@link model.MLocalImageModel#loadImage(java.lang.String)}.
	 */
	@Test
	final void testLoadImage() {
		Stream.of(new File(model.resourceDirectory).listFiles()).forEach((f) -> {
		    assertDoesNotThrow(() -> model.loadImage(f.getName()));
		});
	}
}
