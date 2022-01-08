package entity;

import java.util.Objects;

/**
 * The EStation class contains information about any public transport Station.
 * Multiple {@link ELine lines} can use the same Station.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public class EStation extends AbstractEntity {

	private final String   name;
	private final Position position;
	private final ETown    town;

	/**
	 * Creates a Station.
	 *
	 * @param id       the Station's id
	 * @param name     the Station's name
	 * @param position the Station's geographical position
	 * @param town     the town in which the Station is located
	 */
	public EStation(int id, String name, Position position, ETown town) {
		super(id);
		this.name = name;
		this.position = position;
		this.town = town;
	}

	/**
	 * Returns this Station's name.
	 *
	 * @return this Station's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the geographical {@link Position position} of this Station.
	 *
	 * @return this Station's position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Returns the {@link ETown town} where this Station is located.
	 *
	 * @return this Station's town
	 */
	public ETown getTown() {
		return town;
	}

	@Override
	public String toString() {
		return String.format("%s: %s at %s in town %s", super.toString(), getName(), getPosition(),
		        getTown());
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EStation))
			return false;
		EStation other = (EStation) obj;
		return Objects.equals(name, other.name) && Objects.equals(position, other.position)
		        && Objects.equals(town, other.town);
	}
}
