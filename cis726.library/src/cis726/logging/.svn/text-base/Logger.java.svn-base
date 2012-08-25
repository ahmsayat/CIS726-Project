package cis726.logging;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * This is a utility class for logging. It uses the Singleton design pattern therefore it is accessible everywhere
 *
 * @author Jakub Janecek
 */
public class Logger {

  private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger ("log");

  static {
    try {
      Calendar c = Calendar.getInstance ();
      String currentDateAndTime = "" + c.get (Calendar.YEAR) + c.get (Calendar.MONTH) + c.get (Calendar.DATE) + "_" + c.get (
          Calendar.HOUR_OF_DAY) + c.get (Calendar.MINUTE) + c.get (Calendar.SECOND) + "_";
      logger.addHandler (new FileHandler (currentDateAndTime + ".log", true));
    }
    catch (IOException ex) {
      ex.printStackTrace ();
    }
    catch (SecurityException ex) {
      ex.printStackTrace ();
    }
  }

  private Logger () {
  }

  public static void logInfo (String msg) {
    logger.log (Level.INFO, msg);
  }

  public static void logWarning (String msg) {
    logger.log (Level.WARNING, msg);
  }

  public static void logEx (String msg, Exception ex) {
    logger.log (Level.SEVERE, msg, ex);
  }
}
