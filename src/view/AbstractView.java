package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controller.IController;
import entity.ELine;
import entity.EStation;
import entity.ETown;

/**
 * An abstract implementation of the {@link IView} interface which defines
 * delegate methods to the View's registered {@link IController}, which the
 * concrete implementations will call.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
abstract class AbstractView extends JFrame implements IView {
	private static final int WINDOW_HEIGHT = 750;
	private static final int WINDOW_WIDTH  = 500;

	/** The factory that constructs graphics for the Entities this View displays */
	protected final AbstractEntityGraphicFactory factory;

	private IController                          controller;

	private final UndoableHistory<ChangeViewCommand> navigationHistory;

	private JPanel mainPanel, headerPanel, contentPanel;

	/**
	 * Constructs the view initialising its UI and providing a factory with which to
	 * construct its graphics. The factory can be changed to provide different
	 * graphics while maintaining the same layout.
	 *
	 * @param factory the factory that will be used to construct its graphics
	 */
	public AbstractView(AbstractEntityGraphicFactory factory) {
		super("Public Transport Prototype");
		this.factory = factory;
		factory.initializeView(this);
		navigationHistory = new UndoableHistory<>();
		setLayout(new FlowLayout());

		mainPanel = new JPanel();
		headerPanel = new JPanel();
		contentPanel = new JPanel();

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(mainPanel);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	@Override
	public final void start() {
		changeToHomePanel();
		navigationHistory.clear();
		setVisible(true);
	}

	@Override
	public final void registerController(IController newController) {
		controller = newController;
	}

	// ---------- Methods for the Concrete View classes ---------- //

	/**
	 * Replaces the panel inside this JFrame with a new one by executing the
	 * appropriate {@code Command}. This updates the history of panels.
	 *
	 * @param newContentPanel the new panel
	 */
	protected final void updatePanel(JPanel newContentPanel) {
		JPanel newMainPanel = new JPanel(new BorderLayout());
		newMainPanel.add(headerPanel, BorderLayout.NORTH);
		newMainPanel.add(newContentPanel, BorderLayout.CENTER);

		ChangeViewCommand u = new ChangeViewCommand(this, mainPanel, newMainPanel);
		u.execute();
		this.mainPanel = newMainPanel;
		navigationHistory.add(u);
	}

	/**
	 * A wrapper method for {@link IController#loadImage(String, int, int)}.
	 *
	 * @param name      the image's name
	 * @param maxWidth  the maximum width of the image
	 * @param maxHeight the maximum height of the image
	 *
	 * @return an {@code ImageIcon} for the image with the corresponding name
	 */
	protected final ImageIcon getImageIcon(String name, int maxWidth, int maxHeight) {
		return new ImageIcon(controller.loadImage(name, maxWidth, maxHeight));
	}

	/**
	 * Delegates to {@link #getImageIcon(String, int, int)}.
	 *
	 * @param name the image's name
	 *
	 * @return an {@code ImageIcon} for the image with the corresponding name, with
	 *         a maximum width and height equal to that of this View
	 */
	protected final ImageIcon getImageIcon(String name) {
		return getImageIcon(name, AbstractView.WINDOW_WIDTH, AbstractView.WINDOW_HEIGHT);
	}

	// ---------- AbstractView Commands ---------- //

	protected final void changeToHomePanel() {
		updateHeaderPanel(null);
		updateViewWithHomepage();
	}

	protected final void changeToPreviousPanel() {
		if (navigationHistory.canUndo()) {
			navigationHistory.undo();
			// top of stack is last in the list
			List<ChangeViewCommand> future = navigationHistory.getFuture();
			this.mainPanel = future.get(future.size() - 1).prevPanel;
		}
	}

	protected final void changeToNextPanel() {
		if (navigationHistory.canRedo()) {
			navigationHistory.redo();
			// top of stack is last in the list
			List<ChangeViewCommand> past = navigationHistory.getPast();
			this.mainPanel = past.get(past.size() - 1).prevPanel;
		}
	}

	/**
	 * A wrapper method for {@link IController#getStationsByTown(ETown)} that also
	 * updates the source panel of the view.
	 *
	 * @param town the town from which the stations will be selected
	 *
	 * @see #updateHeaderPanel(JPanel)
	 */
	protected final void getStationsByTown(ETown town) {
		updateHeaderPanel(factory.getETownGraphic(town));
		controller.getStationsByTown(town);
	}

	/**
	 * A wrapper method for {@link IController#getLinesByTown(ETown)}
	 * that also updates the source panel of the view.
	 *
	 * @param town the town from which the lines will be selected
	 * @see #updateHeaderPanel(JPanel)
	 */
	protected final void getLinesByTown(ETown town) {
		updateHeaderPanel(factory.getETownGraphic(town));
		controller.getLinesByTown(town);
	}

	/**
	 * A wrapper method for {@link IController#getStationsByLine(ELine)}
	 * that also updates the source panel of the view.
	 *
	 * @param line the line from which the stations will be selected
	 * @see #updateHeaderPanel(JPanel)
	 */
	protected final void getStationsByLine(ELine line) {
		updateHeaderPanel(factory.getELineGraphic(line));
		controller.getStationsByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getTimetablesByLine(ELine)}
	 * that also updates the source panel of the view.
	 *
	 * @param line the line from which the timetables will be selected
	 * @see #updateHeaderPanel(JPanel)
	 */
	protected final void getTimetablesByLine(ELine line) {
		updateHeaderPanel(factory.getELineGraphic(line));
		controller.getTimetablesByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getTownsByLine(ELine)}
	 * that also updates the source panel of the view.
	 *
	 * @param line the line from which the timetables will be selected
	 * @see #updateHeaderPanel(JPanel)
	 */
	protected final void getTownsByLine(ELine line) {
		updateHeaderPanel(factory.getELineGraphic(line));
		controller.getTownsByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getLinesByStation(EStation)}
	 * that also updates the source panel of the view.
	 *
	 * @param station the station from which the timetables will be selected
	 * @see #updateHeaderPanel(JPanel)
	 */
	protected final void getLinesByStation(EStation station) {
		updateHeaderPanel(factory.getEStationGraphic(station));
		controller.getLinesByStation(station);
	}

	/**
	 * A wrapper method for {@link IController#getAllTowns()}
	 * that also resets the source panel of the view.
	 *
	 * @see #updateHeaderPanel(JPanel)
	 */
	protected final void getAllTowns() {
		updateHeaderPanel(null);
		controller.getAllTowns();
	}

	/**
	 * A wrapper method for {@link IController#getAllLines()}
	 * that also resets the source panel of the view.
	 *
	 * @see #updateHeaderPanel(JPanel)
	 */
	protected final void getAllLines() {
		updateHeaderPanel(null);
		controller.getAllLines();
	}

	/**
	 * <b>TODO: FIX DOCUMENTATION! THIS METHOD ONLY CHANGES THE HEADER_PANEL. IT
	 * DOESN'T UPDATE THE MAIN PANEL WITH THE NEW HEADER PANEL. LOOK AT SOURCE OF
	 * {@link #updatePanel(JPanel)} FOR MORE INFO. </b>
	 * <p>
	 * Update the panel above the main panel. The new panel represents the previous
	 * panel which produced the change in view.
	 * <p>
	 * For example if the view showed the stations, and a click on a station changed
	 * the main panel to show the lines from that station, this method should be
	 * updated with the clicked station.
	 *
	 * @param newSourcePanel null to remove the main panel, a JPanel to update it
	 *                       with a new graphic
	 */
	private void updateHeaderPanel(JPanel newSourcePanel) {
		headerPanel = newSourcePanel;
		if (headerPanel == null)
			headerPanel = new JPanel();
	}
}
