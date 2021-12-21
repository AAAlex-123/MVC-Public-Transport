package entity;

/**
 * The EStation class contains information about any 
 * public transport station. 
 * 
 * Multiple lines of different type can operate the same station.
 *
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public final class EStation {

	private final String   name;
	private final Position position;
	private final ETown    eTown;
	
	/**
	 * Create a new station record.
	 * @param name the station's name
	 * @param position the station's geographical position
	 * @param town the town in which the station is situated
	 */
	public EStation(String name, Position position, ETown town) {
		this.name = name;
		this.position = position;
		this.eTown = town;
	}

	/**
	 * Get the station's name.
	 * 
	 * @return the station's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the geographical {@link Position position} of the station
	 * 
	 * @return the station's position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Get the {@link ETown town} in which the station is situated.
	 * 
	 * @return the station's town 
	 */
	public ETown getTown() {
		return eTown;
	}
	
	
}
