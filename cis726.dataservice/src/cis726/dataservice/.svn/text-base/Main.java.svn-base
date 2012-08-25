package cis726.dataservice;

import cis726.jms.JmsConsumer;
import cis726.jms.JmsSupport;
import cis726.jms.RegisterMessage;
import cis726.logging.Logger;
import cis726.properties.PropertiesManager;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is a main class of DataService. It creates JMS consumers on certain topics and listens for device registration and
 * device readings.
 *
 * @author Jakub Janecek
 */
public class Main {

  private static ExecutorService pool = Executors.newCachedThreadPool ();

  public Main () {
  }

  public static void main (String[] args) {
    Properties p = PropertiesManager.loadProperties ("../cis726.library/jms.properties");
    final JmsConsumer jmsRegister = JmsSupport.getQueueConsumer (p.getProperty ("server"), p.getProperty ("registerQueue"));
    if (jmsRegister == null) {
      Logger.logWarning ("Couldn't connect to JMS.");
      System.exit (-1);
    }
    final JmsConsumer jmsDevices = JmsSupport.getConsumer (p.getProperty ("server"), p.getProperty ("devices"));
    if (jmsDevices == null) {
      Logger.logWarning ("Couldn't connect to JMS.");
      System.exit (-1);
    }
    new Thread (new Runnable () {

      public void run () {
        while (true) {
          Object o = jmsRegister.receiveObject ();
          if (o instanceof RegisterMessage) {
            pool.execute (new RegisterProcessor ((RegisterMessage) o));
          }
          else {
            Logger.logWarning ("Register message of wrong type!");
          }
        }
      }
    }).start ();
    new Thread (new Runnable () {

      public void run () {
        while (true) {
          String s = jmsDevices.receiveText ();
          pool.execute (new MessageProcessor (s));
        }
      }
    }).start ();
  }
}
