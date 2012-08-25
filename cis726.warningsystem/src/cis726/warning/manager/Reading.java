package cis726.warning.manager;

import java.util.Date;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cis726.warning.xml.WarningsParser;

public class Reading {
	public int deviceID;
	public float value;
	public Date date;
	
	public Reading(int deviceID, float value) {
		this.deviceID = deviceID;
		this.value = value;
		this.date = date = WarningsParser.getDate();
	}
	
	public Reading(Node readingNode) {
		NodeList elements = readingNode.getChildNodes();
		for(int i = 0; i < elements.getLength(); i++) {
			Node nextNode = elements.item(i);
			if(nextNode.getNodeName().equals("id"))
				deviceID = Integer.parseInt(nextNode.getTextContent());
			else if(nextNode.getNodeName().equals("value"))
				value = Float.parseFloat(nextNode.getTextContent());
		}
		date = WarningsParser.getDate();
	}

	public int getDeviceID() {
		return deviceID;
	}

	public float getValue() {
		return value;
	}

	public Date getDate() {
		return date;
	}
}
