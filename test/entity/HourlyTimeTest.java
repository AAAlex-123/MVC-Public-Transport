package entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link entity.ETimetable}.
 *
 * @author Alex Mandelias
 */
class HourlyTimeTest {

	private ETimetable defaultTime;

	/**
	 * Sets up a defaultTime object that will be used to test getter methods.
	 *
	 * @throws java.lang.Exception when the setup throws an Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		defaultTime = new ETimetable(18, 45);
	}

	/**
	 * Test method for {@link entity.ETimetable#ETimetable(int, int)}.
	 */
	@SuppressWarnings({ "unused", "static-method" })
	@Test
	final void testETimetable() {
		assertDoesNotThrow(() -> {
			new ETimetable(0, 0);
			new ETimetable(0, 59);
			new ETimetable(23, 0);
			new ETimetable(23, 59);
			new ETimetable(15, 30);
		});
		assertThrows(IllegalArgumentException.class, () -> new ETimetable(24, 30));
		assertThrows(IllegalArgumentException.class, () -> new ETimetable(-1, 0));
		assertThrows(IllegalArgumentException.class, () -> new ETimetable(10, -5));
		assertThrows(IllegalArgumentException.class, () -> new ETimetable(15, 60));
	}

	/**
	 * Test method for {@link entity.ETimetable#getHours()}.
	 */
	@Test
	final void testGetHour() {
		assertEquals(18, defaultTime.getHours());
	}

	/**
	 * Test method for {@link entity.ETimetable#getMinutes()}.
	 */
	@Test
	final void testGetMinutes() {
		assertEquals(45, defaultTime.getMinutes());
	}

	/**
	 * Test method for {@link entity.ETimetable#toString()}.
	 */
	@Test
	final void testToString() {
		assertEquals("18:45", defaultTime.toString()); //$NON-NLS-1$
	}
}
