package view;

import java.util.List;

import javax.swing.JPanel;

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
 */
public class View extends AbstractView {

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

	private static class ETownGraphic extends JPanel {

		public ETownGraphic(ETown town) {

		}

		// TODO: implement
	}

	private static class ELineGraphic extends JPanel {

		public ELineGraphic(ELine line) {

		}

		// TODO: implement
	}

	private static class EStationGraphic extends JPanel {

		public EStationGraphic(EStation station) {

		}

		// TODO: implement
	}

	private static class ETimetableGraphic extends JPanel {

		public ETimetableGraphic(ETimetable timetable) {

		}

		// TODO: implement
	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		// TODO: implement

		/*
		Example dummy implementation

		@foff
		JPanel p = new JPanel(new GridLayout(0, 1, 1, 1));

		for (ETown town : towns) {
			p.add(new ETownGraphic(town));
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
}
