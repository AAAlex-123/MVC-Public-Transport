package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.IImageController;
import entity.Coordinates;
import entity.ELine;
import entity.EStation;
import entity.ETimestamp;
import entity.ETown;
import localisation.Languages;
import requirement.util.Requirements;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
public class MapView extends AbstractGUIView {

	private static final Font globalFont = new Font(Font.SANS_SERIF, Font.PLAIN, 22);

	private JPanel cachedHomepage;

	/**
	 * TODO
	 *
	 * @param imageController
	 */
	public MapView(IImageController imageController) {
		super(new MapEntityRepresentationFactory(), imageController, 850, 650);

		((MapEntityRepresentationFactory) factory).initializeView(this);

		// final String imgname  = "37.97066403382808_23.903633118641128_37.95683671311368_23.9314925592738.png"; //$NON-NLS-1$
		final String imgname  = "38.10823767406228_23.62081227624487_37.796943185419764_24.016115410154413.png"; //$NON-NLS-1$
		final String fullPath = System.getProperty("user.dir") + "\\resources\\maps\\" + imgname;                //$NON-NLS-1$ //$NON-NLS-2$

		try {
			ImageInfo.init(fullPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateViewWithHomepage() {
		// TODO Auto-generated method stub
		if (cachedHomepage == null) {

			final JPanel contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

			final JPanel imagePanel = new JPanel();
			imagePanel.add(new JLabel(super.getImageIcon("oasa_home.jpg"))); //$NON-NLS-1$

			final JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));
			buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 30, 30));

			final JButton button1 = new JButton(Languages.getString("OASAView.21")); //$NON-NLS-1$
			final JButton button2 = new JButton(Languages.getString("OASAView.22")); //$NON-NLS-1$

			button1.setFont(globalFont);
			button2.setFont(globalFont);

			button1.addActionListener(e -> MapView.super.getAllLines());
			button2.addActionListener(e -> MapView.super.getAllTowns());

			buttonPanel.add(button1);
			buttonPanel.add(button2);

			contentPanel.add(imagePanel);
			contentPanel.add(Box.createVerticalStrut(15));
			contentPanel.add(buttonPanel);

			cachedHomepage = contentPanel;
		}

		super.updatePanel(cachedHomepage);
	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {

		List<Coordinates> coordinates = extractCoords(towns, (t) -> t.getCoordinates());

		final ImageInfo iinfo = ImageInfo.getCroppedInstance(coordinates);

		final JPanel p = forImage(iinfo.img);

		// plot every town with factory
		for (final ETown town : towns) {
			final Coordinates coords = town.getCoordinates();
			final Point       mapped = new Point();
			iinfo.mapper.map(coords, mapped);

			final JComponent townGraphic = factory.getETownRepresentation(town);
			final Dimension  dim      = townGraphic.getSize();
			townGraphic.setLocation(mapped.x - (dim.width / 2), mapped.y - (dim.height / 2));
			p.add(townGraphic);
		}

		super.updatePanel(p);
	}

	@Override
	public void updateViewWithLines(List<ELine> lines) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateViewWithLineArrivalTimes(List<ELine> lines, EStation station) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateViewWithStations(List<EStation> stations) {

		List<Coordinates> coordinates = extractCoords(stations, (s) -> s.getCoordinates());

		final ImageInfo iinfo = ImageInfo.getCroppedInstance(coordinates);

		final JPanel p = forImage(iinfo.img);

		// plot every station with factory
		for (final EStation station : stations) {
			final Coordinates coords = station.getCoordinates();
			final Point       mapped = new Point();
			iinfo.mapper.map(coords, mapped);

			final JComponent stationGraphic = factory.getEStationRepresentation(station);
			final Dimension  dim      = stationGraphic.getSize();
			stationGraphic.setLocation(mapped.x - (dim.width / 2), mapped.y - (dim.height / 2));
			p.add(stationGraphic);
		}

		super.updatePanel(p);
	}

	@Override
	public void updateViewWithTimestamps(List<ETimestamp> timestamps) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateViewWithError(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected JPanel constructFooter() {
		return new JPanel();
	}

	@Override
	protected void changeLanguage() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fulfilRequirements(Requirements reqs, String prompt) {
		// TODO Auto-generated method stub

	}

	private static <T> List<Coordinates> extractCoords(List<T> ls,
	        Function<T, ? extends Coordinates> mapper) {
		return ls.stream().map(mapper).collect(Collectors.toList());
	}

	private static JPanel forImage(BufferedImage img) {
		return new JPanel(null) {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, null);
			}
		};
	}

	private static class ImageInfo {
		private static BufferedImage fullImage;
		private static Coordinates   topLeft, bottomRight;

		private final BufferedImage img;
		private final Mapper<Coordinates, Point> mapper;

		private ImageInfo(BufferedImage img, Mapper<Coordinates, Point> mapper) {
			this.img = img;
			this.mapper = mapper;
		}

		public static void init(String filename) throws IOException {

			fullImage = ImageIO.read(new File(filename));

			final int      from   = filename.lastIndexOf(File.separatorChar);
			final int      to     = filename.lastIndexOf('.');
			final String[] coords = filename.substring(from + 1, to).split("_"); //$NON-NLS-1$

			final Function<Object, Double> f = s -> Double.parseDouble((String) s);

			topLeft = new Coordinates(f.apply(coords[0]), f.apply(coords[1]));
			bottomRight = new Coordinates(f.apply(coords[2]), f.apply(coords[3]));
		}

		public static ImageInfo getCroppedInstance(List<Coordinates> coordinates) {

			// find the Coordinates of top-left and bottom-right bounds
			final Coordinates tl_bound, br_bound;

			final double max    = Double.MAX_VALUE;
			double       maxLat = -max, minLon = max, minLat = max, maxLon = -max;

			for (final Coordinates coords : coordinates) {
				minLon = Math.min(minLon, coords.getLongitude());
				maxLon = Math.max(maxLon, coords.getLongitude());
				minLat = Math.min(minLat, coords.getLatitude());
				maxLat = Math.max(maxLat, coords.getLatitude());
			}

			tl_bound = new Coordinates(maxLat, minLon);
			br_bound = new Coordinates(minLat, maxLon);

			// map the Coordinate bounds to Point bounds
			final int imgWidth  = fullImage.getWidth();
			final int imgHeight = fullImage.getHeight();

			final Coordinates tls = topLeft;
			final Coordinates brs = bottomRight;
			final Point       tld = new Point(0, 0);
			final Point       brd = new Point(imgWidth, imgHeight);

			final Mapper<Coordinates, Point> old_mapper = new Mapper<>(tls, brs, tld, brd);

			final Point mapped_tl_bound, mapped_br_bound, mapped_tls;
			old_mapper.map(tl_bound, mapped_tl_bound = new Point());
			old_mapper.map(br_bound, mapped_br_bound = new Point());
			old_mapper.map(tls, mapped_tls = new Point());

			// crop the image according to Point bounds
			final int x = (int) Math.max(mapped_tls.getX(), mapped_tl_bound.getX() - 30);
			final int y = (int) Math.max(mapped_tls.getY(), mapped_tl_bound.getY() - 30);
			final int w = (int) Math.min((mapped_br_bound.getX() - x) + 30, imgWidth - x);
			final int h = (int) Math.min((mapped_br_bound.getY() - y) + 30, imgHeight - y);

			final BufferedImage new_img = fullImage.getSubimage(x, y, w, h);

			// create the mapper for the cropped image
			final Coordinates cropped_tls, cropped_brs;
			old_mapper.invmap(new Point(x, y), cropped_tls = new Coordinates());
			old_mapper.invmap(new Point(x + w, y + h), cropped_brs = new Coordinates());

			final Point cropped_tld, cropped_brd;
			cropped_tld = new Point(0, 0);
			cropped_brd = new Point(new_img.getWidth(), new_img.getHeight());

			final Mapper<Coordinates, Point> new_mapper = new Mapper<>(cropped_tls, cropped_brs,
			        cropped_tld, cropped_brd);

			// returned the cropped image and its mapper
			return new ImageInfo(new_img, new_mapper);
		}
	}

	private static class Mapper<T extends Point2D, S extends Point2D> {
		private final T tls, brs;
		private final S tld, brd;

		private final double dxs, dys, dxd, dyd;

		public Mapper(T topLeftSrc, T bottomRightSrc, S topLeftDst, S bottomRightDst) {
			tls = topLeftSrc;
			brs = bottomRightSrc;
			tld = topLeftDst;
			brd = bottomRightDst;

			dxs = brs.getX() - tls.getX();
			dys = tls.getY() - brs.getY();
			dxd = brd.getX() - tld.getX();
			dyd = tld.getY() - brd.getY();
		}

		public void map(T from, S to) {
			double dx = (from.getX() - tls.getX()) / dxs;
			double dy = (from.getY() - brs.getY()) / dys;

			to.setLocation(tld.getX() + (dx * dxd), brd.getY() + (dy * dyd));
		}

		public void invmap(S from, T to) {
			double dx = (from.getX() - tld.getX()) / dxd;
			double dy = (from.getY() - brd.getY()) / dyd;

			to.setLocation(tls.getX() + (dx * dxs), brs.getY() + (dy * dys));
		}
	}
}
