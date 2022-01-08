package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link entity.Position}.
 *
 * @author Alex Mandelias
 */
class PositionTest {

	private Position defaultPosition;

	/** Sets up a defaultPosition object that will be used to test getter methods */
	@BeforeEach
	void setUp() {
		defaultPosition = new Position(1, 5.4);
	}

	/**
	 * Test method for {@link entity.Position#distanceFrom(entity.Position)}.
	 */
	@Test
	final void testDistanceFrom() {
		assertEquals(0, defaultPosition.distanceFrom(defaultPosition));

		Position p3 = new Position(3, 0);
		Position p4 = new Position(0, 4);
		assertEquals(5, p3.distanceFrom(p4));

		Position p5 = new Position(-5, 0);
		Position p6 = new Position(0, 12);
		assertEquals(13, p5.distanceFrom(p6));
	}

	/**
	 * Test method for {@link entity.Position#getX()}.
	 */
	@Test
	final void testGetX() {
		assertEquals(1, defaultPosition.getX());
	}

	/**
	 * Test method for {@link entity.Position#getY()}.
	 */
	@Test
	final void testGetY() {
		assertEquals(5.4, defaultPosition.getY());
	}
}
