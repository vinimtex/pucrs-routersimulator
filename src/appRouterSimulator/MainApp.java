package appRouterSimulator;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.*;

import appRouterSimulator.view.Console;
import appRouterSimulator.view.GetIpWindow;
import appRouterSimulator.view.IPList;
import appRouterSimulator.view.RouterTable;
import services.RouterService;

public class MainApp {
	
	public final static String PathIPList = "IPList.txt";
	
	public static List<String> ipList = new ArrayList<>();
	
	public static void main(String[] args) {
		
		
		
/*		Scanner sc = new Scanner(System.in);
		System.out.print("Digite seu IP:");
		String ip = sc.nextLine();
		
		RouterService.startRouter(ip);*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Console consoleView = new Console();
					consoleView.setTitle("RouterSimulator - Console");
					consoleView.setVisible(true);
					ReadIPList();
					
					if(ipList.isEmpty()) {
						IPList ipListView = new IPList();
						ipListView.setTitle("RouterSimulator - Digite IP dos Roteadores Vizinhos");
						ipListView.setVisible(true);
					} else {
						GetIpWindow frame = new GetIpWindow();
						frame.setTitle("RouterSimulator - Digite o seu IP");
						frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
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
