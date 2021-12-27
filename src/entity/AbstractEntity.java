package entity;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
abstract class AbstractEntity {

	private final int id;

	/**
	 * TODO
	 *
	 * @param id
	 */
	protected AbstractEntity(int id) {
		this.id = id;
	}

	/**
	 * Returns the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
}
