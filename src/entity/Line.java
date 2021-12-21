package entity;

import java.util.List;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
public final class Line {

	private final int           lineNo;
	private final boolean       direction;
	private final LineType      type;
	private final String        name;
	private final List<Integer> startTimes;

	private Line(int lineNo, boolean direction, LineType type, String name,
	        List<Integer> startTimes) {
		this.lineNo = lineNo;
		this.direction = direction;
		this.type = type;
		this.name = name;
		this.startTimes = startTimes;
	}
}
