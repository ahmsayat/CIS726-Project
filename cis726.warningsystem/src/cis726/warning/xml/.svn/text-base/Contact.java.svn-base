package cis726.warning.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cis726.logging.Logger;

/*
 * This class is an object representing a contact for a warning registration. It is constructed from a DOM Node
 * object representing a contact. Objects of this class are created from the Warning class through the WarningsParser.
 * 
 * Author: Kenton Born
 */
public class Contact {
	String name; //name of the contact
	String relation; //type of contact (i.e. doctor, nurse, gui, other)
	String channel; //phone number (optional)
	String mail; //phone number (optional)
	String phone; //phone number (optional)
	
	//builds a Contact from a contact DOM node. The xml should already be validated
	public Contact(Node node) {
		NodeList nodeList = node.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node nextNode = nodeList.item(i);
			
			if(nextNode.getNodeName().equals("name"))
				name = nextNode.getTextContent();
			else if(nextNode.getNodeName().equals("relation"))
				relation = nextNode.getTextContent();
			else if(nextNode.getNodeName().equals("phone"))
				phone = nextNode.getTextContent();
			else if(nextNode.getNodeName().equals("mail"))
				mail = nextNode.getTextContent();
			else if(nextNode.getNodeName().equals("channel"))
				channel = nextNode.getTextContent();
			else
				Logger.logWarning("Error parsing contact: invalid tag");
		}
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return relation;
	}

	public String getPhone() {
		return phone;
	}

	public String getMail() {
		return mail;
	}

	public String getChannel() {
		return channel;
	}
	
	public String toString() {
		String text = new String();
		if(name != null)
			text+= "		name: " + name + '\n';
		if(relation != null)
			text+= "		type: " + relation + '\n';
		if(phone != null)
			text+= "		phone: " + phone + '\n';
		if(mail != null)
			text+= "		mail: " + mail + '\n';
		if(channel != null)
			text+= "		channel: " + channel;
		
		return text;
	}
}