package cis726.devices;

import cis726.jms.JmsProducer;
import cis726.jms.JmsSupport;
import cis726.jms.RegisterMessage;
import cis726.logging.Logger;
import cis726.properties.PropertiesManager;
import java.util.Properties;

/**
 * Abstract class of Device.
 *
 * @author Jakub Janecek
 */
public abstract class Device implements Runnable {

  protected int deviceId;
  protected int roomId;
  protected boolean stop = false;
  protected JmsProducer globalProducer;
  protected JmsProducer producer;

  public Device (int deviceId, int roomId) throws Exception {
    this.deviceId = deviceId;
    this.roomId = roomId;
    Properties props = PropertiesManager.loadProperties ("../cis726.library/jms.properties");
    JmsProducer p = JmsSupport.getQueueProducer (props.getProperty ("server"), props.getProperty ("registerQueue"));
    globalProducer = JmsSupport.getProducer (props.getProperty ("server"), props.getProperty ("devices"));
    if (p == null) {
      Logger.logWarning ("Device #" + deviceId + "(" + this.getClass ().getSimpleName () + ") cannot be instantiated!");
      throw new Exception ("Device #" + deviceId + "(" + this.getClass ().getSimpleName () + ") cannot be instantiated!");
    }
    p.sendObject (new RegisterMessage (deviceId, roomId, this.getClass ().getSimpleName ().replace ("Device", "")));
  }

  public int getId () {
    return deviceId;
  }

  public int getRoomId () {
    return roomId;
  }

  public void setTopic (String topic) {
    Properties props = PropertiesManager.loadProperties ("../cis726.library/jms.properties");
    producer = JmsSupport.getProducer (props.getProperty ("server"), topic);
    if (producer == null) {
      Logger.logWarning ("Device #" + deviceId + "(" + this.getClass ().getSimpleName () + ") cannot send messages to its topic!");
    }
  }

  public void turnOff () {
    stop = true;
  }
}
