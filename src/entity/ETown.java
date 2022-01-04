package entity;

import java.util.Objects;

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

	@Override
	public String toString() {
		return String.format("%s: %s", super.toString(), getName()); //$NON-NLS-1$
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ETown))
			return false;
		ETown other = (ETown) obj;
		return Objects.equals(name, other.name);
	}
}
