package cis726.devices.test;

import cis726.devices.PressureDevice;
import org.junit.Test;
import static org.junit.Assert.*;

public class PressureDeviceTest {

  private PressureDevice d;
  private Thread t;

  public PressureDeviceTest () {
    try {
      d = new PressureDevice (6, 100);
      d.setTopic ("PressureDeviceTestTopic");
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
      assertTrue (d.getSystolic () < 180);
      assertTrue (d.getSystolic () > 70);
      assertTrue (d.getDiastolic () < 120);
      assertTrue (d.getDiastolic () > 40);
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
