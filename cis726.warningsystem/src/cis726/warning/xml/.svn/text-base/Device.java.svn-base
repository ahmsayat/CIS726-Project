package cis726.warning.xml;

import java.util.Properties;
import java.util.Vector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cis726.jms.JmsProducer;
import cis726.jms.JmsSupport;
import cis726.properties.PropertiesManager;
import cis726.warning.mail.MailUtility;
import cis726.warning.manager.Reading;
import cis726.warning.xml.WarningsParser;
import cis726.db.DbSupport;
import cis726.logging.Logger;

/*
 * This class is an object representing a device from a warning registration. It is constructed from a DOM Node
 * object representing a device. Objects of this class are created from the WarningsParser.
 * 
 * Author: Kenton Born
 */
public class Device {
	private int id; //id of the device
	private String owner; //owner of the device
	private Vector<Warning> warnings; //set of warnings that should be monitored for the device
	
	//builds a Device from a device DOM node. The xml should already be validated
	public Device(Node deviceNode) {
		warnings = new Vector<Warning>();
		
		NodeList elements = deviceNode.getChildNodes();
		for(int i = 0; i < elements.getLength(); i++) {
			Node nextNode = elements.item(i);
			if(nextNode.getNodeName().equals("id"))
				id = Integer.parseInt(nextNode.getTextContent());
			else if(nextNode.getNodeName().equals("owner"))
				owner = nextNode.getTextContent();
			else if(nextNode.getNodeName().equals("warnings")) {
				NodeList warningsList = nextNode.getChildNodes();
				for(int index = 0; index < warningsList.getLength(); index++) {
					Node warningNode = warningsList.item(index);
					Warning warning = new Warning(warningNode);
					warnings.add(warning);
				}
	        }
		}
	}

	/*Takes the most recent list of readings, and looks for warnings that should fire.
	If one is found, appropriate actions should be taken */
	public void fireWarnings(Reading reading) {
		for(Warning warning : warnings) {
			if(warning.shouldFire(reading)) {
				Logger.logInfo("device " + id + " detected a warning!");
				
				if(!DbSupport.saveWarning(id, warning.getType(), warning.getValue(), reading.getValue()))
					Logger.logWarning("Could not save warning to database!");
				
				for(Contact contact : warning.getContacts()) {
					if((contact.getChannel() != null)) {  //the warning registration requested an active-MQ message be sent
						/*
						String warningMessage = "<device><id>"+id+"</id>";
						warningMessage += "<warning><type>"+warning.getType()+"</type>";
						warningMessage += "<value>"+reading.getValue()+"</value>";
						warningMessage += "<time>"+WarningsParser.getDateString()+"</time></warning></device>";
						*/
						/*
						 * The above code has been commented out at the request of Jakub and a few others because
						 * they did not have the time to handle xml on their end. Instead, they requested it sent in
						 * plain text in the following format: deviceID#WARNING;valueTooHIgh
						 */
						
						/*
						 * Changed 5/1/2009. Luis
						 * new format deviceID#WARNING. For UI the value is not needed anymore (avoid duplicated signal)
						 */
						String warningMessage = id + "#" + warning.getType();
						//End change
			//Added by Luis. Message to make debugging, when an alert was launched.
System.out.println(warningMessage);
						JmsProducer producer = JmsSupport.getProducer(PropertiesManager.getProperty("activemqloc"), contact.getChannel());
						producer.sendText(warningMessage);
					}
					if((contact.getMail() != null)) { //the warning registration requested an e-mail be sent
						Properties properties = PropertiesManager.loadProperties("mail.properties");
						String warningMessage = "Device: " + id + '\n';
						warningMessage += "Warning type: " + warning.getType() + ' ' + warning.getValue() + '\n';
						warningMessage += "Value: " + reading.getValue() + '\n';
						warningMessage += "Time: " + WarningsParser.getDateString() + "\n\n";
						warningMessage += "If you would like to remove yourself from the warning system for this device, simply reply to this e-mail and leave the subject unchanged!'";
						
						Logger.logInfo("Sending warning e-mail to: " + contact.getMail());
						
						//send the e-mail
						if(!MailUtility.sendMail(properties.getProperty("email"), contact.getMail(), properties.getProperty("username"), properties.getProperty("password"), "Device '" + id + "' has issued a warning", warningMessage)) {
							Logger.logWarning("Error sending e-mail to " + contact.getMail());
						}
					}
					if(contact.getPhone() != null) {
						//TODO: Send an SMS! (will not be implemented for this class)
					}
				}
			}
		}
	}
	
	public int getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public Vector<Warning> getWarnings() {
		return warnings;
	}
	
	public String toString() {
		String text = new String("\nDEVICE\n");
		if(id != 0)
			text+= "id: " + id + '\n';
		if(owner != null)
			text+= "owner: " + owner + '\n';
		for(int i = 0; i < warnings.size(); i++)
			text+= "warning:\n" + warnings.get(i);
		
		return text;
	}
}
