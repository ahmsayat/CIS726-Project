package cis726.devices;

import cis726.jms.JmsProducer;
import cis726.jms.JmsSupport;
import cis726.properties.PropertiesManager;
import java.util.Properties;

/**
 *
 * @author Jakub Janecek
 */
public class Registrar {

  private static Properties props = PropertiesManager.loadProperties ("../cis726.library/jms.properties");

  private Registrar () {
  }

  public static void register (Device d, String topic) {
    String register;
    if (d.getRoomId () == 100) {
      if (d instanceof PulseDevice) {
        register = "<device><id>" + d.getId () + "</id><owner>Jakub</owner><warnings>" +
                   "<warning><type>above</type><value>150</value><frequency>60</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning>" +
                   "<warning><type>below</type><value>45</value><frequency>60</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning></warnings></device>";
      }
      else if (d instanceof TemperatureDevice) {
        register = "<device><id>" + d.getId () + "</id><owner>Jakub</owner><warnings>" +
                   "<warning><type>above</type><value>109</value><frequency>60</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning>" +
                   "<warning><type>below</type><value>83</value><frequency>60</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning></warnings></device>";
      }
      else if (d instanceof OxygenDevice) {
        register = "<device><id>" + d.getId () + "</id><owner>Jakub</owner><warnings>" +
                   "<warning><type>below</type><value>75</value><frequency>60</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning></warnings></device>";
      }
      else if (d instanceof RespirationRateDevice) {
        register = "<device><id>" + d.getId () + "</id><owner>Jakub</owner><warnings>" +
                   "<warning><type>above</type><value>30</value><frequency>60</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning>" +
                   "<warning><type>below</type><value>7</value><frequency>60</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning></warnings></device>";
      }
      else {
        return;
      }
    }
    else {
      if (d instanceof PulseDevice) {
        register = "<device><id>" + d.getId () + "</id><owner>Jakub</owner><warnings>" +
                   "<warning><type>above</type><value>100</value><frequency>40</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning>" +
                   "<warning><type>below</type><value>80</value><frequency>40</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning></warnings></device>";
      }
      else if (d instanceof TemperatureDevice) {
        register = "<device><id>" + d.getId () + "</id><owner>Jakub</owner><warnings>" +
                   "<warning><type>above</type><value>90</value><frequency>80</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning>" +
                   "<warning><type>below</type><value>88</value><frequency>80</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning></warnings></device>";
      }
      else if (d instanceof OxygenDevice) {
        register = "<device><id>" + d.getId () + "</id><owner>Jakub</owner><warnings>" +
                   "<warning><type>below</type><value>100</value><frequency>30</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning></warnings></device>";
      }
      else if (d instanceof RespirationRateDevice) {
        register = "<device><id>" + d.getId () + "</id><owner>Jakub</owner><warnings>" +
                   "<warning><type>above</type><value>20</value><frequency>60</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning>" +
                   "<warning><type>below</type><value>15</value><frequency>60</frequency><contacts><contact><name>Jakub</name><relation>gui</relation><channel>" +
                   topic + "</channel></contact></contacts></warning></warnings></device>";
      }
      else {
        return;
      }
    }
    JmsProducer registerProducer = JmsSupport.getProducer (props.getProperty ("server"),
                                                           props.getProperty ("warningsRegistration"));
    registerProducer.sendText (register);
  }
}
