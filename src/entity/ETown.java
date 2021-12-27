package entity;

/**
 * A class containing information about a Town.
 *
 * @author Alex Mandelias
 */
public class ETown extends AbstractEntity {

	private final String name;

	/**
	 * Constructs a Town.
	 *
	 * @param id   the Town's id
	 * @param name the Town's name
	 */
	public ETown(int id, String name) {
		super(id);
		this.name = name;
	}

	/**
	 * Returns the town's name.
	 *
	 * @return the town's name
	 */
	public String getName() {
		return name;
	}
}
