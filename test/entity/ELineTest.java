package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link entity.ELine}.
 *
 * @author Alex Mandelias
 */
class ELineTest {

	private ELine defaultLine;

	/** Sets up a defaultTime object that will be used to test getter methods */
	@BeforeEach
	void setUp() {
		defaultLine = new ELine(-1, "A8", LineType.BUS, "marousi-akadhmia", new ArrayList<>(), //$NON-NLS-1$ //$NON-NLS-2$
		        new ArrayList<>());
	}

	/**
	 * Test method for {@link entity.ELine#getLineNumber()}.
	 */
	@Test
	final void getLineNumber() {
		assertEquals("A8", defaultLine.getLineNumber()); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link entity.ELine#getType()}.
	 */
	@Test
	final void testGetType() {
		assertEquals(LineType.BUS, defaultLine.getType());
	}

	/**
	 * Test method for {@link entity.ELine#getName()}.
	 */
	@Test
	final void testGetName() {
		assertEquals("marousi-akadhmia", defaultLine.getName()); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link entity.ELine#getStations()}.
	 */
	@Test
	final void testGetStations() {
		assertEquals(new ArrayList<>(), defaultLine.getStations());
	}

	/**
	 * Test method for {@link entity.ELine#getTimetables()}.
	 */
	@Test
	final void testGetTimeTables() {
		assertEquals(new ArrayList<>(), defaultLine.getTimetables());
	}
}
