
import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cis726.jms.JmsConsumer;
import cis726.jms.JmsProducer;
import cis726.jms.JmsSupport;
import cis726.properties.PropertiesManager;
import cis726.warning.manager.WarningsManager;
import cis726.warning.xml.Device;

/*
 * To run these test cases, ActiveMQ should be running. This will test the ability for the system to
 * register devices, unregister devices, throw warnings for high and low values, and throw warnings
 * for rising and lowering trends. It also implicitly tests the parsing ability for ActiveMQ messages.
 */
public class TestWarningSystem {
	WarningsManager manager;
	String register;
	String unregister;
	String warningAbove;
	String warningBelow;
	String warningTrend1;
	String warningTrend2;
	String warningTrend3;
	JmsProducer registerProducer;
	JmsProducer unregisterProducer;
	JmsProducer readingsProducer;
	JmsConsumer warningsConsumer;
	
	@Before
	public void setUp() throws Exception {
		manager = new WarningsManager("tcp://localhost:61616","registerDevice","unregisterDevice","deviceReadings");
		manager.start();
		
		register = "<device><id>1234</id><owner>bob</owner><warnings><warning><type>above</type><value>50.3</value><frequency>3600</frequency>";
    	register += "<contacts><contact><name>pressure guauge</name><relation>gui</relation><channel>cis726.warningstest</channel></contact></contacts></warning>";
    	register += "<warning><type>below</type><value>20.8</value><frequency>12</frequency><contacts><contact><name>Nancy</name><relation>nurse</relation>";
    	register += "<channel>cis726.warningstest</channel><mail>cis726.nurse@gmail.com</mail></contact></contacts></warning>";
    	register += "<warning><type>trend</type><value>3</value><frequency>3600</frequency>";
    	register += "<contacts><contact><name>pressure guauge</name><relation>gui</relation><channel>cis726.warningstest</channel></contact></contacts></warning></warnings></device>";
    	
    	unregister = "<device><id>1234</id></device>";
        warningAbove = "<reading><id>1234</id><value>52.1</value></reading>";
        warningBelow = "<reading><id>1234</id><value>18.7</value></reading>";
        warningTrend1 = "<reading><id>1234</id><value>25</value></reading>";
        warningTrend2 = "<reading><id>1234</id><value>26</value></reading>";
        warningTrend3 = "<reading><id>1234</id><value>27</value></reading>";
		
		registerProducer = JmsSupport.getProducer(PropertiesManager.getProperty("activemqloc"), PropertiesManager.getProperty("registerchannel"));
		unregisterProducer = JmsSupport.getProducer(PropertiesManager.getProperty("activemqloc"), PropertiesManager.getProperty("unregisterchannel"));
		readingsProducer = JmsSupport.getProducer(PropertiesManager.getProperty("activemqloc"), PropertiesManager.getProperty("readingschannel"));
		warningsConsumer = JmsSupport.getConsumer(PropertiesManager.getProperty("activemqloc"), "cis726.warningstest");
	}
	
	@Test
	public void testRegistration() { //register a new device, then unregister it
		registerProducer.sendText(register);
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Vector<Device> devices = manager.getDevices();
		assertTrue(devices.size()==1);
		assertTrue(devices.elementAt(0).getId() == 1234);
		
		unregisterProducer.sendText(unregister);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(devices.size()==0);
	}
	
	@Test
	public void testWarningAbove() { //test a reading coming in higher than the warning limit
		registerProducer.sendText(register);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		readingsProducer.sendText(warningAbove);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String receivedText = warningsConsumer.receiveText().trim();
		
		//assertTrue(receivedText.substring(0,67).equals("<device><id>1234</id><warning><type>above</type><value>52.1</value><time>3-19-2009 23:28:31</time></warning></device>".substring(0,67)));
		//This was modified to account for the xml->plaintext request I received from Jakub. The above
		//statement should properly test the xml if that is uncommented in the Device class.
		assertTrue(receivedText.equals("1234#above"));
	}
	
	@Test
	public void testWarningBelow() { //test a reading coming in lower than the warning limit
		registerProducer.sendText(register);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		readingsProducer.sendText(warningBelow);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String receivedText = warningsConsumer.receiveText().trim();

		//assertTrue(receivedText.substring(0,67).equals("<device><id>1234</id><warning><type>below</type><value>18.7</value><time>3-19-2009 23:28:31</time></warning></device>".substring(0,67)));
		//This was modified to account for the xml->plaintext request I received from Jakub. The above
		//statement should properly test the xml if that is uncommented in the Device class.
		assertTrue(receivedText.equals("1234#below"));
	}
	
	@Test
	public void testWarningTrendUp() { // test a set of warnings coming in trending upwards
		registerProducer.sendText(register);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		readingsProducer.sendText(warningTrend1);
		readingsProducer.sendText(warningTrend2);
		readingsProducer.sendText(warningTrend2);
		readingsProducer.sendText(warningTrend3);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String receivedText = warningsConsumer.receiveText().trim();

		//assertTrue(receivedText.substring(0,67).equals("<device><id>1234</id><warning><type>trend</type><value>27.0</value><time>3-19-2009 23:28:31</time></warning></device>".substring(0,67)));
		//This was modified to account for the xml->plaintext request I received from Jakub. The above
		//statement should properly test the xml if that is uncommented in the Device class.
		assertTrue(receivedText.equals("1234#trend"));
	}
	
	@Test
	public void testWarningTrendDown() { // test a set of warnings coming in trending downwards
		registerProducer.sendText(register);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		readingsProducer.sendText(warningTrend3);
		readingsProducer.sendText(warningTrend2);
		readingsProducer.sendText(warningTrend2);
		readingsProducer.sendText(warningTrend1);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String receivedText = warningsConsumer.receiveText().trim();
		
		//assertTrue(receivedText.substring(0,67).equals("<device><id>1234</id><warning><type>trend</type><value>25.0</value><time>3-19-2009 23:28:31</time></warning></device>".substring(0,67)));
		//This was modified to account for the xml->plaintext request I received from Jakub. The above
		//statement should properly test the xml if that is uncommented in the Device class.
		assertTrue(receivedText.equals("1234#trend"));
	}
	
	@After
	public void tearDown() throws Exception {
	}
}
