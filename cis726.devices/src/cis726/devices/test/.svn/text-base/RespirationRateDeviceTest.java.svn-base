package cis726.devices.test;

import cis726.devices.RespirationRateDevice;
import org.junit.Test;
import static org.junit.Assert.*;

public class RespirationRateDeviceTest {

  private RespirationRateDevice d;
  private Thread t;

  public RespirationRateDeviceTest () {
    try {
      d = new RespirationRateDevice (7, 100);
      d.setTopic ("RespirationRateDeviceTestTopic");
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
      assertTrue (d.getRate () > 0);
      assertTrue (d.getRate () < 40);
      i++;
      if (i > 3) {
        break;
      }
      try {
        Thread.sleep (60000);
      }
      catch (InterruptedException ex) {
        ex.printStackTrace ();
      }
    }
    d.turnOff ();
    try {
      Thread.sleep (61000);
      t.interrupt ();
    }
    catch (InterruptedException ex) {
    }
  }
}
