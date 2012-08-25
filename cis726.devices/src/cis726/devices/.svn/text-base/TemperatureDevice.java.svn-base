package cis726.devices;

import cis726.logging.Logger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jakub Janecek
 */
public class TemperatureDevice extends Device {

  private AtomicInteger temperature = new AtomicInteger (98);
  private Random r = new Random ();

  public TemperatureDevice (int deviceId, int roomId) throws Exception {
    super (deviceId, roomId);
  }

  public void run () {
    while (!stop) {
      int delta = r.nextInt (1);
      delta = r.nextBoolean () ? delta : -delta;
      int t = temperature.addAndGet (delta);
      if (t > 114) {
        temperature.addAndGet (-3);
      }
      if (t < 77) {
        temperature.addAndGet (3);
      }
      producer.sendText (getId () + "#" + getTemperature ());
      globalProducer.sendText (getId () + "#" + getTemperature ());
      try {
        Thread.sleep (60000);
      }
      catch (InterruptedException ex) {
        Logger.logEx ("TemperatureDevice interrupted!", ex);
      }
    }
  }

  public int getTemperature () {
    return temperature.get ();
  }
}
