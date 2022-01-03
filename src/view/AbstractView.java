package view;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.IController;

/**
 * An implementation of the {@link IView} interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
abstract class AbstractView extends JFrame implements IView {
	protected static final int WINDOW_HEIGHT = 750;
	protected static final int WINDOW_WIDTH = 500;

	protected IController controller;
	protected final AbstractEntityGraphicFactory factory;
	
	private final JComponent settingsBar; //idk if this should be kept as a reference
	private JPanel mainPanel = new JPanel();
	private JPanel sourcePanel = new JPanel();

	private UndoableHistory<Undoable> navigationHistory;

	public AbstractView(JComponent settingsBar, AbstractEntityGraphicFactory factory) {
		super("Public Transport Prototype");
		
		this.settingsBar = settingsBar;
		this.factory = factory;
		navigationHistory = new UndoableHistory<>();
		add(sourcePanel);
		add(mainPanel);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.add(settingsBar);
	}

	@Override
	public final void start() {		
		updateViewWithHomepage();
		setVisible(true);
	}

	@Override
	public final void registerController(IController newController) {
		controller = newController;
		factory.setLoader(new ImageLoader(newController));
	}
	
	/**
	 * Update the panel above the main panel.
	 * The new panel represents the previous panel which produced
	 * the change in view.
	 * 
	 * For example if the view showed the stations, and a click on a station
	 * changed the main panel to show the lines from that station, this method
	 * should be updated with the clicked station.
	 * 
	 * @param newSourcePanel null to remove the main panel, a JPanel to 
	 * update it with a new graphic
	 */
	protected final void updateSourcePanel(JPanel newSourcePanel) {
		if (newSourcePanel == null)
			sourcePanel = new JPanel();
		else
			sourcePanel = newSourcePanel;
		
			
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
		Undoable u = new ChangeViewCommand(this, mainPanel, newPanel);
		u.execute();
		navigationHistory.add(u);
	}

	private final void prevPanel() {
		navigationHistory.undo();
	}

	private final void nextPanel() {
		navigationHistory.redo();
	}
	
	/**
	 * A small wrapper class restricting access to the View's controller, 
	 * allowing only image loading.
	 *
	 */
	static class ImageLoader {
		private final IController controller;
		
		protected ImageLoader(IController controller) {
			this.controller = controller;
		}
		
		public Image loadImage(String name, int maxWidth, int maxHeight) {
			return controller.loadImage(name, maxWidth, maxHeight);
		}
	}

}
