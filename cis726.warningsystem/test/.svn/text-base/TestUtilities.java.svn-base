
import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cis726.properties.PropertiesManager;

import cis726.warning.mail.MailMessage;
import cis726.warning.mail.MailUtility;

/*
 * These tests verify that the PropertiesManager and MailUtility are functioning properly.
 */
public class TestUtilities {
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testPropertiesManager() { //verify properties can be retrieved from the proper files
		
		assertTrue(PropertiesManager.getProperty("activemqloc") != null);
		assertTrue(PropertiesManager.getProperty("mail.properties","username") != null);
		assertTrue(PropertiesManager.getProperty("shouldnotexistdrandresonlikesburritos") == null);
	}
	
	@Test
	public void testMailUtility() { //verify that e-mails can be sent and received
		
		assertTrue(MailUtility.sendMail(PropertiesManager.getProperty("mail.properties","email"), "cis726.nurse@gmail.com", PropertiesManager.getProperty("mail.properties","username"), PropertiesManager.getProperty("mail.properties","password"), "device '1234' has issued a warning", "blah blah"));
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Vector<MailMessage> messages = MailUtility.getMail("cis726.nurse@gmail.com", "bluescreen");
		assertTrue(messages.size() > 0);
		
		boolean found = false;
		for(int i = 0; i < messages.size(); i++) {
			if(messages.get(i).getContent().trim().equals("blah blah")) {
				found = true;
				break;
			}
		}
		assertTrue(found);
	}
	
	@After
	public void testMail() throws Exception {
	}
}
