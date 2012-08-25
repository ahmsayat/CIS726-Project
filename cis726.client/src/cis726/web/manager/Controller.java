package cis726.web.manager;

import java.util.ArrayList;
import java.util.Iterator;

import cis726.db.DbSupport;
import cis726.entities.DeviceReading;
import cis726.entities.RegisteredDevice;
import cis726.db.dbClass.PatientProfile;
/*
 * This class is the controller to get and set initial data in the client UI.
 * 
 * Author: Luis Carranco
 * Modified by Team GUI
 */
public class Controller {
	private int roomId; // Room to get data
	private ArrayList<RegisteredDevice> devicesConnected; // Devices in room
	ArrayList<RegisteredDevice> authorizedDevices; // Authorized Devices for
													// widgets
	private String contextDirectory; // filesystem web application directory.
										// (To create intial data.xml files for
										// widgets' charts)

	/*
	 * Controller constructor.
	 * 
	 * @roomId: room to get devices connected
	 * 
	 * @contextDirectory: file system folder where is running the web
	 * application.
	 */
	public Controller(int roomId, String contextDirectory) {
		this.roomId = roomId;
		// get all devices connected to the room
		devicesConnected = DbSupport.getDevicesByRoom(this.roomId);
		this.contextDirectory = contextDirectory;
	}

	/*
	 * Get The devices allowed to display in the page.
	 */
	public ArrayList<RegisteredDevice> getRoomDevices(String token) {
		authorizedDevices = new ArrayList<RegisteredDevice>();
		// Get devices authorized by token
		for (Iterator<RegisteredDevice> it = devicesConnected.iterator(); it
				.hasNext();) {
			RegisteredDevice device = it.next();
			// TODO: filter just Devices Authorized by token.. or use session
			// data
			authorizedDevices.add(device);
		}
		return authorizedDevices;
	}

	/*
	 * Initialize the widget data. For charts, it creates data.xml files based
	 * on last information in DB (Historical signal) TODO: If needed make other
	 * kind of initialization for widgets.
     *
     */
	public void InitWidgetData() {
		// Iterate all the room devices which are authorized by user.
		for (RegisteredDevice device : authorizedDevices) {
			if (device.getDeviceType().equalsIgnoreCase("Pulse")) {
				PulseManager.setDeviceInitialData(contextDirectory, device
						.getDeviceId());
			}
			if (device.getDeviceType().equalsIgnoreCase("Temperature")) {
				TemperatureManager.setDeviceInitialData(contextDirectory,
						device.getDeviceId());
			}
			if (device.getDeviceType().equalsIgnoreCase("Pressure")) {
				PressureManager.setDeviceInitialData(contextDirectory,
						device.getDeviceId());
			}
			if (device.getDeviceType().equalsIgnoreCase("Oxygen")) {
				OxygenManager.setDeviceInitialData(contextDirectory,
						device.getDeviceId());
			}		
			if (device.getDeviceType().equalsIgnoreCase("RespirationRate")) {
				RespirationManager.setDeviceInitialData(contextDirectory,
						device.getDeviceId());
			}
            if (device.getDeviceType().equalsIgnoreCase("InputOutput")) { // I'm assuming the same naming pattern
				IOManager.setDeviceInitialData(contextDirectory,
						device.getDeviceId());
			}
			// TODO: more devices data initialization
		}
	}
	
	/*
	 * Get the last signal stored in the DB given the type of the device
	 * (Look in all the current devices connected for this room) 
	 */
	public String getLastSignal(String deviceType){
		String signal="";
		for (RegisteredDevice device : authorizedDevices) {
			ArrayList<DeviceReading> readings = null;
			if (device.getDeviceType().equalsIgnoreCase(deviceType))
			{
				readings = DbSupport.getLastInputsByDevice(device.getDeviceId(), 1);
				if(readings.size() == 1)
					return readings.get(0).getValue();
			}
		}
		return signal;
	}
	
    /*
	 * Accessor method for room Id
     * Added by Team GUI
	 */
	public int getRoomId() {
    return this.roomId;
    }
   
    /*
	 * Get the Profile values
     */
	public PatientProfile getProfile() {
		return DbSupport.getPatientProfileFromRoomId(this.getRoomId());
    }

}
