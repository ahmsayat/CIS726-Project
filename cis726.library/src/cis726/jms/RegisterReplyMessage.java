package cis726.jms;

import java.io.Serializable;

/**
 * Value object representing a message for device registration reply.
 *
 * @author Jakub Janecek
 */
public class RegisterReplyMessage implements Serializable {

  public static final long serialVersionUID = 1L;
  public int deviceId;
  public String deviceTopic;

  public RegisterReplyMessage (int deviceId, String deviceTopic) {
    this.deviceId = deviceId;
    this.deviceTopic = deviceTopic;
  }
}
