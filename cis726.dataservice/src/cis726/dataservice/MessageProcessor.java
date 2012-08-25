package cis726.dataservice;

import cis726.db.DbSupport;
import cis726.logging.Logger;

/**
 * This is a class which processes received messages with device readings.
 *
 * @author Jakub Janecek
 */
class MessageProcessor implements Runnable {

  private String received;

  public MessageProcessor (String received) {
    this.received = received;
  }

  public void run () {
    String[] parts = received.split ("#");
    if (parts.length == 2) {
      boolean result = DbSupport.saveDeviceReading (Integer.valueOf (parts[0]), parts[1]);
      if (!result) {
        Logger.logWarning ("Device reading not saved to database!");
      }
    }
    else {
      Logger.logWarning ("Wrong message format!");
    }
  }
}
