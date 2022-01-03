package view;

import java.awt.Container;

import javax.swing.JPanel;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
class ChangeViewCommand implements Undoable {

	private final AbstractView view;
	private final JPanel       prevPanel, nextPanel;


	/**
	 * TODO
	 *
	 * @param view
	 * @param prevPanel
	 * @param nextPanel
	 */
	public ChangeViewCommand(AbstractView view, JPanel prevPanel, JPanel nextPanel) {
		this.view = view;
		this.prevPanel = prevPanel;
		this.nextPanel = nextPanel;
	}

	@Override
	public void execute() {
		gotoPanel(nextPanel, prevPanel);
	}

	@Override
	public void unexecute() {
		gotoPanel(prevPanel, nextPanel);
	}

	/**
	 * Replaces the panel inside this JFrame with a new one.
	 * <p>
	 * To be called by {@code ChangeViewCommand#execute}.
	 *
	 * @param newPanel the new panel
	 * @param oldPanel the old panel
	 */
	private void gotoPanel(JPanel newPanel, JPanel oldPanel) {
		final Container contentPane = view.getContentPane();
		
		contentPane.remove(oldPanel);
		contentPane.add(newPanel);

		view.invalidate();
		view.repaint();
	}
}
