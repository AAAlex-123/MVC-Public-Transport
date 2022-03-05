package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link entity.ETown}.
 *
 * @author Alex Mandelias
 */
class ETownTest {

	private ETown defaultTown;

	/** Sets up a defaultTime object that will be used to test getter methods */
	@BeforeEach
	void setUp() {
		defaultTown = new ETown(-1, "athens", new Coordinates(1, 2)); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link entity.ETown#getName()}.
	 */
	@Test
	final void testGetName() {
		assertEquals("athens", defaultTown.getName()); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link entity.ETown#getCoordinates()}.
	 */
	@Test
	final void testGetCoordinates() {
		assertEquals(new Coordinates(1, 2), defaultTown.getCoordinates());
	}
}
