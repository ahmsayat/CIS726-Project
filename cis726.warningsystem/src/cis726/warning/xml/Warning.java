package cis726.warning.xml;

import java.util.Calendar;
import java.util.Vector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cis726.logging.Logger;
import cis726.warning.manager.Reading;

/*
 * This class is an object representing the information from a warning registration. It is constructed from a DOM Node
 * object representing a warning. Objects of this class are created from a Device through the WarningsParser.
 * 
 * Author: Kenton Born
 */
public class Warning {
	
	private String type; //type of warning, i.e. "above", "below"
	private float value; //value that will set off the warning
	private int frequency; //frequency (in seconds) that the warning should be delivered when triggered
	private Vector<Contact> contacts; //the contacts a warning should be sent to
	private long lastFired; //the last time this warning fired (stored in milliseconds)
	private Reading[] readings; //the last 20 readings
	private int index = 0; //current index into the readings array
	
	//builds a warning from a warning DOM node. The xml should already be validated
	public Warning(Node warning) {
		lastFired = 0;
		NodeList elements = warning.getChildNodes();
		contacts = new Vector<Contact>();
		readings = new Reading[20];
		for(int i = 0; i < readings.length; i++) {
			readings[i] = null;
		}

    	for(int i = 0; i < elements.getLength(); i++) {
    		Node nextNode = elements.item(i);

    		if(nextNode.getNodeName().equals("type"))
    			type = nextNode.getTextContent();
    		else if(nextNode.getNodeName().equals("value"))
    			value = Float.parseFloat(nextNode.getTextContent());
    		else if(nextNode.getNodeName().equals("frequency"))
    			frequency = Integer.parseInt(nextNode.getTextContent());
    		else if(nextNode.getNodeName().equals("contacts")) {
    			NodeList contactList = nextNode.getChildNodes();
    			for(int index = 0; index < contactList.getLength(); index++)
    				contacts.add(new Contact(contactList.item(index)));
    		}
    		else {
    			Logger.logWarning("Error parsing warning: invalid tag");
    		}
    	}
	}
	
	/* Determines whether or not this warning should fire based on the passed in readings.
	It returns the first reading that triggers a firing, or null if none is found */
	public boolean shouldFire(Reading reading) {
		readings[index] = reading;
		index = (index+1)%20;
		
		long time = Calendar.getInstance().getTimeInMillis();
		if((time - lastFired) < (frequency*1000)) {
			System.out.println("not enough time has passed!");
			return false;
		}
		
		if(type.equals("above") && reading.getValue() > value) {
			lastFired = time;
			return true;
		}
		else if(type.equals("below") && reading.getValue() < value) {
			lastFired = time;
			return true;
		}
		else if(type.equals("trend") && (shouldTrendUpFire() || shouldTrendDownFire())) {
			lastFired = time;
			return true;
		}
		
		return false;
	}
	
	private boolean shouldTrendDownFire() {
		int count = 1; // the number of consecutive drops found
		int iterations = 0; //number of readings checked
		int i = index-1; //index into readings of last recorded value
		if(i < 0)
			i += 20;
		if(readings[i] == null)
			return false;
		
		float lastValue = readings[i].getValue(); //last value checked
		
		while(count < value) {
			if(--i < 0)
				i += 20;
			iterations++;
			
			if(readings[i] == null)
				return false;
			else if(readings[i].getValue() > lastValue) { //trend continues!
				lastValue = readings[i].getValue();
				count++;
			}
			else if(readings[i].getValue() == lastValue) //disregard equal readings
				continue;
			else
				return false; //trend broken
			
			if(iterations == 20) //we have gone through the entire array and are now looping
				return false;
		}
		
		return true; //It made it through the while loop, it should fire!
	}
	
	private boolean shouldTrendUpFire() {
		int count = 1; // the number of consecutive drops found
		int iterations = 0; //number of readings checked
		int i = index-1; //index into readings of last recorded value
		if(i < 0)
			i += 20;

		if(readings[i] == null)
			return false;
		
		float lastValue = readings[i].getValue(); //last value checked
		
		while(count < value) {
			if(--i < 0)
				i += 20;
			iterations++;
			
			if(readings[i] == null)
				return false;
				
			else if(readings[i].getValue() < lastValue) { //trend continues!
				lastValue = readings[i].getValue();
				count++;
			}
			else if(readings[i].getValue() == lastValue) //disregard equal readings
				continue;
			else
				return false; //trend broken
			
			if(iterations == 20) //we have gone through the entire array and are now looping
				return false;
		}
		
		return true; //It made it through the while loop, it should fire!
	}

	public String getType() {
		return type;
	}

	public float getValue() {
		return value;
	}

	public int getFrequency() {
		return frequency;
	}

	public Vector<Contact> getContacts() {
		return contacts;
	}
	
	public Reading[] getReadings() {
		return readings;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		String text = new String();
		if(type != null)
			text+= "	type: " + type + '\n';
		if(value != 0)
			text+= "	value: " + value + '\n';
		if(frequency != 0)
			text+= "	frequency: " + frequency;
		
		for(int i = 0; i < contacts.size(); i++)
			text+= "\n	contact:\n" + contacts.get(i);
		
		return text + '\n';
	}
	
	public static void main(String[] args) {
		System.out.println("Value is: " + -1%20);
	}
}