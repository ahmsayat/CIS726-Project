package cis726.jms;

import java.io.Serializable;

/**
 * Value object representing a message with device reading.
 *
 * @author Jakub Janecek
 */
public class DeviceReadingMessage implements Serializable {

  public static final long serialVersionUID = 1L;
  public int deviceId;
  public Object reading;

  public DeviceReadingMessage (int deviceId, Object reading) {
    this.deviceId = deviceId;
    this.reading = reading;
  }
}
