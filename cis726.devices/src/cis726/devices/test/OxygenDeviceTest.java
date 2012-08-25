package cis726.devices.test;

import cis726.devices.OxygenDevice;
import org.junit.Test;
import static org.junit.Assert.*;

public class OxygenDeviceTest {

  private OxygenDevice d;
  private Thread t;

  public OxygenDeviceTest () {
    try {
      d = new OxygenDevice (5, 100);
      d.setTopic ("OxygenDeviceTestTopic");
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
      assertTrue (d.getOxygen () <= 100);
      assertTrue (d.getOxygen () > 69);
      i++;
      if (i > 6) {
        break;
      }
      try {
        Thread.sleep (30000);
      }
      catch (InterruptedException ex) {
        ex.printStackTrace ();
      }
    }
    d.turnOff ();
    try {
      Thread.sleep (31000);
      t.interrupt ();
    }
    catch (InterruptedException ex) {
    }
  }
}
