package cis726.devices;

import cis726.logging.Logger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jakub Janecek
 */
public class PressureDevice extends Device {

  private AtomicInteger systolic = new AtomicInteger (100);
  private AtomicInteger diastolic = new AtomicInteger (70);
  private Random r1 = new Random ();
  private Random r2 = new Random ();

  public PressureDevice (int deviceId, int roomId) throws Exception {
    super (deviceId, roomId);
  }

  public void run () {
    while (!stop) {
      int delta1 = r1.nextInt (4);
      int delta2 = r2.nextInt (4);
      delta1 = r1.nextBoolean () ? delta1 : -delta1;
      delta2 = r2.nextBoolean () ? delta2 : -delta2;
      int s = systolic.addAndGet (delta1);
      int d = diastolic.addAndGet (delta1);
      if (s > 180) {
        systolic.addAndGet (-10);
      }
      if (s < 70) {
        systolic.addAndGet (10);
      }
      if (d > 120) {
        diastolic.addAndGet (-10);
      }
      if (d < 40) {
        diastolic.addAndGet (10);
      }
      producer.sendText (getId () + "#" + getSystolic () + "/" + getDiastolic ());
      globalProducer.sendText (getId () + "#" + getSystolic () + "/" + getDiastolic ());
      try {
        Thread.sleep (30000);
      }
      catch (InterruptedException ex) {
        Logger.logEx ("PressureDevice interrupted!", ex);
      }
    }
  }

  public int getSystolic () {
    return systolic.get ();
  }

  public int getDiastolic () {
    return diastolic.get ();
  }
}
