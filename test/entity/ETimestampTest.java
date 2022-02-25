package entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link entity.ETimestamp}.
 *
 * @author Alex Mandelias
 */
class ETimestampTest {

	private ETimestamp defaultTime;

	/**
	 * Sets up a defaultTime object that will be used to test getter methods.
	 *
	 * @throws java.lang.Exception when the setup throws an Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		defaultTime = new ETimestamp(18, 45);
	}

	/**
	 * Test method for {@link entity.ETimestamp#fromMinutes(int)}.
	 */
	@Test
	final void testFromMinutes() {
		assertDoesNotThrow(() -> {
			ETimestamp.fromMinutes(0);
			ETimestamp.fromMinutes(59);
			ETimestamp.fromMinutes(1380);
			ETimestamp.fromMinutes(1439);
			ETimestamp.fromMinutes(930);
		});

		assertThrows(IllegalArgumentException.class, () -> ETimestamp.fromMinutes(1470));
		assertThrows(IllegalArgumentException.class, () -> ETimestamp.fromMinutes(-5));

		assertEquals(defaultTime, ETimestamp.fromMinutes(1125));
	}

	/**
	 * Test method for {@link entity.ETimestamp#fromHours(double)}.
	 */
	@Test
	final void testFromHours() {
		assertDoesNotThrow(() -> {
			ETimestamp.fromHours(0.0);
			ETimestamp.fromHours(0.9833333);
			ETimestamp.fromHours(23.0);
			ETimestamp.fromHours(23.98333333);
			ETimestamp.fromHours(15.5);
		});

		assertThrows(IllegalArgumentException.class, () -> ETimestamp.fromHours(24.1));
		assertThrows(IllegalArgumentException.class, () -> ETimestamp.fromHours(-0.5));

		assertEquals(defaultTime, ETimestamp.fromHours(18.75));
		assertEquals(defaultTime, ETimestamp.fromHours(18.765));
		assertEquals(defaultTime, ETimestamp.fromHours(18.766));
		assertNotEquals(defaultTime, ETimestamp.fromHours(18.767));
		assertEquals(defaultTime.add(new ETimestamp(0, 1)), ETimestamp.fromHours(18.767));
	}

	/**
	 * Test method for {@link entity.ETimestamp#ETimestamp(int, int)}.
	 */
	@SuppressWarnings({ "unused", "static-method" })
	@Test
	final void testETimestamp() {
		assertDoesNotThrow(() -> {
			new ETimestamp(0, 0);
			new ETimestamp(0, 59);
			new ETimestamp(23, 0);
			new ETimestamp(23, 59);
			new ETimestamp(15, 30);
		});

		assertThrows(IllegalArgumentException.class, () -> new ETimestamp(24, 30));
		assertThrows(IllegalArgumentException.class, () -> new ETimestamp(-1, 0));
		assertThrows(IllegalArgumentException.class, () -> new ETimestamp(10, -5));
		assertThrows(IllegalArgumentException.class, () -> new ETimestamp(15, 60));
	}

	/**
	 * Test method for {@link entity.ETimestamp#getHours()}.
	 */
	@Test
	final void testGetHour() {
		assertEquals(18, defaultTime.getHours());
	}

	/**
	 * Test method for {@link entity.ETimestamp#getMinutes()}.
	 */
	@Test
	final void testGetMinutes() {
		assertEquals(45, defaultTime.getMinutes());
	}

	/**
	 * Test method for {@link entity.ETimestamp#compareTo(ETimestamp)}.
	 */
	@Test
	final void testCompareTo() {
		final ETimestamp prev1, prev2, curr, next1, next2;
		prev1 = new ETimestamp(17, 50);
		prev2 = new ETimestamp(18, 40);
		curr = new ETimestamp(18, 45);
		next1 = new ETimestamp(19, 30);
		next2 = new ETimestamp(18, 59);

		assertTrue(defaultTime.compareTo(prev1) > 0);
		assertTrue(defaultTime.compareTo(prev2) > 0);
		assertTrue(defaultTime.compareTo(curr) == 0);
		assertTrue(defaultTime.compareTo(next1) < 0);
		assertTrue(defaultTime.compareTo(next2) < 0);
	}

	/**
	 * Test method for {@link entity.ETimestamp#add(ETimestamp)}.
	 */
	@Test
	final void testAdd() {
		final ETimestamp add1, add2, add3, add4;
		add1 = new ETimestamp(18, 58);
		add2 = new ETimestamp(20, 45);
		add3 = new ETimestamp(20, 30);
		add4 = new ETimestamp(1, 10);

		assertEquals(defaultTime, defaultTime.add(new ETimestamp(0, 0)));
		assertEquals(add1, defaultTime.add(new ETimestamp(0, 13)));
		assertEquals(add2, defaultTime.add(new ETimestamp(2, 0)));
		assertEquals(add3, defaultTime.add(new ETimestamp(1, 45)));
		assertEquals(add4, defaultTime.add(new ETimestamp(6, 25)));
	}

	/**
	 * Test method for {@link entity.ETimestamp#sub(ETimestamp)}.
	 */
	@Test
	final void testSub() {
		final ETimestamp sub1, sub2, sub3, sub4;
		sub1 = new ETimestamp(18, 20);
		sub2 = new ETimestamp(16, 45);
		sub3 = new ETimestamp(15, 50);
		sub4 = new ETimestamp(19, 55);

		assertEquals(defaultTime, defaultTime.sub(new ETimestamp(0, 0)));
		assertEquals(sub1, defaultTime.sub(new ETimestamp(0, 25)));
		assertEquals(sub2, defaultTime.sub(new ETimestamp(2, 0)));
		assertEquals(sub3, defaultTime.sub(new ETimestamp(2, 55)));
		assertEquals(sub4, defaultTime.sub(new ETimestamp(22, 50)));
	}

	/**
	 * Test method for {@link entity.ETimestamp#getFormattedTime()}.
	 */
	@Test
	final void testgetFormattedTime() {
		assertEquals("18:45", defaultTime.getFormattedTime()); //$NON-NLS-1$
	}
}
