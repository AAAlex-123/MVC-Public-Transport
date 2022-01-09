package localisation;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import requirement.util.Requirements;

/**
 * Wrapper for a {@code ResourceBundle} that additionally provides a way to
 * alter the Locale the Application uses.
 * <p>
 * Since all of the Strings are loaded when the Application starts, changing the
 * Locale will take effect the next time the Application is launched.
 *
 * @author Alex Mandelias
 */
public final class Languages {

	/** The file containing information about the current Locale */
	public static final String FILE = "program_data\\languages.properties"; //$NON-NLS-1$

	/** The directory containing the .properties language files */
	public static final String LANGUAGES_DIRECTORY = "src\\localisation"; //$NON-NLS-1$

	private static final Properties properties = new OrderedProperties();

	private static final String  LANGUAGE_LITERAL = "Language"; //$NON-NLS-1$
	private static final String  COUNTRY_LITERAL  = "Country";  //$NON-NLS-1$
	private static final String  VARIANT_LITERAL  = "Variant";  //$NON-NLS-1$
	private static final Pattern pattern;

	private static final String BUNDLE_NAME = "localisation.language"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE;

	static {
		final String regex = String.format(
		        "^language(?:_(?<%s>[a-zA-Z]{2})(?:_(?<%s>[a-zA-Z]{2})(?:_(?<%s>[a-zA-Z]{2}))?)?)", //$NON-NLS-1$
		        Languages.LANGUAGE_LITERAL, Languages.COUNTRY_LITERAL, Languages.VARIANT_LITERAL);
		pattern = Pattern.compile(regex);

		try (BufferedReader reader = new BufferedReader(new FileReader(Languages.FILE))) {
			Languages.properties.load(reader);
		} catch (final FileNotFoundException e) {
			System.err.printf("File %s doesn't exist%n", Languages.FILE); //$NON-NLS-1$
			System.exit(0);
		} catch (final IOException e) {
			System.err.printf(
			        "Error while reading from file %s. Inform the developer about 'Languages.static-IO'%", //$NON-NLS-1$
			        Languages.FILE);
			System.exit(0);
		}

		RESOURCE_BUNDLE = ResourceBundle.getBundle(Languages.BUNDLE_NAME,
		        Languages.currentLocale());
	}

	/**
	 * Returns the String associated with the {@code key} in the ResourceBundle.
	 *
	 * @param key the key
	 *
	 * @return the String associated with the key
	 */
	public static String getString(String key) {
		try {
			return Languages.RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static Requirements getLanguageRequirements() {

		final List<Locale> locales = new ArrayList<>();

		final File directory = new File(Languages.LANGUAGES_DIRECTORY);
		for (File file : directory.listFiles()) {

			final String fname = file.getName();

			if (Languages.isLanguageFile(fname)) {
				final Matcher m = Languages.pattern.matcher(fname);
				if (!m.find())
					throw new RuntimeException(
					        String.format("Invalid language file name: %s", fname)); //$NON-NLS-1$

				final Function<String, String> f = (s -> s == null ? "" : s); //$NON-NLS-1$

				final String language = f.apply(m.group(Languages.LANGUAGE_LITERAL));
				final String country  = f.apply(m.group(Languages.COUNTRY_LITERAL));
				final String variant  = f.apply(m.group(Languages.VARIANT_LITERAL));
				locales.add(new Locale(language, country, variant));
			}
		}

		if (locales.isEmpty()) {
			return null;
		}

		final Requirements reqWrapper = new Requirements();
		reqWrapper.add(Languages.getString("MyMenu.1"), locales); //$NON-NLS-1$
		return reqWrapper;
	}

	public static boolean updateLanguageWithReqs(Requirements reqs) throws IOException {
		final Locale chosen  = reqs.getValue(Languages.getString("MyMenu.1"), Locale.class); //$NON-NLS-1$
		final Locale current = Languages.currentLocale();

		if ((chosen == null) || chosen.equals(current))
			return false;

		Languages.set(Languages.LANGUAGE_LITERAL, chosen.getLanguage());
		Languages.set(Languages.COUNTRY_LITERAL, chosen.getCountry());
		Languages.set(Languages.VARIANT_LITERAL, chosen.getVariant());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Languages.FILE))) {
			Languages.properties.store(writer, null);
		}

		return true;
	}

	/**
	 * Displays a pop-up dialog to select a Locale and write it to the file if it
	 * was changed.
	 *
	 * @param frame the parent frame of the pop-up dialog.
	 *
	 * @return {@code true} the Locale was changed, {@code false} otherwise
	 *
	 * @throws IOException if an error occurred while writing to file
	 */
	public static boolean editAndWriteToFile(Frame frame) throws IOException {

		final List<Locale> locales = new ArrayList<>();

		final File directory = new File(Languages.LANGUAGES_DIRECTORY);
		for (File file : directory.listFiles()) {

			final String fname = file.getName();

			if (Languages.isLanguageFile(fname)) {
				final Matcher m = Languages.pattern.matcher(fname);
				if (!m.find())
					throw new RuntimeException(
					        String.format("Invalid language file name: %s", fname)); //$NON-NLS-1$

				final Function<String, String> f = (s -> s == null ? "" : s); //$NON-NLS-1$

				final String language = f.apply(m.group(Languages.LANGUAGE_LITERAL));
				final String country  = f.apply(m.group(Languages.COUNTRY_LITERAL));
				final String variant  = f.apply(m.group(Languages.VARIANT_LITERAL));
				locales.add(new Locale(language, country, variant));
			}
		}

		if (locales.isEmpty()) {
			JOptionPane.showMessageDialog(frame,
			        String.format(Languages.getString("Languages.1"), //$NON-NLS-1$
			                Languages.LANGUAGES_DIRECTORY),
			        Languages.getString("Languages.2"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$
			return false;
		}

		final Requirements reqWrapper = new Requirements();
		reqWrapper.add(Languages.getString("MyMenu.1"), locales); //$NON-NLS-1$
		reqWrapper.fulfillWithDialog(frame, Languages.getString("Languages.3")); //$NON-NLS-1$

		final Locale chosen  = reqWrapper.getValue(Languages.getString("MyMenu.1"), Locale.class); //$NON-NLS-1$
		final Locale current = Languages.currentLocale();

		if ((chosen == null) || chosen.equals(current))
			return false;

		Languages.set(Languages.LANGUAGE_LITERAL, chosen.getLanguage());
		Languages.set(Languages.COUNTRY_LITERAL, chosen.getCountry());
		Languages.set(Languages.VARIANT_LITERAL, chosen.getVariant());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Languages.FILE))) {
			Languages.properties.store(writer, null);
		}

		return true;
	}

	/* Constructs a Locale from the information found in the properties */
	private static Locale currentLocale() {
		return new Locale(Languages.get(Languages.LANGUAGE_LITERAL),
		        Languages.get(Languages.COUNTRY_LITERAL), Languages.get(Languages.VARIANT_LITERAL));
	}

	private static String get(String propertyKey) {
		return Languages.properties.getProperty(propertyKey);
	}

	private static void set(String propertyKey, String propertyValue) {
		Languages.properties.setProperty(propertyKey, propertyValue);
	}

	private static boolean isLanguageFile(String filename) {
		return filename.startsWith("language_") && filename.endsWith(".properties"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
