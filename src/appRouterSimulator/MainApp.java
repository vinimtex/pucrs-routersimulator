package appRouterSimulator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import services.RouterService;

public class MainApp {
	
	public static List<String> ipList = new ArrayList<>();
	
	public static void main(String[] args) {
		ipList.add("192.168.1.109");
		
		try {
			RouterService.startRouter(InetAddress.getLocalHost().getHostAddress());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}

}
