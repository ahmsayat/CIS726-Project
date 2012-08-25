package cis726.warning.test;

import cis726.jms.JmsProducer;
import cis726.jms.JmsSupport;
import cis726.warning.manager.WarningsManager;

public class WarningsManagerTest {
	public static void main(String[] args) {
		
		WarningsManager manager = new WarningsManager("tcp://localhost:61616","registerDevice","unregisterDevice","deviceReadings");
		manager.start();
		
		String register = "<device><id>1234</id><owner>bob</owner><warnings><warning><type>above</type><value>50.3</value><frequency>3600</frequency>";
    	register += "<contacts><contact><name>pressure guauge</name><relation>gui</relation><channel>cis726.warnings</channel></contact></contacts></warning>";
    	register += "<warning><type>below</type><value>20.8</value><frequency>12</frequency><contacts><contact><name>Nancy</name><relation>nurse</relation>";
    	register += "<mail>cis726.nurse@gmail.com</mail></contact></contacts></warning></warnings></device>";
    	
    	String unregister = "<device><id>1234</id></device>";
        String reading = "<reading><id>1234</id><value>19.0</value></reading>";
		
		JmsProducer registerProducer = JmsSupport.getProducer("tcp://localhost:61616", "registerDevice");
		JmsProducer unregisterProducer = JmsSupport.getProducer("tcp://localhost:61616", "unregisterDevice");
		JmsProducer readingsProducer = JmsSupport.getProducer("tcp://localhost:61616", "deviceReadings");
		
		
		registerProducer.sendText(register);
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		unregisterProducer.sendText(unregister);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		registerProducer.sendText(register);
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		readingsProducer.sendText(reading);
	}
	
}
