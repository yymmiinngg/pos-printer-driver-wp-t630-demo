package yao.print.wpt630.demo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

public class DiscoverListener implements DiscoveryListener {

	private List<RemoteDevice> _remoteList = new LinkedList<RemoteDevice>();
	private List<RemoteDevice> remoteList = new LinkedList<RemoteDevice>();
	private boolean complete = false;

	public void deviceDiscovered(RemoteDevice rt, DeviceClass cod) {
		_remoteList.add(rt);
		System.out.println("Address: " + rt.getBluetoothAddress());
		try {
			System.out.println("   Name: " + rt.getFriendlyName(true));
		} catch (IOException e) {
		}
		System.out.println();
	}

	public void inquiryCompleted(int distype) {
		remoteList.clear();
		if (distype == INQUIRY_TERMINATED)
			System.out.println("Application Terminated");
		else if (distype == INQUIRY_COMPLETED) {
			remoteList.addAll(_remoteList);
			System.out.println("Inquiry Completed");
		} else {
			System.out.println("ERROR: Inquiry aborted");
		}
		_remoteList.clear();
		complete = true;
	}

	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		System.out.println("servicesDiscovered");
	}

	public void serviceSearchCompleted(int transID, int respCode) {
		System.out.println("serviceSearchCompleted");
	}

	public boolean isComplete() {
		return complete;
	}

	public List<RemoteDevice> getRemoteList() {
		return remoteList;
	}

}