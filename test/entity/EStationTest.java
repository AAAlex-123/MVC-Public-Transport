package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link entity.EStation}.
 *
 * @author Alex Mandelias
 */
@SuppressWarnings("nls")
class EStationTest {

	private EStation defaultStation;

	/** Sets up a defaultStation object that will be used to test getter methods */
	@BeforeEach
	void setUp() {
		final Coordinates coords = new Coordinates(5, 10);
		final ETown       town   = new ETown(-2, "athens");

		defaultStation = new EStation(-1, "panepisthmio", coords, town);
	}

	/**
	 * Test method for {@link entity.EStation#getName()}.
	 */
	@Test
	final void testGetName() {
		assertEquals("panepisthmio", defaultStation.getName());
	}

	/**
	 * Test method for {@link entity.EStation#getCoordinates()}.
	 */
	@Test
	final void testGetPosition() {
		assertEquals(new Coordinates(5, 10), defaultStation.getCoordinates());
	}

	/**
	 * Test method for {@link entity.EStation#getTown()}.
	 */
	@Test
	final void testGetTown() {
		assertEquals(new ETown(-2, "athens"), defaultStation.getTown());
	}
}
