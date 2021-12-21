package localisation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * A very poorly implemented {@link Properties} subclass which:
 * <ol>
 * <li>Retains the order in which the properties are added</li>
 * <li>Prints an error message when a property doesn't exist for a given
 * key</li>
 * </ol>
 *
 * @author Alex Mandelias
 */
final class OrderedProperties extends Properties {

	private final Map<Object, Object> map = new LinkedHashMap<>();

	@Override
	public String getProperty(String key) {
		final String property = super.getProperty(key);

		if (property == null)
			System.err.printf("No value found for key %s%n", key); //$NON-NLS-1$

		return property;
	}

	@Override
	public synchronized Object put(Object key, Object value) {
		map.put(key, value);
		return super.put(key, value);
	}

	@Override
	public Set<Map.Entry<Object, Object>> entrySet() {
		return map.entrySet();
	}
}
