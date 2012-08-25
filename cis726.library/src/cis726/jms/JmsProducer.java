package cis726.jms;

import java.io.Serializable;

/**
 * @author Jakub Janecek
 */
public interface JmsProducer {

  public boolean sendText (String content);

  public boolean sendObject (Serializable content);
}
