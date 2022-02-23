package entity;

import java.util.Objects;

import localisation.Languages;

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
	 * Constructs a Station.
	 *
	 * @param id       the id of this Station
	 * @param name     the name of this Station
	 * @param position the Position of this Station, its geographical location
	 * @param town     the Town of this Station, where it is located
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
	 * Returns the geographical {@link Position} of this Station.
	 *
	 * @return this Station's position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Returns the {@link ETown} where this Station is located.
	 *
	 * @return this Station's town
	 */
	public ETown getTown() {
		return town;
	}

	@Override
	public String toString() {
		return String.format(Languages.getString("EStation.0"), super.toString(), getName(), getPosition(), //$NON-NLS-1$
		        getTown());
	}

	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = super.hashCode();
		result = (prime * result) + Objects.hash(name, position, town);
		return result;
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
