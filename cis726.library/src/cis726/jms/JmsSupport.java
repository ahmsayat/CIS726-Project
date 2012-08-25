package cis726.jms;

import cis726.logging.Logger;

import java.io.Serializable;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

/**
 * This is a utility class which makes it easy to get a JMS producer or consumer for given topic and server address.
 *
 * @author Jakub Janecek
 */
public class JmsSupport {

  private JmsSupport () {
  }

  public static JmsProducer getProducer (String address, String name) {
    return new Producer (address, name, Type.TOPIC);
  }

  public static JmsConsumer getConsumer (String address, String name) {
    return new Consumer (address, name, Type.TOPIC);
  }

  public static JmsProducer getQueueProducer (String address, String name) {
    return new Producer (address, name, Type.QUEUE);
  }

  public static JmsConsumer getQueueConsumer (String address, String name) {
    return new Consumer (address, name, Type.QUEUE);
  }

  private static class Producer implements JmsProducer {

    private ActiveMQConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private MessageProducer producer = null;

    public Producer (String address, String name, Type type) {
      try {
        connectionFactory = new ActiveMQConnectionFactory (address);
        connection = connectionFactory.createConnection ();
        connection.start ();
        session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
        Destination producerDestination;
        switch (type) {
          case TOPIC:
            producerDestination = session.createTopic (name);
            break;
          case QUEUE:
            producerDestination = session.createQueue (name);
            break;
          default:
            producerDestination = session.createTopic (name);
        }
        producer = session.createProducer (producerDestination);
      }
      catch (JMSException ex) {
        Logger.logEx ("Producer couldn't be constructed!", ex);
      }
    }

    @Override
    public boolean sendText (String content) {
      TextMessage msg = new ActiveMQTextMessage ();
      try {
        msg.setText (content);
        producer.send (msg);
      }
      catch (JMSException ex) {
        Logger.logEx ("TextMessage couldn't be sent!", ex);
        return false;
      }
      return true;
    }

    @Override
    public boolean sendObject (Serializable content) {
      ObjectMessage msg = new ActiveMQObjectMessage ();
      try {
        msg.setObject (content);
        producer.send (msg);
      }
      catch (JMSException ex) {
        Logger.logEx ("ObjectMessage couldn't be sent!", ex);
        return false;
      }
      return true;
    }
  }

  private static class Consumer implements JmsConsumer {

    private ActiveMQConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private MessageConsumer consumer = null;

    public Consumer (String address, String name, Type type) {
      try {
        connectionFactory = new ActiveMQConnectionFactory (address);
        connection = connectionFactory.createConnection ();
        connection.start ();
        session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
        Destination consumerDestination;
        switch (type) {
          case TOPIC:
            consumerDestination = session.createTopic (name);
            break;
          case QUEUE:
            consumerDestination = session.createQueue (name);
            break;
          default:
            consumerDestination = session.createTopic (name);
        }
        consumer = session.createConsumer (consumerDestination);
      }
      catch (JMSException ex) {
        Logger.logEx ("Consumer couldn't be constructed!", ex);
      }
    }

    @Override
    public String receiveText () {
      try {
        Message m = consumer.receive ();
        if (m instanceof TextMessage) {
          return ((TextMessage) m).getText ();
        }
      }
      catch (JMSException ex) {
        Logger.logEx ("TextMessage couldn't be received!", ex);
      }
      return "";
    }

    @Override
    public Object receiveObject () {
      try {
        Message m = consumer.receive ();
        if (m instanceof ObjectMessage) {
          return ((ObjectMessage) m).getObject ();
        }
      }
      catch (JMSException ex) {
        Logger.logEx ("ObjectMessage couldn't be received!", ex);
      }
      return null;
    }
  }

  private static enum Type {

    TOPIC, QUEUE
  }
}
