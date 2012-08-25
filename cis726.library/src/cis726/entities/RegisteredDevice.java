package cis726.entities;

/*
 * This class is an entity that represent RegiteredDevice table
 * 
 * Author: Luis Carranco
 */
public class RegisteredDevice {

	private int deviceId;
	private int roomId;
	private String deviceType;
	private String channel;

	public RegisteredDevice(int deviceId, int roomId, String deviceType,
			String channel) {
		this.deviceId = deviceId;
		this.roomId = roomId;
		this.deviceType = deviceType;
		this.channel = channel;
	}

	public int getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getRoomId() {
		return this.roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
