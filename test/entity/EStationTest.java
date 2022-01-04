package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link entity.EStation}.
 *
 * @author Alex Mandelias
 */
class EStationTest {

	private EStation defaultStation;

	/** Sets up a defaultStation object that will be used to test getter methods */
	@BeforeEach
	void setUp() {
		Position position = new Position(5, 10);
		ETown    town     = new ETown(-2, "athens"); //$NON-NLS-1$

		defaultStation = new EStation(-1, "panepisthmio", position, town); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link entity.EStation#getName()}.
	 */
	@Test
	final void testGetName() {
		assertEquals("panepisthmio", defaultStation.getName()); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link entity.EStation#getPosition()}.
	 */
	@Test
	final void testGetPosition() {
		assertEquals(new Position(5, 10), defaultStation.getPosition());
	}

	/**
	 * Test method for {@link entity.EStation#getTown()}.
	 */
	@Test
	final void testGetTown() {
		assertEquals(new ETown(-2, "athens"), defaultStation.getTown()); //$NON-NLS-1$
	}
}
