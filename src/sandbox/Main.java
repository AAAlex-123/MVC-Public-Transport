package sandbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import entity.Coordinates;

@SuppressWarnings({ "nls", "javadoc" })
public class Main {

	private static final double EQUATORIAL_RADIUS      = 6_378_137;
	private static final double VOLUMETRIC_MEAN_RADIUS = 6_371_000;
	private static final double LATITUDE_AT_CENTER     = 37.97543985747732;

	private static double lonToX(double lon) {
		return VOLUMETRIC_MEAN_RADIUS * lon * Math.cos(LATITUDE_AT_CENTER);
	}

	private static double latToY(double lat) {
		return VOLUMETRIC_MEAN_RADIUS * lat;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> doTheThing());
	}

	private static void doTheThing() {
		final String imgname = "37.97066403382808_23.903633118641128_37.95683671311368_23.9314925592738.png";

		final String[] coords = imgname.split("_");
		coords[3] = coords[3].substring(0, coords[3].lastIndexOf("."));

		final BufferedImage img;
		try {
			File file = new File(System.getProperty("user.dir") + "\\src\\sandbox\\" + imgname);
			System.out.println(file);
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		JFrame f = buildFrame();
		JPanel p = new JPanel() {
						@Override
						public void paintComponent(Graphics g) {
							super.paintComponent(g);
							g.drawImage(img, 0, 0, null);
						}
					};

		Dimension dim = new Dimension(img.getWidth(), img.getHeight());
		configurePanel(p, dim);

		final Function<Object, Double> func = s -> Double.parseDouble((String) s);

		Coordinates ul = new Coordinates(func.apply(coords[0]), func.apply(coords[1]));
		Coordinates br = new Coordinates(func.apply(coords[2]), func.apply(coords[3]));

		final Mapper m = new Mapper(ul, br, dim);

		JTextField tf = new JTextField(30);

		JButton rb = new JButton("plot");
		rb.addActionListener(e -> {
			// Requirements reqs = new Requirements();
			// reqs.add("x", StringType.ANY);
			// reqs.add("y", StringType.ANY);
			// reqs.add("c", StringType.ANY);
			// reqs.fulfillWithDialog(f, "plot");

			// double x = reqs.getValue("x", func);
			// double y = reqs.getValue("y", func);
			// String[] in = reqs.getValue("c", String.class).replace("\\s+", "").split(",");

			String[] in = tf.getText().replace("\\s+", "").split(",");
			System.out.println(tf.getText());
			tf.setText("");
			double lat = func.apply(in[0]);
			double lon = func.apply(in[1]);

			Coordinates old    = new Coordinates(lat, lon);
			Point       mapped = m.map(old);

			Graphics g = p.getGraphics();
			g.setColor(Color.YELLOW);
			final int size = 20;
			g.fillOval((int) mapped.getX() - (size / 2), (int) mapped.getY() - (size / 2), size,
			        size);
		});

		JPanel top = new JPanel(new FlowLayout());
		f.setLayout(new BorderLayout());
		f.add(p, BorderLayout.CENTER);
		top.add(rb);
		top.add(tf);
		f.add(top, BorderLayout.NORTH);
		f.setVisible(true);
	}

	private static class Mapper {
		public final Coordinates  tl, br;
		public final Dimension dim;

		public Mapper(Coordinates tl, Coordinates br, Dimension dim) {
			this.tl = tl;
			this.br = br;
			this.dim = dim;
		}


		public Point map(Coordinates p) {
			double dx = (p.longitude - tl.longitude) / (br.longitude - tl.longitude);
			double dy = (p.latitude - br.latitude) / (tl.latitude - br.latitude);

			return new Point((int) (dx * dim.getWidth()), (int) ((1 - dy) * dim.getHeight()));
		}
	}

	private static JFrame buildFrame() {
		JFrame frame = new JFrame("iamge sandbox");
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		return frame;
	}

	private static void configurePanel(JPanel panel, Dimension dim) {
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createLineBorder(Color.PINK));
		panel.setMaximumSize(dim);
		panel.setSize(dim);
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);
	}
}
