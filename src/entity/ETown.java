package entity;

/**
 * A class containing information about a Town.
 *
 * @author Alex Mandelias
 */
public class ETown {

	private final String name;

	/**
	 * Constructs a Town.
	 *
	 * @param name the name of the Town
	 */
	public ETown(String name) {
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
