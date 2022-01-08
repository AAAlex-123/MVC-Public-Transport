package view;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import entity.Position;

/**
 * A Factory producing String representations of entities.
 * 
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class StringEntityGraphicFactory implements AbstractEntityGraphicFactory<String> {
	
	@Override
	public void initializeView(AbstractGUIView view) {}

	@Override
	public String getETownGraphic(ETown town) {
		return town.getName();
	}

	@Override
	public String getELineGraphic(ELine line) {
		return String.format("%s (%s): %s", line.getLineNumber(), line.getType(), line.getName());
	}

	@Override
	public String getEStationGraphic(EStation station) {
		Position pos = station.getPosition();
		StringBuffer buff = new StringBuffer(station.getName() + " at coordinates: ");
		
		buff.append(Math.abs(pos.getX()));
		
		if(pos.getX() > 0)
			buff.append('N');
		else 
			buff.append('S');
		
		buff.append(' ');
		buff.append(Math.abs(pos.getY()));
		
		if(pos.getY() > 0)
			buff.append('W');
		else 
			buff.append('E');
		
		return buff.toString();
	}

	@Override
	public String getETimetableGraphic(ETimetable timetable) {
		return String.format("%s:%s", formatTime(timetable.getHours()), formatTime(timetable.getMinutes()));
	}
	
	private static String formatTime(int time) {
		if(time < 10)
			return "0" + Integer.toString(time);
		else 
			return Integer.toString(time);
	}

}
