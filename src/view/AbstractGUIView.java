package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controller.IController;
import controller.IImageController;
import entity.ELine;
import entity.EStation;
import entity.ETown;
import localisation.Languages;

/**
 * An implementation of the {@link AbstractView} class which offers a GUI
 * template and expanded delegate methods supporting graphics.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
abstract class AbstractGUIView extends AbstractView<JComponent> {

	private final int width, height;

	private IImageController imageController;

	private final UndoableHistory<ChangeViewCommand> navigationHistory;

	/** The frame that will display the UI */
	protected final JFrame frame;

	private JPanel     mainPanel;
	private JComponent headerPanel;

	/**
	 * Constructs the view initialising its UI and providing a factory with which to
	 * construct its graphics. The factory can be changed to provide different
	 * graphics while maintaining the same layout.
	 *
	 * @param factory         the factory that will be used to construct its
	 *                        graphics
	 * @param imageController the controller that will be used to load its images
	 * @param width           the width of the window
	 * @param height          the height of the window
	 */
	public AbstractGUIView(
	        AbstractGraphicalEntityRepresentationFactory<? extends AbstractGUIView> factory,
	        IImageController imageController, int width, int height) {
		super(factory, imageController);

		this.width = width;
		this.height = height;

		frame = new JFrame(Languages.getString("AbstractGUIView.0")); //$NON-NLS-1$

		navigationHistory = new UndoableHistory<>();
		frame.setLayout(new BorderLayout());

		mainPanel = new JPanel();
		headerPanel = new JPanel();

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(new JPanel(), BorderLayout.CENTER);

		frame.setJMenuBar(constructJMenuBar(new Font(Font.SANS_SERIF, Font.PLAIN, 22)));
		frame.add(mainPanel, BorderLayout.CENTER);
		frame.add(constructFooter(), BorderLayout.SOUTH);

		// frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setSize(new Dimension(this.width, this.height));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	@Override
	public final void start() {
		changeToHomePanel();
		frame.setVisible(true);
		// remove the previous blank panel so the user can't go back from the initial home one
		navigationHistory.clear();
	}

	@Override
	public final void registerImageController(IImageController newImageController) {
		imageController = newImageController;
	}

	/**
	 * Constructs a JMenuBar for this View.
	 *
	 * @param font the font used for the text on the JMenuBar
	 *
	 * @return the JMenuBar
	 */
	protected JMenuBar constructJMenuBar(Font font) {
		final JMenuBar menuBar = new JMenuBar();

		final JMenu     m_insert, m_preferences;
		final JMenuItem home, prev, next, i_town, i_station, i_line, i_station_to_line,
		        i_timetable_to_line, /* p_settings, */ p_language;

		home = new JMenuItem(Languages.getString("OASAView.0")); //$NON-NLS-1$
		prev = new JMenuItem(Languages.getString("OASAView.1")); //$NON-NLS-1$
		next = new JMenuItem(Languages.getString("OASAView.2")); //$NON-NLS-1$

		home.setFont(font);
		prev.setFont(font);
		next.setFont(font);

		m_insert = new JMenu(Languages.getString("OASAView.3")); //$NON-NLS-1$
		i_town = new JMenuItem(Languages.getString("OASAView.4")); //$NON-NLS-1$
		i_station = new JMenuItem(Languages.getString("OASAView.5")); //$NON-NLS-1$
		i_line = new JMenuItem(Languages.getString("OASAView.6")); //$NON-NLS-1$
		i_station_to_line = new JMenuItem(Languages.getString("OASAView.7")); //$NON-NLS-1$
		i_timetable_to_line = new JMenuItem(Languages.getString("OASAView.8")); //$NON-NLS-1$

		m_insert.setFont(font);
		i_town.setFont(font);
		i_station.setFont(font);
		i_line.setFont(font);
		i_station_to_line.setFont(font);
		i_timetable_to_line.setFont(font);

		m_insert.add(i_town);
		m_insert.add(i_station);
		m_insert.add(i_line);
		m_insert.add(i_station_to_line);
		m_insert.add(i_timetable_to_line);

		m_preferences = new JMenu(Languages.getString("OASAView.9")); //$NON-NLS-1$
		// p_settings = new JMenuItem(Languages.getString("OASAView.10")); //$NON-NLS-1$
		p_language = new JMenuItem(Languages.getString("OASAView.10")); //$NON-NLS-1$
		// m_preferences.add(p_settings);
		m_preferences.add(p_language);

		m_preferences.setFont(font);
		// p_settings.setFont(font);
		p_language.setFont(font);

		menuBar.add(home);
		menuBar.add(prev);
		menuBar.add(next);
		menuBar.add(m_insert);
		menuBar.add(m_preferences);


		home.addActionListener(e -> AbstractGUIView.this.changeToHomePanel());
		prev.addActionListener(e -> AbstractGUIView.this.changeToPreviousPanel());
		next.addActionListener(e -> AbstractGUIView.this.changeToNextPanel());

		i_town.addActionListener(e -> AbstractGUIView.this.insertTown());
		i_station.addActionListener(e -> AbstractGUIView.this.insertStation());
		i_line.addActionListener(e -> AbstractGUIView.this.insertLine());
		i_station_to_line.addActionListener(e -> AbstractGUIView.this.insertStationToLine());
		i_timetable_to_line.addActionListener(e -> AbstractGUIView.this.insertTimetableToLine());

		p_language.addActionListener(e -> AbstractGUIView.this.changeLanguage());

		home.setIcon(getImageIcon("home_button.jpg", 25, 25)); //$NON-NLS-1$
		prev.setIcon(getImageIcon("prev_button_small.png", 25, 25)); //$NON-NLS-1$
		next.setIcon(getImageIcon("next_button_small.png", 25, 25)); //$NON-NLS-1$
		i_town.setIcon(getImageIcon("town.png", 18, 18)); //$NON-NLS-1$
		i_station.setIcon(getImageIcon("station.png", 18, 18)); //$NON-NLS-1$
		i_line.setIcon(getImageIcon("bus.png", 18, 18)); //$NON-NLS-1$
		p_language.setIcon(getImageIcon("language.png", 18, 18)); //$NON-NLS-1$

		return menuBar;
	}

	/**
	 * Constructs a JPanel that will be used as a footer for this View, meaning that
	 * it will be placed at the bottom of every view.
	 *
	 * @return the JPanel
	 */
	protected abstract JPanel constructFooter();

	// ---------- Methods for the Concrete GUIView classes ---------- //

	/**
	 * Replaces the panel inside this JFrame with a new one by executing the
	 * appropriate {@code Command}. This updates the history of panels.
	 *
	 * @param newContentPanel the new panel
	 */
	protected final void updatePanel(JPanel newContentPanel) {
		final JPanel newMainPanel = new JPanel(new BorderLayout());
		newMainPanel.add(headerPanel, BorderLayout.NORTH);
		newMainPanel.add(newContentPanel, BorderLayout.CENTER);

		final ChangeViewCommand u = new ChangeViewCommand(this, mainPanel, newMainPanel);
		u.execute();
		mainPanel = newMainPanel;
		navigationHistory.add(u);
	}

	/**
	 * A wrapper method for {@link IImageController#loadImage(String, int, int)}.
	 *
	 * @param name      the image's name
	 * @param maxWidth  the maximum width of the image
	 * @param maxHeight the maximum height of the image
	 *
	 * @return an {@code ImageIcon} for the image with the corresponding name
	 */
	protected final ImageIcon getImageIcon(String name, int maxWidth, int maxHeight) {
		return new ImageIcon(imageController.loadImage(name, maxWidth, maxHeight));
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
		return getImageIcon(name, width, height);
	}

	// ---------- AbstractGUIView Commands ---------- //

	/** Command to change to home panel */
	protected final void changeToHomePanel() {
		updateHeaderPanel(null);
		updateViewWithHomepage();
	}

	/** Command to change to the previous panel in the history */
	protected final void changeToPreviousPanel() {
		if (navigationHistory.canUndo()) {
			navigationHistory.undo();
			// top of stack is last in the list
			final List<ChangeViewCommand> future = navigationHistory.getFuture();
			mainPanel = future.get(future.size() - 1).prevPanel;
		}
	}

	/** Command to change to the next panel in the history */
	protected final void changeToNextPanel() {
		if (navigationHistory.canRedo()) {
			navigationHistory.redo();
			// top of stack is last in the list
			final List<ChangeViewCommand> past = navigationHistory.getPast();
			mainPanel = past.get(past.size() - 1).prevPanel;
		}
	}

	// ---------- AbstractView Command Implementations ---------- //

	/**
	 * Changes the headerPanel to a new one.
	 * <p>
	 * <b>Note:</b> this only changes the reference to a new one, it does <b>not</b>
	 * update the view. The {@link #updatePanel(JPanel)} method is responsible for
	 * combining the new header panel with the rest of the view.
	 *
	 * @param newHeaderPanel null to remove the header panel, a JPanel to update it
	 *                       with a new one
	 */
	private void updateHeaderPanel(JComponent newHeaderPanel) {
		headerPanel = newHeaderPanel;
		if (headerPanel == null)
			headerPanel = new JPanel();
	}

	/**
	 * A wrapper method for {@link IController#getStationsByTown(ETown)} that also
	 * updates the source panel of the view.
	 *
	 * @param town the town from which the stations will be selected
	 *
	 * @see #updateHeaderPanel(JComponent)
	 */
	@Override
	protected void getStationsByTown(ETown town) {
		updateHeaderPanel(factory.getETownRepresentation(town));
		super.getStationsByTown(town);
	}

	/**
	 * A wrapper method for {@link IController#getLinesByTown(ETown)} that also
	 * updates the source panel of the view.
	 *
	 * @param town the town from which the lines will be selected
	 *
	 * @see #updateHeaderPanel(JComponent)
	 */
	@Override
	protected void getLinesByTown(ETown town) {
		updateHeaderPanel(factory.getETownRepresentation(town));
		super.getLinesByTown(town);
	}

	/**
	 * A wrapper method for {@link IController#getStationsByLine(ELine)} that also
	 * updates the source panel of the view.
	 *
	 * @param line the line from which the stations will be selected
	 *
	 * @see #updateHeaderPanel(JComponent)
	 */
	@Override
	protected void getStationsByLine(ELine line) {
		updateHeaderPanel(factory.getELineRepresentation(line));
		super.getStationsByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getTimetablesByLine(ELine)} that also
	 * updates the source panel of the view.
	 *
	 * @param line the line from which the timetables will be selected
	 *
	 * @see #updateHeaderPanel(JComponent)
	 */
	@Override
	protected void getTimetablesByLine(ELine line) {
		updateHeaderPanel(factory.getELineRepresentation(line));
		super.getTimetablesByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getTownsByLine(ELine)} that also
	 * updates the source panel of the view.
	 *
	 * @param line the line from which the timetables will be selected
	 *
	 * @see #updateHeaderPanel(JComponent)
	 */
	@Override
	protected void getTownsByLine(ELine line) {
		updateHeaderPanel(factory.getELineRepresentation(line));
		super.getTownsByLine(line);
	}

	/**
	 * A wrapper method for {@link IController#getLinesByStation(EStation)} that
	 * also updates the source panel of the view.
	 *
	 * @param station the station from which the timetables will be selected
	 *
	 * @see #updateHeaderPanel(JComponent)
	 */
	@Override
	protected void getLinesByStation(EStation station) {
		updateHeaderPanel(factory.getEStationRepresentation(station));
		super.getLinesByStation(station);
	}

	/**
	 * A wrapper method for {@link IController#getAllTowns()} that also resets the
	 * source panel of the view.
	 *
	 * @see #updateHeaderPanel(JComponent)
	 */
	@Override
	protected void getAllTowns() {
		updateHeaderPanel(null);
		super.getAllTowns();
	}

	/**
	 * A wrapper method for {@link IController#getAllLines()} that also resets the
	 * source panel of the view.
	 *
	 * @see #updateHeaderPanel(JComponent)
	 */
	@Override
	protected void getAllLines() {
		updateHeaderPanel(null);
		super.getAllLines();
	}
}
