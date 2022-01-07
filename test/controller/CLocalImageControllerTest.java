package controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import entity.LineType;
import model.MLocalImageModel;

/**
 * Unit tests for the {@link CLocalImageController} class.
 *
 * @author Alex Mandelias
 */
@SuppressWarnings("nls")
class CLocalImageControllerTest {

	private final IImageController controller = new CLocalImageController(new MLocalImageModel());

	/**
	 * Test method for
	 * {@link controller.CLocalImageController#loadImage(java.lang.String, int, int)}.
	 */
	@Test
	final void testLoadImage() {
		assertThrows(IllegalArgumentException.class, () -> controller.loadImage(null, 0, 0));
		assertThrows(IllegalArgumentException.class, () -> controller.loadImage("", 0, 0));
		assertThrows(IllegalArgumentException.class, () -> controller.loadImage("dummy", 0, 0));
		assertThrows(IllegalArgumentException.class, () -> controller.loadImage("dummy", 0, 5));
		assertThrows(IllegalArgumentException.class, () -> controller.loadImage("dummy", 5, 0));

		assertDoesNotThrow(() -> controller.loadImage("dummy", 5, 5));
	}

	/**
	 * Test method for
	 * {@link controller.CLocalImageController#getVehicleSprite(entity.LineType)}.
	 */
	@Test
	final void testGetVehicleSprite() {
		assertThrows(IllegalArgumentException.class, () -> controller.getVehicleSprite(null));

		for (final LineType type : LineType.values())
			assertDoesNotThrow(() -> controller.getVehicleSprite(type));
	}
}
