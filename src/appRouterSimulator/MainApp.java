package appRouterSimulator;

import java.util.*;

import services.RouterService;

public class MainApp {
	
	public static List<String> ipList = new ArrayList<>();
	
	public static void main(String[] args) {
		ipList.add("localhost");
		
		RouterService.startRouter("MEU_IP");
		
		
	}

}
