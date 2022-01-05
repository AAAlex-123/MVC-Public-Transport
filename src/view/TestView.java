package view;

import java.util.List;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
public class TestView extends AbstractView {

	public TestView() {
		super(null);
	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		System.out.println(towns);
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		System.out.println(lines);
	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		System.out.println(stations);
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		System.out.println(timetables);
	}

	@Override
	public void updateViewWithError(Exception e) {
		System.out.println(e);
	}

	@Override
	public void updateViewWithHomepage() {
		System.out.println("homepage");
	}
}
