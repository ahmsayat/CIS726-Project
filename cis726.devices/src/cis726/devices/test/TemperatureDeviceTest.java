package cis726.devices.test;

import cis726.devices.TemperatureDevice;
import org.junit.Test;
import static org.junit.Assert.*;

public class TemperatureDeviceTest {

  private TemperatureDevice d;
  private Thread t;

  public TemperatureDeviceTest () {
    try {
      d = new TemperatureDevice (3, 100);
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
      assertTrue (d.getTemperature () > 77);
      assertTrue (d.getTemperature () < 114);
      i++;
      if (i > 5) {
        break;
      }
      try {
        Thread.sleep (61000);
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
