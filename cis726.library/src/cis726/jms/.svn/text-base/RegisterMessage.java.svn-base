package cis726.jms;

import java.io.Serializable;

/**
 * Value object representing a message for device registration.
 *
 * @author Jakub Janecek
 */
public class RegisterMessage implements Serializable {

  public static final long serialVersionUID = 1L;

  public int deviceId;
  public int roomId;
  public String deviceType;
  

  public RegisterMessage (int deviceId, int roomId, String deviceType) {
    this.deviceId = deviceId;
    this.roomId = roomId;
    this.deviceType = deviceType;
  }
}
