package entity;

//is this actually needed anywhere?
//why do we care that a station belongs to a town?
//what about stations outside of city limits?

/**
 * A class containing information about a town.
 *
 *
 * @author Alex Mandelias
 */
public final class ETown {

	private final String name;

	public ETown(String name) {
		this.name = name;
	}
	
	/**
	 * Get the town's name.
	 * 
	 * @return the town's name
	 */
	public String getName() {
		return name;
	}
}
