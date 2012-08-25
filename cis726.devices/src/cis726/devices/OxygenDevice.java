package cis726.devices;

import cis726.logging.Logger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jakub Janecek
 */
public class OxygenDevice extends Device {

  private AtomicInteger oxygen = new AtomicInteger (93);
  private Random r = new Random ();

  public OxygenDevice (int deviceId, int roomId) throws Exception {
    super (deviceId, roomId);
  }

  public void run () {

    while (!stop) {
      int delta = r.nextInt (3);
      delta = r.nextBoolean () ? delta : -delta;
      int value = oxygen.addAndGet (delta);
      if (value > 100) {
        oxygen.set (100);
      }
      if (value < 70) {
        oxygen.addAndGet (5);
      }
      producer.sendText (getId () + "#" + getOxygen ());
      globalProducer.sendText (getId () + "#" + getOxygen ());
      try {
        Thread.sleep (30000);
      }
      catch (InterruptedException ex) {
        Logger.logEx ("PulseDevice interrupted!", ex);
      }
    }
  }

  public int getOxygen () {
    return oxygen.get ();
  }
}
