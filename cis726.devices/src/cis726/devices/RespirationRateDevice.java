package cis726.devices;

import cis726.logging.Logger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jakub Janecek
 */
public class RespirationRateDevice extends Device {

  private AtomicInteger rate = new AtomicInteger (15);
  private Random r = new Random ();

  public RespirationRateDevice (int deviceId, int roomId) throws Exception {
    super (deviceId, roomId);
  }

  public void run () {

    while (!stop) {
      int delta = r.nextInt (4);
      delta = r.nextBoolean () ? delta : -delta;
      int value = rate.addAndGet (delta);
      if (value > 24) {
        rate.addAndGet (-3);
      }
      if (value < 8) {
        rate.addAndGet (3);
      }
      producer.sendText (getId () + "#" + getRate ());
      globalProducer.sendText (getId () + "#" + getRate ());
      try {
        Thread.sleep (60000);
      }
      catch (InterruptedException ex) {
        Logger.logEx ("RespirationRateDevice interrupted!", ex);
      }
    }
  }

  public int getRate () {
    return rate.get ();
  }
}
