package cis726.web.manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cis726.db.DbSupport;
import cis726.entities.DeviceReading;

/*
 * This class manages the initialization of Oxygen device
 * 
 * Author: Luis Carranco
 */
public class OxygenManager {
	/*
	 * Create a .xml file to be read by widget chart.
	 */
	public static void setDeviceInitialData(String contextPath, int deviceId) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(contextPath
					+ "/config/oxygenDevice" + deviceId + ".xml"));
			// Get the last 100 values for oxygen device.
			ArrayList<DeviceReading> readings = DbSupport
					.getLastInputsByDevice(deviceId, 100);

			out.write("<chart>" + "<series>");
			for (int i = 0; i < readings.size(); i++) {
				out.write("<value xid='" + i + "'>" + i + "</value>");
			}
			out.write("</series>" + "<graphs>" + "<graph gid='1'>");
			for (int i = 0; i < readings.size(); i++) {
				out.write("<value xid='" + i + "'>"
						+ readings.get(i).getValue() + "</value>");
			}
			out.write("</graph>" + "</graphs>" + "</chart>");
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("There was a problem:" + e);
		}
	}
}
