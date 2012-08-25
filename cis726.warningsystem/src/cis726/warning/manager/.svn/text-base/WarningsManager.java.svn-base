package cis726.warning.manager;

import java.util.Vector;

import cis726.jms.JmsConsumer;
import cis726.jms.JmsProducer;
import cis726.jms.JmsSupport;
import cis726.logging.Logger;
import cis726.properties.PropertiesManager;
import cis726.warning.mail.MailMessage;
import cis726.warning.mail.MailUtility;
import cis726.warning.xml.Device;
import cis726.warning.xml.WarningsParser;

/*
 * This class manages registering and unregistering warnings through ActiveMQ. The register and unregister
 * functions are idempotent, allowing them to be called several times without adverse effect.
 * 
 * Author: Kenton Born
 */
public class WarningsManager {
	private WarningsParser parser; //responsible for parsing the activeMQ xml messages
	private Vector<Device> devices; //the devices with registered warnings
	private JmsConsumer registerConsumer; //consumes messages for registering devices
	private JmsConsumer unregisterConsumer; //consumes messages for unregistering devices
	private JmsConsumer readingsConsumer; //consumes readings posted from devices
	private Thread registerThread;
	private Thread unregisterThread;
	private Thread readingsThread;
	private Thread mailThread;
	
	public WarningsManager(String address, String registerQueue, String unRegisterQueue, String readingsQueue) {
		parser = new WarningsParser();
		devices = new Vector<Device>();
		
		registerConsumer = JmsSupport.getConsumer(address, registerQueue);
		unregisterConsumer = JmsSupport.getConsumer(address, unRegisterQueue);
		readingsConsumer = JmsSupport.getConsumer(address, readingsQueue);
		
		mailThread = new Thread(new MailListener());
		registerThread = new Thread(new RegisterListener());
		unregisterThread = new Thread(new UnregisterListener());
		readingsThread = new Thread(new ReadingListener());
	}
	
	public void start() {
		registerThread.start();
		unregisterThread.start();
		readingsThread.start();
		
		mailThread.start();
	}
	
	public void stop() {
		//TODO: modify this to use undeprecated "stop()" method
		registerThread.stop();
		unregisterThread.stop();
		readingsThread.stop();
		mailThread.stop();
	}
	
	/*Idempotent: will remove any devices that currently exist under that ID and replace it
	It will first validate the xml, and return false if the object fails to be properly created */
	public boolean registerDevice(String xml) {
		//make sure the xml follows the schema
		if(!parser.isValidRegister(xml)) {
			Logger.logWarning("Received invalid register xml: " + xml);
			return false;
		}
		//try parsing the xml
		Device device = parser.parseDevice(xml);
		if(device != null) {
			Logger.logInfo("new device registered: " + device.getId());
			addDevice(device);
			return true;
		}
		else //the device is not valid, do not register it
			return false;
	}
	
	//unregisters any devices with this id from the manager
	public boolean unregisterDevice(String xml) {
		if(!parser.isValidUnRegister(xml)) {
			Logger.logWarning("Received invalid unregister xml: " + xml);
			return false;
		}

		int id = Integer.parseInt(xml.substring(12, xml.indexOf("<", 12)));
		Logger.logInfo("unregistering device: " + id);
		removeDevice(id);
		return true;
	}
	
	//finds the device the reading is for and checks if any warnings should be fired
	public boolean handleReading(String xml) {
		Reading reading = null;
		if(!parser.isValidReading(xml)) {
			//Logger.logWarning("Received invalid reading xml: " + xml);
			//return false;
			/*
			 * instead of returning false, I have changed this to account for the "plain text" solution
			 * requested by Jakub. In the real system, only xml should be handled
			 * plain text format: deviceID#value
			 */
			int splitIndex = xml.indexOf('#');
			if(splitIndex > -1 && splitIndex < xml.length()) {
				try {
				int deviceID = Integer.parseInt(xml.substring(0,splitIndex));
				float value = Float.parseFloat(xml.substring(splitIndex+1));
				reading = new Reading(deviceID, value);
				} catch(Exception e) {
					Logger.logWarning("Received invalid reading: " + xml);
					return false;
				}
			}
			else {
				Logger.logWarning("Received invalid reading: " + xml);
				return false;
			}		
		} 
		else {
			reading = parser.parseReading(xml);
		}
		for(Device device : devices) {
			if(device.getId() == reading.getDeviceID()) {
				//Commented by Luis. We don't want this message to avoid confusions.
				//Logger.logInfo("Received reading for registered device: " + device.getId());
				device.fireWarnings(reading);
				return true;
			}
		}
		
		return true;
	}
	
	//removes any devices with this id currently being watched, and replaces it with a new object
	//Another option would be to concatenate the two warnings together. This would have to be specified
	//in the requirements specification.
	private void addDevice(Device device) {
		removeDevice(device.getId());
		devices.add(device);
	}
	
	//removes any devices with this id from the manager
	private void removeDevice(int id) {
		for(int i = devices.size()-1; i >= 0; i--) {
			if(devices.get(i).getId() == id) {
				devices.remove(i);
			}
		}
	}
	
	//In the real system, we would probably want to be fine-grained here and just remove the contact.
	//It is possible that a gui or another doctor/nurse may be using this device/warning, but we will not
	//worry about that situation for now.
	private void removeContactFromDevice(String mailAddress, int deviceID) {
		removeDevice(deviceID);
	}
	
	public Vector<Device> getDevices() {
		return devices;
	}
	
	//This class runs a thread that constantly listens for registering devices
	private class RegisterListener implements Runnable {

		public void run() {
			do {
				String msg = registerConsumer.receiveText();
				registerDevice(msg);
			} while(true);
		}
	}
	
	//This class runs a thread that constantly listens for unregistering devices
	private class UnregisterListener implements Runnable {

		public void run() {
			do {
				String msg = unregisterConsumer.receiveText();
				unregisterDevice(msg);
			} while(true);
		}
	}
	
	//This class runs a thread that constantly listens for readings from devices
	private class ReadingListener implements Runnable {

		public void run() {
			do {
				String xml = readingsConsumer.receiveText();
				handleReading(xml);
				
			} while(true);
		}
	}
	
	//This class checks the warning system e-mail every 3 minutes looking for reply messages. These messages signal
	//that the contact (and for this implementation, the device) should be removed from the warning system.
	private class MailListener implements Runnable {

		public void run() {
			do {
				Vector<MailMessage> mailMessages = MailUtility.getMail(PropertiesManager.getProperty("mail.properties","username"), PropertiesManager.getProperty("mail.properties","password"));
				for(MailMessage message : mailMessages) {
					String fromAddress = message.getFrom();
					String subject = message.getSubject();
					int indexStart = subject.indexOf('\'');
					int indexEnd = subject.indexOf('\'', indexStart+1);
					String deviceString = subject.substring(indexStart+1, indexEnd);
					
					try { //extract the device id from the subject line and remove that device
						int deviceID = Integer.parseInt(deviceString);
						removeContactFromDevice(fromAddress, deviceID);
					} catch(Exception e) {
						Logger.logWarning("Could not parse device ID from mail: " + e.getMessage());
					}
				}
				
				try {
					Thread.sleep(60000); // sleep for 1 minute
				} catch (InterruptedException e) {
					Logger.logWarning("Error Sleeping in MailListener: " + e.getMessage());
				}
			} while(true);
		}
	}
}