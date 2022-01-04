package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.IController;
import entity.ETown;

/**
 * An implementation of the {@link IView} interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
abstract class AbstractView extends JFrame implements IView {

	private IController controller;
	protected final AbstractEntityGraphicFactory factory;

	private JPanel panel;

	private UndoableHistory<Undoable> navigationHistory;

	public AbstractView(AbstractEntityGraphicFactory factory) {
		this.factory = factory;
		navigationHistory = new UndoableHistory<>();
	}

	@Override
	public final void start() {
		setVisible(true);
	}

	@Override
	public final void registerController(IController newController) {
		controller = newController;
	}

	/**
	 * Replaces the panel inside this JFrame with a new one by executing the
	 * appropriate {@code Command}.
	 * <p>
	 * To be called by Concrete View objects.
	 *
	 * @param newPanel the new panel
	 */
	protected final void updatePanel(JPanel newPanel) {
		Undoable u = new ChangeViewCommand(this, panel, newPanel);
		u.execute();
		navigationHistory.add(u);
	}

	private final void prevPanel() {
		navigationHistory.undo();
	}

	private final void nextPanel() {
		navigationHistory.redo();
	}

	// TODO: define the actions that a view may take

	/*
	Example AbstractAction that will be called by the concrete view and the graphics inside of it

	@foff
	*/
	protected final class StationsByTownListener extends AbstractAction {

		// the entity associated with that graphic
		// it will be used as context for the controller
		private ETown town;

		public StationsByTownListener(ETown town) {
			this.town = town;
		}

		// call a method of the controller interface
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.getStationsByTown(this.town);
		}
	}
	/*
	@fon
	*/
}
