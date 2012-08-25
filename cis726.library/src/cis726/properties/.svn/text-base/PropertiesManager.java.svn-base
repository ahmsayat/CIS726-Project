package cis726.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import cis726.logging.Logger;

/*
 * This class may be used for managing various properties of the system. The default file "properties.properties"
 * may be used for anything. If there are a large amount of domain specific properties, it may be reasonable to
 * create a separate properties file like I have for mail, "mail.properties". Methods are available for accessing
 * either the default properties file, or for accessing a custom one you have made.
 * 
 * Author: Kenton Born
 */
public class PropertiesManager {

  private static Properties properties;

  static {
    properties = new Properties ();

    try {
      properties.load (new FileInputStream ("../cis726.library/properties.properties"));
    }
    catch (IOException e) {
      System.out.println ("Error loading properties file: " + e.getMessage ());
    }
  }

  public static String getProperty (String property) {
    if (properties != null) {
      return properties.getProperty (property);
    }
    else {
      return "";
    }
  }

  public static boolean setProperty (String property, String value) {
    if (properties != null) {
      properties.setProperty (property, value);
      return true;
    }
    else {
      return false;
    }
  }

  public static String getProperty (String filename, String property) {
    Properties properties = loadProperties (filename);
    if (properties != null) {
      return properties.getProperty (property);
    }
    else {
      return "";
    }
  }

  public static boolean setProperty (String filename, String property, String value) {
    Properties properties = loadProperties (filename);
    if (properties != null) {
      properties.setProperty (property, value);
      if (saveProperties (filename, properties)) {
        return true;
      }
    }

    return false;
  }

  public static Properties loadProperties (String filename) {
    Properties properties = new Properties ();
    try {
      properties.load (new FileInputStream (filename));
    }
    catch (IOException e) {
      System.out.println ("Error loading properties file: " + e.getMessage ());
      Logger.logWarning ("Could not load \"" + filename + "\": " + e.getMessage ());
    }
    return properties;
  }

  public static boolean saveProperties (String filename, Properties props) {
    try {
      props.store (new FileOutputStream (filename), null);
    }
    catch (Exception e) {
      Logger.logWarning ("Could not save \"" + filename + "\": " + e.getMessage ());
      return false;
    }
    return true;
  }
}
