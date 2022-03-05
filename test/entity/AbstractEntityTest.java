package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link entity.AbstractEntity}.
 *
 * @author Alex Mandelias
 */
class AbstractEntityTest {

	private AbstractEntity defaultEntity;

	/** Sets up a defaultStation object that will be used to test getter methods */
	@BeforeEach
	void setUp() {
		defaultEntity = new ETown(-1, null, null);
	}

	/**
	 * Test method for {@link entity.AbstractEntity#getId()}.
	 */
	@Test
	final void testGetId() {
		assertEquals(-1, defaultEntity.getId());
	}
}
