package cis726.db;

import cis726.logging.Logger;
import cis726.entities.*;
import cis726.db.dbClass.PatientProfile;

import cis726.properties.PropertiesManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This is a utility class which makes it easy to do database stuff. Just add public static method if you need to save something
 * into the database or load something from it.
 *
 * @author Jakub Janecek
 * Modified by Team GUI
 */
public class DbSupport {

  private static final String driver;
  private static final String dbUrl;
  private static final String user;
  private static final String pass;
  private static final long TIMEOUT = 60000;
  private static Connection connection = null;
  private static long lastUse = 0;

  static {
    Properties p = PropertiesManager.loadProperties ("../cis726.library/db.properties");
    driver = p.getProperty ("driver");
    dbUrl = p.getProperty ("url");
    user = p.getProperty ("user");
    pass = p.getProperty ("pass");
    try {
      Class.forName (driver);
      connection = DriverManager.getConnection (dbUrl, user, pass);
    }
    catch (SQLException ex) {
      Logger.logEx ("Database connection couldn't be created!", ex);
    }
    catch (ClassNotFoundException ex) {
      Logger.logEx ("Database connection couldn't be created due to class loading problem!", ex);
    }
  }

  private DbSupport () {
  }

  /**
   * This method validates the database connection. If the connection timed out or is closed it creates a new one instead.
   * This method should be called in every public method before any work is done.
   */
  private static void validate () {
    boolean validated = true;
    try {
      connection.getMetaData ();
    }
    catch (SQLException ex) {
      validated = false;
    }
    if ((System.currentTimeMillis () - lastUse) > TIMEOUT || !validated) {
      try {
        connection.close ();
      }
      catch (SQLException ex) {
      }
      try {
        connection = DriverManager.getConnection (dbUrl, user, pass);
      }
      catch (SQLException ex) {
        Logger.logEx ("Database connection couldn't be created!", ex);
      }
    }
    lastUse = System.currentTimeMillis ();
  }

  public static boolean saveDeviceReading (int deviceId, String value) {
    validate ();
    try {
      PreparedStatement statement = connection.prepareStatement (
          "INSERT INTO cis726.devicereading VALUES (NULL, ?, ?, NOW())");
      statement.setInt (1, deviceId);
      statement.setString (2, value);
      int result = statement.executeUpdate ();
      if (result == 1) {
        return true;
      }
    }
    catch (SQLException ex) {
      Logger.logEx ("SQLException while saveDeviceReading!", ex);
    }
    return false;
  }

  public static boolean saveDeviceRegistration (int deviceId, int roomId, String deviceType, String topic) {
    validate ();
    try {
      PreparedStatement statement = connection.prepareStatement (
          "INSERT INTO cis726.registereddevice VALUES (NULL, ?, ?, ?, ?)");
      statement.setInt (1, deviceId);
      statement.setInt (2, roomId);
      statement.setString (3, deviceType);
      statement.setString (4, topic);
      int result = statement.executeUpdate ();
      if (result == 1) {
        return true;
      }
    }
    catch (SQLException ex) {
      Logger.logEx ("SQLException while saveDeviceRegistration!", ex);
    }
    return false;
  }

  /**
   * Retrieve the values from patientprofile table
   */
  public static PatientProfile getPatientProfileFromRoomId (int roomId) {
    validate ();
    try {
      PreparedStatement statement = connection.prepareStatement (
          "select * from cis726.patientprofile where Patient_Id in (select Patient_Id from patientroomdetails where Room_Id=?)");
      statement.setInt (1, roomId);
      ResultSet rs = statement.executeQuery ();
      if (rs != null) {
        while (rs.next ()) {
          PatientProfile profile = new PatientProfile (
              rs.getString ("Patient_Id"),
              rs.getString ("Patient_Name"),
              rs.getInt ("Age"),
              rs.getInt ("Weight"),
              rs.getString ("Medical_History"),
              rs.getString ("Allergies"),
              rs.getDate ("timestamp"));
          return profile;
        }
      }
    }
    catch (SQLException ex) {
      Logger.logEx ("SQLException while Profile retrieval!", ex);
    }
    return null;
  }

  /*
   * Get the current devices registered for a room/patient.
   */
  public static ArrayList<RegisteredDevice> getDevicesByRoom (int roomId) {
    validate ();
    //TODO: make this to be called on demand
    ArrayList<RegisteredDevice> devices = new ArrayList<RegisteredDevice> ();
    try {
      PreparedStatement statement = connection.prepareStatement (
          "SELECT * FROM cis726.registereddevice WHERE roomId=?");
      statement.setInt (1, roomId);
      ResultSet rs = statement.executeQuery ();
      // Loop through the result set
      while (rs.next ()) {
        RegisteredDevice device = new RegisteredDevice (
            rs.getInt ("deviceId"),
            rs.getInt ("roomId"),
            rs.getString ("deviceType"),
            rs.getString ("topic"));
        devices.add (device);
      }

    }
    catch (SQLException ex) {
      Logger.logEx ("SQLException while getDevicesByRoom!, roomId=" + roomId, ex);
    }
    return devices;
  }

  /*
   * Get the last inputs of a device
   */
  public static ArrayList<DeviceReading> getLastInputsByDevice (int deviceId, int numInputs) {
    validate ();
    //TODO: make this to be called on demand
    ArrayList<DeviceReading> rows = new ArrayList<DeviceReading> ();
    try {
      PreparedStatement statement = connection.prepareStatement (
          "select * from (" +
          "select * FROM cis726.devicereading WHERE deviceId = ? ORDER BY timestamp DESC limit ?)" +
          "as tbl order by timestamp");
      statement.setInt (1, deviceId);
      statement.setInt (2, numInputs);
      ResultSet rs = statement.executeQuery ();
      // Loop through the result set
      while (rs.next ()) {
        DeviceReading reading = new DeviceReading (
            rs.getInt ("deviceId"),
            rs.getString ("value"));
        rows.add (reading);
      }

    }
    catch (SQLException ex) {
      Logger.logEx ("SQLException while getLastInputsByDevice!, deviceId=" + deviceId, ex);
    }
    return rows;
  }

  public static boolean saveWarning (int deviceId, String type, float value, float reading) {
    validate ();
    try {
      PreparedStatement statement = connection.prepareStatement (
          "INSERT INTO cis726.warnings(device, type, value, reading, time) VALUES (?, ?, ?, ?, NOW())");
      statement.setInt (1, deviceId);
      statement.setString (2, type);
      statement.setFloat (3, value);
      statement.setFloat (4, reading);
      int result = statement.executeUpdate ();
      if (result == 1) {
        return true;
      }
    }
    catch (Exception e) {
      Logger.logWarning ("Could not save warning to database: " + e.getMessage ());
    }
    return false;
  }
}
