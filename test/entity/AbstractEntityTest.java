package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
class AbstractEntityTest {

	private AbstractEntity defaultEntity;

	/** Sets up a defaultStation object that will be used to test getter methods */
	@BeforeEach
	void setUp() {
		defaultEntity = new ETown(-2, "athens"); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link entity.AbstractEntity#getId()}.
	 */
	@Test
	final void testGetId() {
		assertEquals(-2, defaultEntity.getId());
	}
}
