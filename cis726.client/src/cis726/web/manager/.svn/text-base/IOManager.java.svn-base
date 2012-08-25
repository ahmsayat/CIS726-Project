package cis726.web.manager;

import java.io.*;
import java.util.ArrayList;

import cis726.db.DbSupport;
import cis726.entities.DeviceReading;

/*
 * This class manages the initialization of IO device
 */
public class IOManager {

	
    private static void writeHeader(BufferedWriter out) throws IOException {
        out.write("<chart>" +
                "<series>" +
                    "<value xid=\"Input\">Input</value>" +
                    "<value xid=\"Output\">Output</value>" +
                "</series>");
    }

    /*
	 * Create a .xml file to be read by widget chart.
	 */
	public static void setDeviceInitialData(String contextPath, int deviceId) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(contextPath
					+ "/config/ioDevice" + deviceId + ".xml"));
			// Get the last 10 values for pulse device.
			ArrayList<DeviceReading> readings = DbSupport
					.getLastInputsByDevice(deviceId, 1);

            String [] reading = readings.get(0).getValue().split(";");
            
            writeHeader(out);

            out.write("<graphs>");
                out.write("<graph gid=\"Solids\" title=\"Solids\">");
                    out.write("<value xid=\"Input\">" + reading[0] + "</value>");
                    out.write("<value xid=\"Output\">" + reading[3] + "</value>");
                out.write("</graph>");
                out.write("<graph gid=\"Liquids\" title=\"Liquids\">");
                    out.write("<value xid=\"Input\">" + reading[1] + "</value>");
                    out.write("<value xid=\"Output\">" + reading[4] + "</value>");
                out.write("</graph>");
                out.write("<graph gid=\"Intravenous\" title=\"Intravenous\">");
                    out.write("<value xid=\"Input\">" + reading[2] + "</value>");
                    out.write("<value xid=\"Output\">" + reading[5] + "</value>");
                out.write("</graph>");
            out.write("</graphs>");
			out.write("</chart>");
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("There was a problem:" + e);
		}
	}
}
