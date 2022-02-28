package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import controller.IImageController;
import entity.Coordinates;
import entity.ELine;
import entity.EStation;
import entity.ETimestamp;
import entity.ETown;
import requirement.util.Requirements;

/**
 * TODO
 *
 *
 * @author Alex Mandelias
 */
public class MapView extends AbstractGUIView {

	/**
	 * TODO
	 *
	 * @param factory
	 * @param imageController
	 * @param width
	 * @param height
	 */
	public MapView(IImageController imageController) {
		super(new MapEntityRepresentationFactory(), imageController, 850, 650);

		((MapEntityRepresentationFactory) factory).initializeView(this);
	}

	@Override
	public void updateViewWithHomepage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateViewWithTowns(List<ETown> towns) {
		// TODO Auto-generated method stub

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
	@SuppressWarnings("nls")
	public void updateViewWithStations(List<EStation> stations) {
		// TODO Auto-generated method stub

		// Mapper.test();

		final String imgname = "37.97066403382808_23.903633118641128_37.95683671311368_23.9314925592738.png";

		assert imgname.equals(
		        "37.97066403382808_23.903633118641128_37.95683671311368_23.9314925592738.png");

		Coordinates img_tl, img_br;
		{
			final String[] coords = imgname.split("_");
			coords[3] = coords[3].substring(0, coords[3].lastIndexOf("."));

			final Function<Object, Double> f = s -> Double.parseDouble((String) s);

			img_tl = new Coordinates(f.apply(coords[0]), f.apply(coords[1]));
			img_br = new Coordinates(f.apply(coords[2]), f.apply(coords[3]));
		}

		final BufferedImage img;
		try {
			File file = new File(System.getProperty("user.dir") + "\\src\\sandbox\\" + imgname);
			System.out.println(file);
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Dimension img_dim = new Dimension(img.getWidth(), img.getHeight());

		final Mapper mapper = new Mapper(img_tl, img_br, img_dim);

		Coordinates cropped_tl, cropped_br;

		{
			// get positions and bounds from positions
			double m = Double.MAX_VALUE;
			double t = -m, l = m, b = m, r = -m;

			for (EStation station : stations) {
				Coordinates pos = station.getCoordinates();
				t = Math.max(t, pos.latitude);
				l = Math.min(l, pos.longitude);
				b = Math.min(b, pos.latitude);
				r = Math.max(r, pos.longitude);
			}

			cropped_tl = new Coordinates(t, l);
			cropped_br = new Coordinates(b, r);
		}

		// create jpanel - map with these bounds

		Point mapped_n_tl = mapper.map(cropped_tl);
		Point mapped_n_br = mapper.map(cropped_br);
		Point mapped_o_tl = mapper.map(img_tl);

		int x = (int) Math.max(mapped_o_tl.getX(), mapped_n_tl.getX() - 30);
		int y = (int) Math.max(mapped_o_tl.getY(), mapped_n_tl.getY() - 30);
		int w = (int) Math.min((mapped_n_br.getX() - x) + 30, img_dim.width - x);
		int h = (int) Math.min((mapped_n_br.getY() - y) + 30, img_dim.height - y);
		System.out.printf("%d-%d-%d-%d%n", x, y, w, h);

		final BufferedImage cropped = img.getSubimage(x, y, w, h);

		JPanel p = new JPanel(null) {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(cropped, 0, 0, null);
				System.out.println("drawing img");
				System.out.printf("Drawing %d children:%n", this.getComponentCount());
				for (Component c : this.getComponents()) {
					Point pt = c.getLocation();
					System.out.printf("At pos: %s%n", pt);
					int size = 10;
					g.fillOval(pt.x - (size / 2), pt.y - (size / 2), size, size);
				}
			}
		};

		Coordinates final_n_tl = mapper.invmap(new Point(x, y));
		Coordinates final_n_br = mapper.invmap(new Point(x + w, y + h));

		System.out.printf("%s - %s%n", final_n_tl, final_n_br);

		final Mapper new_mapper = new Mapper(final_n_tl, final_n_br,
		        new Dimension(cropped.getWidth(), cropped.getHeight()));

		// plot every station with factory
		for (EStation station : stations) {
			Coordinates pos    = station.getCoordinates();
			Point    mapped = new_mapper.map(pos);

			JComponent stationG = factory.getEStationRepresentation(station);
			stationG.setLocation(mapped.x, mapped.y);
			p.add(stationG);
		}

		super.updatePanel(p);
	}

	private static class Mapper {
		public final Coordinates  tl, br;
		public final Dimension dim;

		public Mapper(Coordinates tl, Coordinates br, Dimension dim) {
			this.tl = tl;
			this.br = br;
			this.dim = dim;
		}

		public static void test() {
			Mapper m = new Mapper(new Coordinates(0, 50), new Coordinates(50, 0),
			        new Dimension(100, 100));

			Coordinates pos    = new Coordinates(20, 30);
			Point    mapped = m.map(pos);
			Coordinates orig   = m.invmap(mapped);
		}

		public Point map(Coordinates p) {
			double dx = (p.longitude - tl.longitude) / (br.longitude - tl.longitude);
			double dy = (p.latitude - br.latitude) / (tl.latitude - br.latitude);

			return new Point((int) (dx * dim.getWidth()), (int) ((1 - dy) * dim.getHeight()));
		}

		public Coordinates invmap(Point p) {

			double dx = p.getX() / dim.getWidth();
			double dy = (dim.getHeight() - p.getY()) / dim.getHeight();

			double dlat = tl.latitude - br.latitude;
			double dlon = br.longitude - tl.longitude;

			return new Coordinates((dy * dlat) + br.latitude, (dx * dlon) + tl.longitude);
		}
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

}
