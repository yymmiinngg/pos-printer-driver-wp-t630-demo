package yao.print.wpt630.demo;

import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;

public class ListBluToothDevice {

	// 列出所有蓝牙设备
	public static void main(String[] args) throws InterruptedException, IOException {

		LocalDevice localdev = null;
		DiscoverListener listener = new DiscoverListener();

		try {
			localdev = LocalDevice.getLocalDevice();
		} catch (BluetoothStateException e) {
			System.out.println("ERROR: cannot access local device");
			System.exit(1);
		}

		DiscoveryAgent agent = localdev.getDiscoveryAgent();
		try {
			agent.startInquiry(DiscoveryAgent.GIAC, listener);
		} catch (BluetoothStateException e) {
			System.out.println("Device unable to inquiry");
			System.exit(2);
		}

		while (!listener.isComplete())
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
	}

}
