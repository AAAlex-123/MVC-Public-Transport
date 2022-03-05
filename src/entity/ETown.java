package entity;

import java.util.Objects;

/**
 * A class containing information about a Town where {@link EStation stations}
 * are located.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirbas
 */
public class ETown extends AbstractEntity {

	private final String name;
	private final Coordinates coords;

	/**
	 * Constructs a Town.
	 *
	 * @param id          the id of this Town
	 * @param name        the name of this Town
	 * @param coordinates the Coordinates of this Town, its geographical location
	 */
	public ETown(int id, String name, Coordinates coordinates) {
		super(id);
		this.name = name;
		this.coords = coordinates;
	}

	/**
	 * Returns this Town's name.
	 *
	 * @return this Town's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the geographical {@link Coordinates} of this Town.
	 *
	 * @return this Town's coordinates
	 */
	public Coordinates getCoordinates() {
		return coords;
	}

	@Override
	public String toString() {
		return String.format("%s: %s, %s", super.toString(), getName(), getCoordinates()); //$NON-NLS-1$
	}

	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = super.hashCode();
		result = (prime * result) + Objects.hash(name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ETown))
			return false;
		ETown other = (ETown) obj;
		return Objects.equals(name, other.name);
	}
}
