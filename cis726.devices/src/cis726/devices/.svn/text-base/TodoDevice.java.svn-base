package cis726.devices;

import cis726.jms.JmsProducer;
import cis726.jms.JmsSupport;
import cis726.jms.RegisterMessage;
import cis726.logging.Logger;
import cis726.properties.PropertiesManager;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

/**
 * @author Jakub Janecek
 */
public class TodoDevice extends Device {

  private Random r = new Random ();
  private String[] sentences = new String[]{"Patient's X-ray", "Nurse needs to check IV", "Doctor's visit",
                                            "Small operation scheduled", "Blood sample", "IV change", "Bandage check", "Bath ;)"};
  private Calendar cal = Calendar.getInstance ();

  public TodoDevice (int deviceId, int roomId) throws Exception {
    super (deviceId, roomId);
  }

  public void run () {

    while (!stop) {
      int i = r.nextInt (sentences.length);
      long sentenceId = r.nextLong ();
      producer.sendText (getId () + "#INSERT;" + sentenceId + ";" + sentences[i] + ";" + (cal.get (Calendar.HOUR_OF_DAY) + r.
          nextInt (6)));
      globalProducer.sendText (getId () + "#INSERT;" + sentenceId + ";" + sentences[i] + ";" +
                               (cal.get (Calendar.HOUR_OF_DAY) + 1));
      try {
        Thread.sleep (60000);
      }
      catch (InterruptedException ex) {
        Logger.logEx ("TodoDevice interrupted!", ex);
      }
    }
  }
}
