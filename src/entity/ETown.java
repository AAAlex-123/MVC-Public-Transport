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

	/**
	 * Constructs a Town.
	 *
	 * @param id   the id of this Town
	 * @param name the name of this Town
	 */
	public ETown(int id, String name) {
		super(id);
		this.name = name;
	}

	/**
	 * Returns this Town's name.
	 *
	 * @return this Town's name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", super.toString(), getName());
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
