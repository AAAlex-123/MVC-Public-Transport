package view;

import java.util.Calendar;

import entity.ELine;
import entity.EStation;
import entity.ETimestamp;
import entity.ETown;
import entity.Position;
import localisation.Languages;

/**
 * A Factory producing String representations of entities.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class StringEntityRepresentationFactory
        implements AbstractEntityRepresentationFactory<String> {

	@Override
	public String getETownRepresentation(ETown town) {
		return town.getName();
	}

	@Override
	public String getELineRepresentation(ELine line) {
		return String.format("%s (%s): %s", line.getName(), line.getType(), line.getDescription()); //$NON-NLS-1$
	}

	@Override
	public String getDetailedELineRepresentation(ELine line, EStation station) {
		Calendar  instance = Calendar.getInstance();
		final int hours    = instance.get(Calendar.HOUR_OF_DAY);
		final int minutes  = instance.get(Calendar.MINUTE);

		final ETimestamp now         = new ETimestamp(hours, minutes);
		final ETimestamp arrivalTime = line.getNextArrival(station, now);

		final String timeToArrival;
		if (arrivalTime == null)
			timeToArrival = "--:--"; //$NON-NLS-1$
		else
			timeToArrival = arrivalTime.sub(now).getFormattedTime();


		return String.format("%s (%s): %s - %s", line.getName(), line.getType(), //$NON-NLS-1$
		        line.getDescription(), timeToArrival);
	}


	@Override
	public String getEStationRepresentation(EStation station) {
		Position     pos  = station.getPosition();

		StringBuffer buff = new StringBuffer(
		        String.format(Languages.getString("StringEntityRepresentationFactory.0"), station.getName())); //$NON-NLS-1$

		buff.append(Math.abs(pos.getX()));
		buff.append(pos.getX() > 0 ? 'N' : 'S');
		buff.append(' ');
		buff.append(Math.abs(pos.getY()));
		buff.append(pos.getY() > 0 ? 'W' : 'E');

		return buff.toString();
	}

	@Override
	public String getETimestampRepresentation(ETimestamp timestamp) {
		return timestamp.getFormattedTime();
	}
}
