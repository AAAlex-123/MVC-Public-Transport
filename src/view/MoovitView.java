package view;

import java.util.List;

import javax.swing.JMenuBar;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

/**
 * TODO
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class MoovitView extends AbstractView {

	/**
	 * Constructs a concrete MoovitView and provides an instance of
	 * {@link MoovitEntityGraphicFactory} to the abstract super class.
	 */
	public MoovitView() {
		super(new MoovitEntityGraphicFactory());
	}

	@Override
	protected JMenuBar constructJMenuBar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateViewWithHomepage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateViewWithError(Exception e) {
		// TODO Auto-generated method stub

	}
}
