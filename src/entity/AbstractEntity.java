package entity;

import java.util.Objects;

/**
 * The AbstractEntity superclass defines the id field that all Entities share.
 * The id is the unique identifier for this AbstractEntity.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
abstract class AbstractEntity {

	private final int id;

	/**
	 * Constructs an AbstractEntity with the given id, its unique identifier inside
	 * the database.
	 *
	 * @param id the id of this AbstractEntity
	 */
	protected AbstractEntity(int id) {
		this.id = id;
	}

	/**
	 * Returns the id of this AbstractEntity.
	 *
	 * @return the id
	 */
	public final int getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("%d", getId()); //$NON-NLS-1$
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AbstractEntity))
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		return id == other.id;
	}
}
