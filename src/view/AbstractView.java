package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controller.IController;
import controller.IImageLoader;
import entity.ELine;
import entity.EStation;
import entity.ETown;

/**
 * An implementation of the {@link AbstractView} class which offers
 * a GUI template and expanded delegate methods supporting graphics.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
abstract class AbstractGUIView extends AbstractView<JPanel>  {
	
	private final IImageLoader loader;

	private final UndoableHistory<ChangeViewCommand> navigationHistory;

	private JPanel mainPanel, headerPanel, contentPanel;
	
	protected final JFrame frame;
	
	
	/**
	 * Constructs the view initialising its UI and providing a factory with which to
	 * construct its graphics. The factory can be changed to provide different
	 * graphics while maintaining the same layout.
	 *
	 * @param factory the factory that will be used to construct its graphics
	 */
	public AbstractGUIView(AbstractEntityGraphicFactory<JPanel> factory, Dimension windowSize, IController controller, IImageLoader loader) {
		super(factory, controller);
		frame = new JFrame("Public Transport Prototype");
		
		this.loader = loader;
		factory.initializeView(this);
		
		navigationHistory = new UndoableHistory<>();
		frame.setLayout(new FlowLayout());

		mainPanel = new JPanel();
		headerPanel = new JPanel();
		contentPanel = new JPanel();

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		frame.getContentPane().add(mainPanel);
		frame.setJMenuBar(constructJMenuBar());

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setSize(new Dimension(windowSize));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(constructFooter());
	}
	
	/**
	 * Constructs the view <b> without assigning a {@link IController} </b> and 
	 * initialising its UI and providing a factory with which to
	 * construct its graphics. The factory can be changed to provide different
	 * graphics while maintaining the same layout.
	 *
	 * @param factory the factory that will be used to construct its graphics
	 */
	public AbstractGUIView(AbstractEntityGraphicFactory<JPanel> factory, Dimension windowSize,  IImageLoader loader) {
		this(factory, windowSize, null, loader);
	}

	@Override
	public final void start() {
		changeToHomePanel();
		navigationHistory.clear();
		frame.setVisible(true);
	}
	
	public Container getContentPane() {
		return frame.getContentPane();
	}

	protected abstract JMenuBar constructJMenuBar();
	
	protected abstract JPanel constructFooter();

	// ---------- Methods for the Concrete View classes ---------- //
	
	/**
	 * A wrapper method for {@link IController#getStationsByTown(ETown)} that also
	 * updates the source panel of the view.
	 *
	 * @param town the town from which the stations will be selected
	 *
	 * @see #updateHeaderPanel(JPanel)
	 */
	@Override
	protected void getStationsByTown(ETown town) {
		super.getStationsByTown(town);
		updateHeaderPanel(factory.getETownGraphic(town));
	}

	/**
	 * A wrapper method for {@link IController#getLinesByTown(ETown)}
	 * that also updates the source panel of the view.
	 *
	 * @param town the town from which the lines will be selected
	 * @see #updateHeaderPanel(JPanel)
	 */
	@Override
	protected void getLinesByTown(ETown town) {
		super.getLinesByTown(town);
		updateHeaderPanel(factory.getETownGraphic(town));
	}

	/**
	 * A wrapper method for {@link IController#getStationsByLine(ELine)}
	 * that also updates the source panel of the view.
	 *
	 * @param line the line from which the stations will be selected
	 * @see #updateHeaderPanel(JPanel)
	 */
	@Override
	protected void getStationsByLine(ELine line) {
		super.getStationsByLine(line);
		updateHeaderPanel(factory.getELineGraphic(line));
	}

	/**
	 * A wrapper method for {@link IController#getTimetablesByLine(ELine)}
	 * that also updates the source panel of the view.
	 *
	 * @param line the line from which the timetables will be selected
	 * @see #updateHeaderPanel(JPanel)
	 */
	@Override
	protected void getTimetablesByLine(ELine line) {
		super.getTimetablesByLine(line);
		updateHeaderPanel(factory.getELineGraphic(line));
	}

	/**
	 * A wrapper method for {@link IController#getTownsByLine(ELine)}
	 * that also updates the source panel of the view.
	 *
	 * @param line the line from which the timetables will be selected
	 * @see #updateHeaderPanel(JPanel)
	 */
	@Override
	protected void getTownsByLine(ELine line) {
		super.getTownsByLine(line);
		updateHeaderPanel(factory.getELineGraphic(line));
	}

	/**
	 * A wrapper method for {@link IController#getLinesByStation(EStation)}
	 * that also updates the source panel of the view.
	 *
	 * @param station the station from which the timetables will be selected
	 * @see #updateHeaderPanel(JPanel)
	 */
	@Override
	protected void getLinesByStation(EStation station) {
		super.getLinesByStation(station);
		updateHeaderPanel(factory.getEStationGraphic(station));
	}

	/**
	 * A wrapper method for {@link IController#getAllTowns()}
	 * that also resets the source panel of the view.
	 *
	 * @see #updateHeaderPanel(JPanel)
	 */
	@Override
	protected void getAllTowns() {
		super.getAllTowns();
		updateHeaderPanel(null);
	}

	/**
	 * A wrapper method for {@link IController#getAllLines()}
	 * that also resets the source panel of the view.
	 *
	 * @see #updateHeaderPanel(JPanel)
	 */
	@Override
	protected void getAllLines() {
		super.getAllLines();
		updateHeaderPanel(null);
	}

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
		return new ImageIcon(loader.loadImage(name, maxWidth, maxHeight));
	}
	
	/**
	 * Delegates to {@link #getImageIcon(String, int, int)}.
	 *
	 * @param name the image's name
	 * @param maximumSize a Dimension describing the maximum height and
	 * width of the image 
	 *
	 * @return an {@code ImageIcon} for the image with the corresponding name, with
	 *         a maximum width and height equal to the provided dimension object
	 */
	protected final ImageIcon getImageIcon(String name, Dimension maximumSize) {
		return getImageIcon(name, maximumSize.width, maximumSize.height);
	}

	// ---------- AbstractGUIView Commands ---------- //

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
	void updateHeaderPanel(JPanel newSourcePanel) {
		headerPanel = newSourcePanel;
		if (headerPanel == null)
			headerPanel = new JPanel();
	}
}
