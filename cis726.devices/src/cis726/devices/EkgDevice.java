package cis726.devices;

import cis726.logging.Logger;

/**
 * @author Jakub Janecek
 */
public class EkgDevice extends Device {

  private static double[] values = {0, 0, 0, 0, 1, 0.2, 0, -0.8, 5, -3, 0, 0, 0.5, 0.5, 0, 0, 0, 0, 1, 0.2, 0, -0.8, 5, -3, 0, 0,
                                    0.5, 0.5};
  private int i = 0;

  public EkgDevice (int deviceId, int roomId) throws Exception {
    super (deviceId, roomId);
  }

  public void run () {
    while (!stop) {
      producer.sendText (getId () + "#" + values[i % values.length]);
      globalProducer.sendText (getId () + "#" + values[i % values.length]);
      i++;
      try {
        Thread.sleep (85);
      }
      catch (InterruptedException ex) {
        Logger.logEx ("EkgDevice interrupted!", ex);
      }
    }
  }

  public double getEkg () {
    return values[i % values.length];
  }
}
