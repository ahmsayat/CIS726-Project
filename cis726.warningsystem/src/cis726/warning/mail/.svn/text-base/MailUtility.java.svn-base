package cis726.warning.mail;

import java.security.Security;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cis726.logging.Logger;
import cis726.properties.PropertiesManager;

/*
 * MailUtility can be utilized to send e-mail messages through GMail. It uses the "mail.properties" file for
 * initializing javax.mail appropriately. The properties are managed through the PropertyManager class. 
 * These methods will be used for sending notifications to contacts, nurses, and doctors for the warning system,
 * and anything else we would like to use it for.
 * 
 * e-mail accounts: cis726.warningsystem@gmail.com, cis726.nurse@gmail.com
 * password: bluescreen
 * 
 * Author: Kenton Born
 */
public class MailUtility {
	
	//create a static properties object since it never changes and there is no reason to load it every time.
	private static Properties mailProperties;
	
	static {
		mailProperties = PropertiesManager.loadProperties("mail.properties");
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	}
	
	//Retrieves e-mail from the gmail account of the specified username.
	//It returns a Vector of MailMessage objects, or null if it failed. 
	public static Vector<MailMessage> getMail(String username, String password) {
		Vector<MailMessage> messages = new Vector<MailMessage>();
		
		if (mailProperties == null) {
			Logger.logWarning("Could not load mail properties file - Aborting!");
			return null;
		}   
          
		Session session = Session.getInstance(mailProperties, null);
        try {
        	// -- Get hold of a POP3 message store, and connect to it --
	        Store store = session.getStore("pop3");
			store.connect(mailProperties.getProperty("pophost"), username, password);
	
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			
			Message message[] = folder.getMessages();
	
			for (int i=0, n=message.length; i<n; i++) {
				String fromAddress = message[i].getFrom()[0].toString();
				MailMessage newMessage = new MailMessage(fromAddress, message[i].getSentDate(), message[i].getSubject(), (String)message[i].getContent());
				messages.add(newMessage);
			}
			//Close connection 
			folder.close(false);
			store.close();
        }
        catch(Exception e) {
        	Logger.logWarning("Could not retrieve e-mail: " + e.getMessage());
        	return null;
        }
        
        return messages;
	}
	
	//sends an e-mail from one person to another through the specified gmail account.
	//It returns true if the e-mail succeeded (false, otherwise).
	public static boolean sendMail(String fromAddress, String toAddress, final String username, final String password, String subject, String message)
	{
		if (mailProperties == null) {
			Logger.logWarning("Could not retrieve mail properties file - Aborting!");
			return false;
		}
	        
	    Session session = Session.getInstance(mailProperties,
	    		new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });
	    
	    try {
	    	//set who the message is from
		    InternetAddress addressFrom = new InternetAddress(fromAddress);
		    Message msg = new MimeMessage(session);
		    msg.setFrom(addressFrom);
		 
		    //set who the message is to
		    InternetAddress[] addressTo = new InternetAddress[1];
		    final String[] sendTo = { toAddress };
		    addressTo[0] = new InternetAddress(sendTo[0]);
		    msg.setRecipients(Message.RecipientType.TO, addressTo);
		 
		    //Setting the Subject and Content Type  
		    msg.setSubject(subject);
		    msg.setContent(message, "text/plain");
		    Transport.send(msg);
	    }
	    catch(Exception e) {
	    	Logger.logWarning("Could not send e-mail: " + e.getMessage());
	    	return false;
	    }
	    
	    //the email succeeded!
	    return true;
	}
}
