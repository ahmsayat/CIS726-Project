package cis726.devices;

import cis726.logging.Logger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jakub Janecek
 */
public class PulseDevice extends Device {

  private AtomicInteger pulse = new AtomicInteger (70);
  private Random r = new Random ();

  public PulseDevice (int deviceId, int roomId) throws Exception {
    super (deviceId, roomId);
  }

  public void run () {

    while (!stop) {
      int delta = r.nextInt (4);
      delta = r.nextBoolean () ? delta : -delta;
      int p = pulse.addAndGet (delta);
      if (p > 250) {
        pulse.addAndGet (-10);
      }
      if (p < 0) {
        pulse.addAndGet (5);
      }
      producer.sendText (getId () + "#" + getPulse ());
      globalProducer.sendText (getId () + "#" + getPulse ());
      try {
        Thread.sleep (1000);
      }
      catch (InterruptedException ex) {
        Logger.logEx ("PulseDevice interrupted!", ex);
      }
    }
  }

  public int getPulse () {
    return pulse.get ();
  }
}
