package cis726.dataservice;

import cis726.db.DbSupport;
import cis726.jms.JmsProducer;
import cis726.jms.JmsSupport;
import cis726.jms.RegisterMessage;
import cis726.jms.RegisterReplyMessage;
import cis726.logging.Logger;
import cis726.properties.PropertiesManager;
import java.util.Properties;
import java.util.Random;

/**
 * This is a class which processes messages with device registrations.
 *
 * @author Jakub Janecek
 */
class RegisterProcessor implements Runnable {

  private RegisterMessage msg;

  public RegisterProcessor (RegisterMessage registerMessage) {
    msg = registerMessage;
  }

  public void run () {
    Random r = new Random ();
    String topic = "device" + msg.deviceId + "_" + r.nextLong ();
    boolean result = DbSupport.saveDeviceRegistration (msg.deviceId, msg.roomId, msg.deviceType, topic);
    Properties props = PropertiesManager.loadProperties ("../cis726.library/jms.properties");
    JmsProducer p = JmsSupport.getQueueProducer (props.getProperty ("server"), props.getProperty ("registerReplyQueue"));
    if (p != null) {
      if (!result) {
        Logger.logWarning ("Device registration not saved to database!");
      }
      else {
        p.sendObject (new RegisterReplyMessage (msg.deviceId, topic));
      }
    }
    else {
      Logger.logWarning ("Couldn't reply to device registration message!");
    }
  }
}
