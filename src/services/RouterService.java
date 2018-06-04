package services;

import java.net.DatagramSocket;
import java.net.Socket;

import models.Router;
import models.RouterTable;
import models.RouterTableRow;
import threads.Receiver;
import threads.Sender;

public abstract class RouterService {
	
	public static Router router;
	public static Thread receiver;
	public static Thread sender;
	public static DatagramSocket socket;
	
	public static void startRouter(String ip) {
		
		router = new Router(ip);
			
		receiver = new Receiver();
		receiver.start();
		
		sender = new Sender();
		sender.start();
			
	}
	
	public static void addRowToRouterTable(String ipDestination, int metric, String ipGateway) {
		RouterTableRow row = new RouterTableRow(ipDestination, metric, ipGateway);
		
		router.getRouterTable().addRow(row);
	}
	
}
