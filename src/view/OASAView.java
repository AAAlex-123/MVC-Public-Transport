package view;

import java.util.List;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

/**
 * One Concrete View that extends the {@link AbstractView}. More Concrete Views
 * can be constructed to illustrate different ways to use the {@link IView}
 * interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class OASAView extends AbstractView {

	/*
	Example use of the static Action classes inside AbstractView
	@foff
	public ETownGraphic(ETown town) {
		JButton b = new JButton("Town: " + town.getName());
		b.addActionListener(new StationsByTownListener(town));
		add(b);
	}
	@fon
	*/

	/**
	 * TODO
	 */
	public OASAView() {
		super(new OASAEntityGraphicFactory());
	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		// TODO: implement

		/*
		Example dummy implementation

		@foff
		JPanel p = new JPanel(new GridLayout(0, 1, 1, 1));

		for (ETown town : towns) {
			p.add(factory.getETownGraphic(town));
		}

		updatePanel(p);
		@fon
		*/
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		// TODO: implement
	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {
		// TODO: implement
	}

	@Override
	public void updateViewWithTimetables(List<ETimetable> timetables) {
		// TODO: implement
	}

	@Override
	public void updateViewWithError(Exception e) {
		// TODO Auto-generated method stub

	}
}
