package cis726.jms.test;

import cis726.jms.JmsConsumer;
import cis726.jms.JmsProducer;
import cis726.jms.JmsSupport;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jakub Janecek
 */
public class JmsSupportTest {

  public JmsSupportTest () {
  }

  @Test
  public void test () {
    JmsProducer prod = JmsSupport.getProducer ("tcp://localhost:61616", "JmsSupportTest");
    JmsConsumer cons = JmsSupport.getConsumer ("tcp://localhost:61616", "JmsSupportTest");
    prod.sendText ("test1");
    prod.sendText ("test2");
    assertTrue (cons.receiveText ().equals ("test1"));
    prod.sendText ("test3");
    assertTrue (cons.receiveText ().equals ("test2"));
    assertTrue (cons.receiveText ().equals ("test3"));
    prod = JmsSupport.getQueueProducer ("tcp://localhost:61616", "JmsSupportTest");
    cons = JmsSupport.getQueueConsumer ("tcp://localhost:61616", "JmsSupportTest");
    prod.sendText ("test1");
    prod.sendText ("test2");
    assertTrue (cons.receiveText ().equals ("test1"));
    prod.sendText ("test3");
    assertTrue (cons.receiveText ().equals ("test2"));
    assertTrue (cons.receiveText ().equals ("test3"));
  }
}
