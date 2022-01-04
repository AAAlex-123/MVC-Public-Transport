package view;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.IController;
import entity.ELine;
import entity.EStation;
import entity.ETown;

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
	
	private JPanel mainPanel = new JPanel();
	private JPanel sourcePanel = new JPanel();

	private UndoableHistory<Undoable> navigationHistory;

	public AbstractView(AbstractEntityGraphicFactory factory) {
		super("Public Transport Prototype");
		
		this.factory = factory;
		navigationHistory = new UndoableHistory<>();
		mainPanel.add(sourcePanel);
		add(mainPanel);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public final void start() {		
		updateViewWithHomepage();
		setVisible(true);
	}

	@Override
	public final void registerController(IController newController) {
		controller = newController;
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
	private void updateSourcePanel(JPanel newSourcePanel) {
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
	 * A wrapper method for {@link IController#getStationsByTown(ETown)}
	 * that also updates the source panel of the view.
	 * 
	 * @param town the town from which the stations will be selected
	 * @see #updateSourcePanel(JPanel)
	 */
	final void getStationsByTown(ETown town) {
		updateSourcePanel(factory.getETownGraphic(town));
		controller.getStationsByTown(town);
	}
	
	/**
	 * A wrapper method for {@link IController#getLinesByTown(ETown)}
	 * that also updates the source panel of the view.
	 * 
	 * @param town the town from which the lines will be selected
	 * @see #updateSourcePanel(JPanel)
	 */
	final void getLinesByTown(ETown town) {
		updateSourcePanel(factory.getETownGraphic(town));
		controller.getLinesByTown(town);
	}
	
	/**
	 * A wrapper method for {@link IController#getStationsByLine(ELine)}
	 * that also updates the source panel of the view.
	 * 
	 * @param line the line from which the stations will be selected
	 * @see #updateSourcePanel(JPanel)
	 */
	final void getStationsByLine(ELine line) {
		updateSourcePanel(factory.getELineGraphic(line));
		controller.getStationsByLine(line);
	}
	
	/**
	 * A wrapper method for {@link IController#getTimetablesByLine(ELine)}
	 * that also updates the source panel of the view.
	 * 
	 * @param line the line from which the timetables will be selected
	 * @see #updateSourcePanel(JPanel)
	 */
	final void getTimetablesByLine(ELine line) {
		updateSourcePanel(factory.getELineGraphic(line));
		controller.getTimetablesByLine(line);
	}
	
	/**
	 * A wrapper method for {@link IController#getTownsByLine(ELine)}
	 * that also updates the source panel of the view.
	 * 
	 * @param line the line from which the timetables will be selected
	 * @see #updateSourcePanel(JPanel)
	 */
	final void getTownsByLine(ELine line) {
		updateSourcePanel(factory.getELineGraphic(line));
		controller.getTownsByLine(line);
	}
	
	/**
	 * A wrapper method for {@link IController#getLinesByStation(EStation)}
	 * that also updates the source panel of the view.
	 * 
	 * @param station the station from which the timetables will be selected
	 * @see #updateSourcePanel(JPanel)
	 */
	final void getLinesByStation(EStation station) {
		updateSourcePanel(factory.getEStationGraphic(station));
		controller.getLinesByStation(station);
	}
	
	/**
	 * A wrapper method for {@link IController#getAllTowns()}
	 * that also resets the source panel of the view.
	 * 
	 * @see #updateSourcePanel(JPanel)
	 */
	final void getAllTowns() {
		updateSourcePanel(null);
		controller.getAllTowns();
	}
	
	/**
	 * A wrapper method for {@link IController#getAllLines()}
	 * that also resets the source panel of the view.
	 * 
	 * @see #updateSourcePanel(JPanel)
	 */
	final void getAllLines() {
		updateSourcePanel(null);
		controller.getAllLines();
	}
	
	/**
	 * A wrapper method for {@link IController#loadImage(String, int, int)}
	 * @param name the image's name
	 * @param maxWidth the maximum width of the image
	 * @param maxHeight the maximum height of the image
	 * @return The image with the corresponding name
	 */
	final Image loadImage(String name, int maxWidth, int maxHeight) {
		return controller.loadImage(name, maxWidth, maxHeight);
	}
	
}
