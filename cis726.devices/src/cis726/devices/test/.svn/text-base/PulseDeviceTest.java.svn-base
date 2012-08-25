package cis726.devices.test;

import cis726.devices.PulseDevice;
import org.junit.Test;
import static org.junit.Assert.*;

public class PulseDeviceTest {

  private PulseDevice d;
  private Thread t;

  public PulseDeviceTest () {
    try {
      d = new PulseDevice (1, 100);
      d.setTopic ("PulseDeviceTestTopic");
    }
    catch (Exception ex) {
      ex.printStackTrace ();
    }
    t = new Thread (d);
    t.start ();
  }

  @Test
  public void test () {
    int i = 0;
    while (true) {
      assertTrue (d.getPulse () > 0);
      assertTrue (d.getPulse () < 250);
      i++;
      if (i > 80) {
        break;
      }
      try {
        Thread.sleep (900);
      }
      catch (InterruptedException ex) {
        ex.printStackTrace ();
      }
    }
    d.turnOff ();
    try {
      Thread.sleep (2000);
      t.interrupt ();
    }
    catch (InterruptedException ex) {
    }
  }
}
