package entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link entity.Coordinates}.
 *
 * @author Alex Mandelias
 */
class CoordinatesTest {

	private Coordinates defaultCoordinates;

	/**
	 * Sets up the defaultCoordinates object that is used in other test methods
	 */
	@BeforeEach
	void setUp() {
		defaultCoordinates = assertDoesNotThrow(() -> new Coordinates(1, 5.4));
	}

	/**
	 * Test method for {@link entity.Coordinates#Coordinates(double, double)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	final void testCoordinates() {
		assertDoesNotThrow(() -> new Coordinates(-90, 5.4));
		assertDoesNotThrow(() -> new Coordinates(90, 5.4));
		assertDoesNotThrow(() -> new Coordinates(1, -180));
		assertDoesNotThrow(() -> new Coordinates(1, 180));

		assertThrows(IllegalArgumentException.class, () -> new Coordinates(-91.1, 50));
		assertThrows(IllegalArgumentException.class, () -> new Coordinates(91.1, 50));
		assertThrows(IllegalArgumentException.class, () -> new Coordinates(21.5, -182.423));
		assertThrows(IllegalArgumentException.class, () -> new Coordinates(21.5, 182.423));
	}

	/**
	 * Test method for {@link entity.Coordinates#greatCircleDistanceFrom(entity.Coordinates)}.
	 */
	@Test
	final void testDistanceFrom() {
		assertEquals(0, defaultCoordinates.greatCircleDistanceFrom(defaultCoordinates));

		Coordinates p3 = new Coordinates(10, 20);
		Coordinates p4 = new Coordinates(30, 50);
		assertEquals(3_821_535.987_707_433_3, p3.greatCircleDistanceFrom(p4));

		Coordinates p5 = new Coordinates(-18.2, 70.123);
		Coordinates p6 = new Coordinates(55.5, -80.444);
		assertEquals(15_184_134.845_704_587, p5.greatCircleDistanceFrom(p6));
	}

	/**
	 * Test method for {@link entity.Coordinates#latitude}.
	 */
	@Test
	final void testLatitude() {
		assertEquals(1, defaultCoordinates.getLatitude());
	}

	/**
	 * Test method for {@link entity.Coordinates#longitude}.
	 */
	@Test
	final void testLongitude() {
		assertEquals(5.4, defaultCoordinates.getLongitude());
	}

	/**
	 * Test method for {@link entity.Coordinates#toString()}.
	 */
	@Test
	@SuppressWarnings("nls")
	final void testToString() {
		assertEquals("( 1.00000N,   5.40000E)", defaultCoordinates.toString());
		assertEquals("(10.50000N,   0.00000E)", new Coordinates(10.5, 0).toString());
		assertEquals("( 0.00000N,   2.50000W)", new Coordinates(0, -2.5).toString());
		assertEquals("(18.12300S,  80.00000E)", new Coordinates(-18.123, 80).toString());
	}
}
