package utilPackage;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Machine {
	
	InetAddress machineAddress;
	String      machineName;
	
	public Machine() {
		super();
		try {
			machineAddress = java.net.InetAddress.getLocalHost();
			java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
			machineName = localMachine.getHostName();
			System.out.println("Hostname of local machine: " + machineName);
 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			machineAddress = null;
			e.printStackTrace();
		}
	}



	public synchronized InetAddress getMachineAddress() {
		return machineAddress;
	}



	public synchronized void setMachineAddress(InetAddress machineAddress) {
		this.machineAddress = machineAddress;
	}



	public synchronized String getMachineName() {
		return machineName;
	}



	public synchronized void setMachineName(String machineName) {
		this.machineName = machineName;
	}


}
