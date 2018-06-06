package appRouterSimulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.*;

import services.RouterService;

public class MainApp {
	
	public final static String PathIPList = "C:\\Users\\João França\\Desktop\\pucrs-routersimulator\\src\\appRouterSimulator\\IPList.txt";
	
	public static List<String> ipList = new ArrayList<>();
	
	public static void main(String[] args) {
		
		ReadIPList();
		
		try {
			RouterService.startRouter(InetAddress.getLocalHost().getHostAddress());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}

	public static void ReadIPList() {
		
		try {

 		    FileReader fileReader = new FileReader(PathIPList);

		    try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
		      String IP;
		      while((IP = bufferedReader.readLine()) != null) {
		        ipList.add(IP);
		      };
		      
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
