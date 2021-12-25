package view;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.IController;

/**
 * An implementation of the {@link IView} interface.
 *
 * @author Alex Mandelias
 */
public abstract class AbstractView extends JFrame implements IView {

	private IController controller;

	private JPanel panel;

	@Override
	public final void start() {
		setVisible(true);
	}

	@Override
	public final void registerController(IController newController) {
		controller = newController;
	}

	/**
	 * Replaces the panel inside this JFrame with a new one.
	 *
	 * @param newPanel the new panel
	 */
	protected final void updatePanel(JPanel newPanel) {
		final Container contentPane = getContentPane();
		contentPane.remove(panel);
		contentPane.add(newPanel);
		panel = newPanel;
		invalidate();
		repaint();
	}

	// TODO: define the actions that a view may take

	/*
	Example AbstractAction that will be called by the concrete view and the graphics inside of it

	@foff
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
	@fon
	*/
}
