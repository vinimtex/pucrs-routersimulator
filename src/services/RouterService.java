package services;

import java.net.DatagramSocket;
import java.util.Iterator;

import appRouterSimulator.MainApp;
import models.Router;
import models.RouterTableRow;
import runnables.Receiver;
import runnables.RemoveOutdatedRows;
import runnables.Sender;

public abstract class RouterService {
	
	public static Router router;
	public static Receiver receiver;
	public static Sender sender;
	public static DatagramSocket socket;
	public static Thread receiverThread;
	public static Thread senderThread;
	public static Thread removeOutdatedRowsThread;
	
	public static void startRouter(String ip) {
		
		router = new Router(ip);
		initialRouterTable();
		
		receiver = new Receiver();
		receiverThread = new Thread(receiver);
		receiverThread.start();
		
		sender = new Sender();	
		senderThread = new Thread(sender);
		senderThread.start();
		
		removeOutdatedRowsThread = new Thread(new RemoveOutdatedRows());
		removeOutdatedRowsThread.start();
	}
	
	public static void initialRouterTable() {
		for(String ip: MainApp.ipList) {
			addRowToRouterTable(ip, 1, router.getIp());
		}
	}
	
	public static void addRowToRouterTable(String destinationIp, int metric, String gatewayIp) {
		
		Iterator<RouterTableRow> iterator = router.getRouterTable().getRows().iterator();
		
		if(destinationIp != router.getIp()) {
			if(router.getRouterTable().getRows().size() > 0) {
				
				while( iterator.hasNext() ) {
					RouterTableRow row = iterator.next();
					
					if(destinationIp.equals(row.getDestinationIp())) {
						
						if(metric < row.getMetric()) {
							row.setGatewayIp(gatewayIp);
						}
						row.setMetric(row.getMetric() + 1);
						
					} else {
						
						router.getRouterTable().addRow(new RouterTableRow(destinationIp, metric, gatewayIp));
						
					}
				}
			
			} else {
				router.getRouterTable().addRow(new RouterTableRow(destinationIp, metric, gatewayIp));
			}
		}

	}
	
	public static String routerTableStringfy() {
		String routerTable = "";
		
		if(router.getRouterTable().getRows().size() > 0) {
			for(RouterTableRow row : router.getRouterTable().getRows()) {
				routerTable += "*"; // inicio de uma linha na tabela
				routerTable += row.getDestinationIp();
				routerTable += ";"; // separador do ip/m�trica 
				routerTable += row.getMetric();
			}
		}
		
		return routerTable;
	}
	
	public static void messageReceived(String msg, String source) {
		msg = sanatizeMessage(msg);
		if(validateMessage(msg)) {
			
			if(msg.length() == 1) {
				try {
					senderThread.notify();
				} catch(Exception e) {
					System.out.println("Thread n�o est� dormindo");
				}
				
			} else {
				String[] tuples = msg.substring(1).split("\\*");
				
				for(String tuple : tuples) {
					
					String[] row = tuple.split(";");
					
					String destinationIp = row[0];
					int metric = Integer.parseInt(row[1]);
					
					addRowToRouterTable(destinationIp, metric, source);
						
				}
			}
			
		} else {
			System.out.println("MENSAGEM DESCARTADA:" + msg);
		}
	}
	
	public static String sanatizeMessage(String msg) {
		msg = msg.replaceAll("\u0000.*", "");
		msg = msg.replaceAll("\\s+", "");
		return msg;
	}
	
	public static boolean validateMessage(String msg) {
		if(msg.length() <= 0 || msg == null) {
			System.out.println("MENSAGEM INV�LIDA: Mensagem vazia");
			return false;
		}
			
					
		if(msg.length() == 1) {
			if(msg.charAt(0) != '!') {
				System.out.println("MENSAGEM INV�LIDA: Mensagem de tamanho 1 inv�lida, n�o � um '!' (An�ncio de roteador) ");
				return false;
			}
		}
			
		
		if(msg.length() > 1) {
			if(msg.charAt(0) != '*') {
				System.out.println("MENSAGEM INV�LIDA: Indicador de tupla '*' n�o encontrado na primeira posi��o.");
				return false;
			} else {				
				String[] tuples = msg.substring(1).split("\\*");
				
				for(String tuple : tuples) {
					
					String[] rowInfo = tuple.split(";");
					
					if(rowInfo.length != 2) {
						System.out.println("MENSAGEM INV�LIDA: Separado de tupla, ip e m�trica n�o encontrado ou inv�lido");
						return false;
					}
						
					
					String destinationIp = rowInfo[0];
					String metric = rowInfo[1];
					
					String[] ipParts = destinationIp.split("\\.");
					
					if(ipParts.length != 4) {
						System.out.println("TUPLA INV�LIDA: IP Inv�lido");
						return false;
					}
						
					
					for(String ip: ipParts) {
						if(Integer.parseInt(ip) < 0 || Integer.parseInt(ip) > 255) {
							System.out.println("TUPLA INV�LIDA: IP Inv�lido fora da faixa 0-255");
							return false;
						}
							
					}
					
					if(Integer.parseInt(metric) <= 0) {
						System.out.println("TUPLA INV�LIDA: M�trica 0 ou negativa");
						return false;
					}
						
					
				}
				
			}
		}
		System.out.println("MENSAGEM V�LIDA:" + msg);
		
		return true;
	}
	
}
