package entity;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
public final class Station {

	private final int      code;
	private final String   name;
	private final Position position;
	private final Town     town;

	public Station(int code, String name, Position position, Town town) {
		this.code = code;
		this.name = name;
		this.position = position;
		this.town = town;
	}
}
