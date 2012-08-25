package cis726.devices;

import cis726.jms.JmsConsumer;
import cis726.jms.JmsSupport;
import cis726.jms.RegisterReplyMessage;
import cis726.properties.PropertiesManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This is a main class which just starts all devices and gives them IDs.
 *
 * @author Jakub Janecek
 */
public class Main {

  private static Map<Integer, Device> devices = new HashMap<Integer, Device> ();

  public Main () {
  }

  public static void main (String[] args) {
    try {
      PulseDevice pulse = new PulseDevice (1, 100);
      devices.put (pulse.getId (), pulse);
      PressureDevice pressure = new PressureDevice (2, 100);
      devices.put (pressure.getId (), pressure);
      TemperatureDevice temperature = new TemperatureDevice (3, 100);
      devices.put (temperature.getId (), temperature);
      EkgDevice ekg = new EkgDevice (4, 100);
      devices.put (ekg.getId (), ekg);
      RespirationRateDevice respRate = new RespirationRateDevice (5, 100);
      devices.put (respRate.getId (), respRate);
      OxygenDevice oxygen = new OxygenDevice (6, 100);
      devices.put (oxygen.getId (), oxygen);
      InputOutputDevice io = new InputOutputDevice (7, 100);
      devices.put (io.getId (), io);
      TodoDevice todo = new TodoDevice (8, 100);
      devices.put (todo.getId (), todo);

      pulse = new PulseDevice (9, 200);
      devices.put (pulse.getId (), pulse);
      pressure = new PressureDevice (10, 200);
      devices.put (pressure.getId (), pressure);
      temperature = new TemperatureDevice (11, 200);
      devices.put (temperature.getId (), temperature);
      ekg = new EkgDevice (12, 200);
      devices.put (ekg.getId (), ekg);
      respRate = new RespirationRateDevice (13, 200);
      devices.put (respRate.getId (), respRate);
      oxygen = new OxygenDevice (14, 200);
      devices.put (oxygen.getId (), oxygen);
      io = new InputOutputDevice (15, 200);
      devices.put (io.getId (), io);
      todo = new TodoDevice (16, 200);
      devices.put (todo.getId (), todo);
      new Thread (new Runnable () {

        public void run () {
          Properties props = PropertiesManager.loadProperties ("../cis726.library/jms.properties");
          JmsConsumer c = JmsSupport.getQueueConsumer (props.getProperty ("server"), props.getProperty ("registerReplyQueue"));
          if (c != null) {
            while (true) {
              Object o = c.receiveObject ();
              if (o instanceof RegisterReplyMessage) {
                RegisterReplyMessage msg = (RegisterReplyMessage) o;
                Device d = devices.get (msg.deviceId);
                if (d != null) {
                  d.setTopic (msg.deviceTopic);
                  Registrar.register (d, msg.deviceTopic);
                  new Thread (d).start ();
                }
              }
            }
          }
        }
      }).start ();
    }
    catch (Exception ex) {
      ex.printStackTrace ();
    }
  }
}
