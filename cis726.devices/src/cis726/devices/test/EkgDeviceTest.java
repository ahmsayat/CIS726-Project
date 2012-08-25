package cis726.devices.test;

import cis726.devices.EkgDevice;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jakub Janecek
 */
public class EkgDeviceTest {

  private EkgDevice d;
  private Thread t;

  public EkgDeviceTest () {
    try {
      d = new EkgDevice (2, 100);
      d.setTopic ("EkgDeviceTestTopic");
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
      assertTrue (d.getEkg () > -3.1);
      assertTrue (d.getEkg () < 5.1);
      i++;
      if (i > 300) {
        break;
      }
      try {
        Thread.sleep (85);
      }
      catch (InterruptedException ex) {
        ex.printStackTrace ();
      }
    }
    d.turnOff ();
    try {
      Thread.sleep (300);
      t.interrupt ();
    }
    catch (InterruptedException ex) {
    }
  }
}
