package cis726.warning.xml;


import java.io.File;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import cis726.logging.Logger;
import cis726.properties.PropertiesManager;
import cis726.warning.manager.Reading;

/*
 * This class will be used for parsing warnings sent to the activemq channel for warning registration.
 * parseWarnings should be called with the text from the activeMQ message, and should follow this format:
 * 
   <device>
	<id>Integer</id>
	<owner></owner>
	<warnings>
		<warning>
			<type> above | below | ? </type>
			<value> Float </value>
			<frequency> Integer (seconds) </frequency>
			contacts>
				<contact>
					<name> Name </name>
					<type> doctor | nurse | gui | other </type>
					<channel>Channel</channel>
					<mail>Email</mail>
					<phone>Phone</phone>
				</contact>
				...
			</contacts>
		</warning>
		...
	</warnings>
  </device>
   
 * Author: Kenton Born
 */
public class WarningsParser {
	
	//private DOMParser parser;
	
	public WarningsParser() {
		//parser = new DOMParser();
	}
	
	//parses the xml, building a Device object
	public Device parseDevice(String xml) {
		DOMParser parser;
		try {
			parser = new DOMParser();
			parser.parse(new InputSource(new java.io.StringReader(xml)));
		} catch (Exception e) {
			Logger.logWarning("Error parsing xml: " + e.getMessage());
			//cannot return a valid Device object
			return null;
		}
		
        Document doc = parser.getDocument();
        //build a new Device object from the xml
        return new Device(doc.getFirstChild()); 	
	}
	
	public Reading parseReading(String xml) {
		DOMParser parser;
		try {
			parser = new DOMParser();
			parser.parse(new InputSource(new java.io.StringReader(xml)));
		} catch (Exception e) {
			Logger.logWarning("Error parsing xml: " + e.getMessage());
			//cannot return a valid Reading object
			return null;
		}
		
        Document doc = parser.getDocument();
        //build a new Reading object from the xml
        return new Reading(doc.getFirstChild()); 
	}
	
	//validates the xml against the register schema
	public boolean isValidRegister(String xml) {

        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        try {
        	//grab the file containing the warning schema to validate against
	        File schemaLocation = new File(PropertiesManager.getProperty("registerwarningschema"));
	        Schema schema = factory.newSchema(schemaLocation);
	    
	        //Get a validator from the schema.
	        Validator validator = schema.newValidator();
	        
	        //Validate it!
	        Source source = new StreamSource(new StringReader(xml));
	        validator.validate(source);
	    }
        catch (Exception e) { //The xml was not valid
        	Logger.logWarning("Error validating xml while registering new device: " + e.getMessage());
        	return false;
        } 
        //the xml passed the validator!
        return true;
	}
	
	//validates the xml against the unregister schema
	public boolean isValidUnRegister(String xml) {

        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        try {
        	//grab the file containing the warning schema to validate against
	        File schemaLocation = new File(PropertiesManager.getProperty("unregisterwarningschema"));
	        Schema schema = factory.newSchema(schemaLocation);
	    
	        //Get a validator from the schema.
	        Validator validator = schema.newValidator();
	        
	        //Validate it!
	        Source source = new StreamSource(new StringReader(xml));
	        validator.validate(source);
	    }
        catch (Exception e) { //The xml was not valid
        	Logger.logWarning("Error validating xml while unregistering device: " + e.getMessage());
        	return false;
        } 
        //the xml passed the validator!
        return true;
	}
	
	//validates the xml against the unregister schema
	public boolean isValidWarning(String xml) {

        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        try {
        	//grab the file containing the warning schema to validate against
	        File schemaLocation = new File(PropertiesManager.getProperty("warningschema"));
	        Schema schema = factory.newSchema(schemaLocation);
	    
	        //Get a validator from the schema.
	        Validator validator = schema.newValidator();
	        
	        //Validate it!
	        Source source = new StreamSource(new StringReader(xml));
	        validator.validate(source);
	    }
        catch (Exception e) { //The xml was not valid
        	Logger.logWarning("Error validating xml of warning: " + e.getMessage());
        	return false;
        } 
        //the xml passed the validator!
        return true;
	}
	
	//validates the xml against thre reading schema
	public boolean isValidReading(String xml) {
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        try {
        	//grab the file containing the warning schema to validate against
	        File schemaLocation = new File(PropertiesManager.getProperty("readingschema"));
	        Schema schema = factory.newSchema(schemaLocation);
	    
	        //Get a validator from the schema.
	        Validator validator = schema.newValidator();
	        
	        //Validate it!
	        Source source = new StreamSource(new StringReader(xml));
	        validator.validate(source);
	    }
        catch (Exception e) { //The xml was not valid
        	//Commented by Luis. We don't want this message to avoid confusions.
        	//Logger.logWarning("Error validating xml for latest reading: " + e.getMessage());
        	return false;
        } 
        //the xml passed the validator!
        return true;
	}
	
	//this method should be used for obtaining the current time for warning messages
	public static String getDateString() {
    	Calendar c = Calendar.getInstance();
    	String str_date= c.get(Calendar.MONTH)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.YEAR)+" "+c.get (Calendar.HOUR_OF_DAY) + ":" + c.get (Calendar.MINUTE) + ":" + c.get (Calendar.SECOND);
    	return str_date;
    }
	
	/*this method turns a string into a Date object (returning null if it is not properly formatted).
	The strings should be formatted as follows: "mm-dd-yyyy hh:mm:ss" */
	public static Date parseDate(String dateString) {
    	DateFormat formatter; 
    	Date date = null;
        formatter = new SimpleDateFormat("mm-dd-yyyy hh:mm:ss");
        try {
        	date = (Date)formatter.parse(dateString);
        } catch (ParseException e) {
        	Logger.logWarning("Error building date from string: " + e.getMessage());
        	return null;
        }    
        return date;
    }
	
	public static Date getDate() {
		return Calendar.getInstance().getTime();
	}
}