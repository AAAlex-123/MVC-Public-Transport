package view;

import java.util.List;

import javax.swing.JPanel;

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
	 * TODO
	 *
	 * @param factory
	 */
	public MoovitView(AbstractEntityGraphicFactory factory) {
		super(new JPanel(), factory);
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

	@Override
	public void updateViewWithHomepage() {
		// TODO Auto-generated method stub
		
	}

}
