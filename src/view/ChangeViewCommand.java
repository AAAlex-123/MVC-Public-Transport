package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * Encapsulates the information necessary to change the main panel of an
 * {@link AbstractGUIView} to a new one. This process can be undone to display
 * the previous panel.
 *
 * @author Alex Mandelias
 */
class ChangeViewCommand implements Undoable {

	private final AbstractGUIView view;

	/** The current panel that will be removed on execution */
	final JPanel prevPanel;

	/** The new panel that will be added on execution */
	final JPanel nextPanel;

	/**
	 * Constructs a Command that, when executed, will remove a panel from an
	 * {@code AbstractView} and add another one in the {@code BorderLayout.CENTER}
	 * position.
	 * <p>
	 * When un-executing this command, the reverse will happen, meaning that the
	 * removed panel will be added and the added will be removed.
	 *
	 * @param view      the {@code AbstractView} instance whose panel will change
	 * @param prevPanel the panel which will be removed
	 * @param nextPanel the panel which will be added
	 */
	public ChangeViewCommand(AbstractGUIView view, JPanel prevPanel, JPanel nextPanel) {
		this.view = view;
		this.prevPanel = prevPanel;
		this.nextPanel = nextPanel;
	}

	@Override
	public void execute() {
		gotoPanel(prevPanel, nextPanel);
	}

	@Override
	public void unexecute() {
		gotoPanel(nextPanel, prevPanel);
	}

	/**
	 * Replaces the panel inside this JFrame with a new one.
	 *
	 * @param oldPanel the old panel
	 * @param newPanel the new panel
	 */
	private void gotoPanel(JPanel oldPanel, JPanel newPanel) {
		view.frame.getContentPane().remove(oldPanel);
		view.frame.getContentPane().add(newPanel, BorderLayout.CENTER);
		// view.revalidate();
		// view.repaint();
	}
}
