package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.IController;
import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;

/**
 * TODO
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
class OASAEntityGraphicFactory implements AbstractEntityGraphicFactory {
	
	private static final Font largeFont = new Font ("TimesRoman", Font.BOLD, 24);
	private static final Font standardFont = new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 18);
	private static final Font smallFont = new Font ("TimesRoman", Font.PLAIN, 12);
	private static final Color textColor = Color.BLACK;
	private static final Color backgroundColor = Color.WHITE;
	private static final int ICON_SIZE = 54;
	
	private AbstractView.ImageLoader loader;
	
	public OASAEntityGraphicFactory(AbstractView.ImageLoader loader) {
		this.loader = loader;
	}
	
	public void setLoader(AbstractView.ImageLoader newLoader) {
		loader = newLoader;
	}
		
	@Override
	public JPanel getETownGraphic(ETown town) {
		JPanel graphic = prepareGraphic();
		
		graphic.add(new JLabel(new ImageIcon(loader.loadImage("town.png", ICON_SIZE, ICON_SIZE))));
		graphic.add(Box.createHorizontalBox());
		
		JLabel nameLabel = new JLabel(town.getName());
		nameLabel.setFont(standardFont);
		nameLabel.setForeground(textColor);
		nameLabel.setBackground(backgroundColor);
		
		graphic.add(nameLabel);
		return graphic;
	}

	@Override
	public JPanel getELineGraphic(ELine line) {
		JPanel graphic = prepareGraphic();
		
		graphic.add(new JLabel(new ImageIcon(loader.loadImage(line.getType().getSpriteName(), ICON_SIZE, ICON_SIZE))));
		graphic.add(Box.createHorizontalBox());
		
		JPanel namePanel = new JPanel();
		namePanel.setBackground(backgroundColor);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
		
		JLabel numLabel = new JLabel(line.getLineNumber());
		numLabel.setFont(largeFont);
		numLabel.setForeground(textColor);
		
		JLabel descrLabel = new JLabel(line.getName());
		descrLabel.setFont(standardFont);
		descrLabel.setForeground(textColor);
		
		namePanel.add(numLabel);
		namePanel.add(descrLabel);
		
		graphic.add(namePanel);
		
		return graphic;
	}

	@Override
	public JPanel getEStationGraphic(EStation station) {
		JPanel graphic = prepareGraphic();
		
		graphic.add(new JLabel(new ImageIcon(loader.loadImage("station.png", ICON_SIZE, ICON_SIZE))));
		graphic.add(Box.createHorizontalBox());
		
		JPanel namePanel = new JPanel();
		namePanel.setBackground(backgroundColor);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
		
		JLabel numLabel = new JLabel(station.getName());
		numLabel.setFont(largeFont);
		numLabel.setForeground(textColor);
		
		JLabel descrLabel = new JLabel("in " + station.getTown().getName());
		descrLabel.setFont(smallFont);
		descrLabel.setForeground(textColor);
		
		namePanel.add(numLabel);
		namePanel.add(descrLabel);
		
		graphic.add(namePanel);
		
		return graphic;
	}

	@Override
	public JPanel getETimetableGraphic(ETimetable timetable) {
		JPanel graphic = prepareGraphic();
		
		JLabel nameLabel = new JLabel(timetable.toString());
		nameLabel.setFont(largeFont);
		nameLabel.setForeground(textColor);
		nameLabel.setBackground(backgroundColor);
		
		graphic.add(nameLabel);
		
		return graphic;
	}
	
	private static JPanel prepareGraphic() {
		JPanel graphic = new JPanel();
		graphic.setLayout(new BoxLayout(graphic, BoxLayout.X_AXIS));
		graphic.setBackground(backgroundColor);
		graphic.setBorder(BorderFactory.createLineBorder(textColor, 2));
		graphic.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		return graphic;
	}
}
