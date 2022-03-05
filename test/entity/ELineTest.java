package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link entity.ELine}.
 *
 * @author Alex Mandelias
 */
@SuppressWarnings("nls")
class ELineTest {

	private ELine defaultLine;
	private List<EStation>   stations;
	private List<ETimestamp> timetables;

	/** Sets up a defaultLine object that will be used to test getter methods */
	@BeforeEach
	void setUp() {
		final ETown a = new ETown(-1, "Town A", new Coordinates(0, 0));
		final ETown b = new ETown(-2, "Town B", new Coordinates(1, 1));

		stations = new LinkedList<>();
		stations.add(new EStation(-1, "Station A", new Coordinates(1, 1), a));
		stations.add(new EStation(-2, "Station B", new Coordinates(1, 4), a));
		stations.add(new EStation(-3, "Station C", new Coordinates(5, 4), b));
		stations.add(new EStation(-4, "Station D", new Coordinates(9, 7), b));
		stations.add(new EStation(-5, "Station E", new Coordinates(4, 19), b));

		timetables = new LinkedList<>();
		timetables.add(new ETimestamp(10, 0));
		timetables.add(new ETimestamp(10, 30));
		timetables.add(new ETimestamp(11, 0));
		timetables.add(new ETimestamp(11, 30));
		timetables.add(new ETimestamp(12, 0));

		defaultLine = new ELine(-1, "A8", LineType.BUS, "marousi-akadhmia", stations, timetables);
	}

	/**
	 * Test method for
	 * {@link entity.ELine#ELine(int, String, LineType, String, List, List)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	final void testGetELine() {
		final ELine emptyLine = new ELine(-1, null, null, null, null, null);
		assertEquals(List.of(), emptyLine.getStations());
		assertEquals(List.of(), emptyLine.getTimetables());
	}

	/**
	 * Test method for {@link entity.ELine#getName()}.
	 */
	@Test
	final void testGetLineNumber() {
		assertEquals("A8", defaultLine.getName());
	}

	/**
	 * Test method for {@link entity.ELine#getType()}.
	 */
	@Test
	final void testGetType() {
		assertEquals(LineType.BUS, defaultLine.getType());
	}

	/**
	 * Test method for {@link entity.ELine#getDescription()}.
	 */
	@Test
	final void testGetName() {
		assertEquals("marousi-akadhmia", defaultLine.getDescription());
	}

	/**
	 * Test method for {@link entity.ELine#getStations()}.
	 */
	@Test
	final void testGetStations() {
		assertEquals(stations, defaultLine.getStations());
	}

	/**
	 * Test method for {@link entity.ELine#getTimetables()}.
	 */
	@Test
	final void testGetTimeTables() {
		assertEquals(timetables, defaultLine.getTimetables());
	}

	/**
	 * Test method for {@link entity.ELine#getDistanceToStation(EStation)}.
	 */
	@Test
	final void testGetDistanceToStation() {
		assertEquals(0.0, defaultLine.getDistanceToStation(stations.get(0)));
		assertEquals(3.0, defaultLine.getDistanceToStation(stations.get(1)));
		assertEquals(7.0, defaultLine.getDistanceToStation(stations.get(2)));
		assertEquals(12.0, defaultLine.getDistanceToStation(stations.get(3)));
		assertEquals(25.0, defaultLine.getDistanceToStation(stations.get(4)));

		assertEquals(-1, defaultLine.getDistanceToStation(new EStation(-1, null, null, null)));

		ELine oneStationLine = new ELine(-2, null, null, null, List.of(stations.get(1)) , null);
		assertEquals(0.0, oneStationLine.getDistanceToStation(stations.get(1)));
	}

	/**
	 * Test method for
	 * {@link entity.ELine#getNextArrival(EStation, ETimestamp)}.
	 */
	@Test
	final void testGetNextArrival() {

		final ETimestamp start = new ETimestamp(10, 45);
		final double speed = defaultLine.getType().getAverageSpeed();

		ETimestamp arrivalAt0 = timetables.get(2);
		assertEquals(arrivalAt0, defaultLine.getNextArrival(stations.get(0), start));

		ETimestamp arrivalAt1 = timetables.get(2)
		        .add(ETimestamp.fromMinutes((int) ((3.0 / speed) * 60)));
		assertEquals(arrivalAt1, defaultLine.getNextArrival(stations.get(1), start));

		ETimestamp arrivalAt2 = timetables.get(1)
		        .add(ETimestamp.fromMinutes((int) ((7.0 / speed) * 60)));
		assertEquals(arrivalAt2, defaultLine.getNextArrival(stations.get(2), start));

		ETimestamp arrivalAt3 = timetables.get(1)
		        .add(ETimestamp.fromMinutes((int) ((12.0 / speed) * 60)));
		assertEquals(arrivalAt3, defaultLine.getNextArrival(stations.get(3), start));

		ETimestamp arrivalAt4 = timetables.get(0)
		        .add(ETimestamp.fromMinutes((int) ((25.0 / speed) * 60)));
		assertEquals(arrivalAt4, defaultLine.getNextArrival(stations.get(4), start));

		assertEquals(null, defaultLine.getNextArrival(stations.get(4), new ETimestamp(13, 30)));
		assertEquals(null, defaultLine.getNextArrival(new EStation(-1, null, null, null), null));
	}
}
