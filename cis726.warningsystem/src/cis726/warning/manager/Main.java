package cis726.warning.manager;

/*
 * This is the main method for enabling the warning system. It will create new threads listening for all
 * the messages specified in the warning system interface documentation.
 */
public class Main {
	public static void main(String[] args) {
		WarningsManager manager = new WarningsManager("tcp://localhost:61616","registerDevice","unregisterDevice","devices");
		manager.start();
	}
}
