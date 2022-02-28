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

	private final String      name;
	private final Coordinates coords;
	private final ETown       town;

	/**
	 * Constructs a Station.
	 *
	 * @param id          the id of this Station
	 * @param name        the name of this Station
	 * @param coordinates the Coordinates of this Station, its geographical location
	 * @param town        the Town of this Station, where it is located
	 */
	public EStation(int id, String name, Coordinates coordinates, ETown town) {
		super(id);
		this.name = name;
		this.coords = coordinates;
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
	 * Returns the geographical {@link Coordinates} of this Station.
	 *
	 * @return this Station's coordinates
	 */
	public Coordinates getCoordinates() {
		return coords;
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
		return String.format(Languages.getString("EStation.0"), super.toString(), getName(), //$NON-NLS-1$
		        getCoordinates(), getTown());
	}

	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = super.hashCode();
		result = (prime * result) + Objects.hash(name, coords, town);
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
		return Objects.equals(name, other.name) && Objects.equals(coords, other.coords)
		        && Objects.equals(town, other.town);
	}


}
