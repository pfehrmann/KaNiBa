package de.kaniba.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class handles the logging.
 * @author Philipp
 *
 */
public class LoggingUtils {
	private static final Logger LOGGER = Logger.getLogger("KaNiBa");
	
	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS.%1$tL %4$-11s %5$s%6$s%n");
		SimpleFormatter formatter = new SimpleFormatter();
		try {
			String path = "kaniba%u.log";
			FileHandler handler = new FileHandler(path, true);
			handler.setFormatter(formatter);
			LOGGER.addHandler(handler);
		} catch (SecurityException | IOException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	private LoggingUtils() {
		// This is just a static utility class, no constructor needed.
	}

	/**
	 * Loggs a message.
	 * 
	 * @param msg
	 */
	public static void log(String msg) {
		LOGGER.info(msg);
	}

	/**
	 * Logs an exception.
	 * 
	 * @param e
	 */
	public static void exception(Exception e) {
		LOGGER.log(Level.WARNING, e.getMessage(), e);
	}

}
