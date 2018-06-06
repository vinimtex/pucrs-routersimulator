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
			addRowToRouterTable(ip, 1, ip);
		}
	}
	
	public static void addRowToRouterTable(String destinationIp, int metric, String gatewayIp) {
		
		if(!destinationIp.equals(router.getIp())) {
			if(router.getRouterTable().getRows().size() > 0) {
								
					RouterTableRow row = ThereIsOnlist(destinationIp);
					if(row == null) {
						if(destinationIp.equals(gatewayIp)) {
							router.getRouterTable().addRow(new RouterTableRow(destinationIp, metric, gatewayIp));
						} else {
							router.getRouterTable().addRow(new RouterTableRow(destinationIp, metric+1, gatewayIp));
						}
					} else {						
						
						if(!gatewayIp.equals(row.getGatewayIp())) {
							if(metric < row.getMetric()) {
								row.setGatewayIp(gatewayIp);
								row.setMetric(metric + 1);
							}
						}
						
					}	
			
			} else {
				router.getRouterTable().addRow(new RouterTableRow(destinationIp, metric, gatewayIp));
			}
			
		}
		
		System.out.println("============ TABELA DE ROTEAMENTO ===========");
		System.out.println("IP               METRICA     SAIDA           ");
		for(RouterTableRow row: router.getRouterTable().getRows()) {
			System.out.println(row.getDestinationIp() + "  " + row.getMetric() + "           " + row.getGatewayIp());
			
		}
		System.out.println("============ FIM TABELA DE ROTEAMENTO ======");
		

	}
	
	public static RouterTableRow ThereIsOnlist(String destinationIp) {
		
		RouterTableRow row;
		Iterator<RouterTableRow> iterator = router.getRouterTable().getRows().iterator();
		
		if(router.getRouterTable().getRows().size() > 0) {
		
			while( iterator.hasNext() ) {
				row = iterator.next();								
				if(destinationIp.equals(row.getDestinationIp())){						
						return row;
				}
			}
		}		
		return null;
	}
	
	public static String routerTableStringfy() {
		String routerTable = "";
		
		if(router.getRouterTable().getRows().size() > 0) {
			for(RouterTableRow row : router.getRouterTable().getRows()) {
				routerTable += "*"; // inicio de uma linha na tabela
				routerTable += row.getDestinationIp();
				routerTable += ";"; // separador do ip/mÃ©trica 
				routerTable += row.getMetric();
			}
		}
		
		return routerTable;
	}
	
	public static void sendRouterTableNow() {

		try {
			wakeUpSenderThread();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void wakeUpSenderThread() throws InterruptedException {
		senderThread.interrupt();
	}
	
	public static void messageReceived(String msg, String source) {
		/*
		 * @TODO
		 * Quando receber um pacote de determinado IP atualizar o updatedAt deste ip na tabela de roteamento
		 * para não ser removido dentro de 30 segundos
		 */
		boolean flag = false;
		for(String ip : MainApp.ipList) {
				if(ip.equals(source)) {
					flag = true;
					break;
				}
		}
		if(!flag) {
			System.out.println("Adicionou o IP:"+source);
			MainApp.ipList.add(source);
			addRowToRouterTable(source, 1, source);
		}
		
		msg = sanatizeMessage(msg);
		if(validateMessage(msg)) {
			
			if(msg.length() == 1) {
				sendRouterTableNow();
			} else {
				String[] tuples = msg.substring(1).split("\\*");
				
				for(String tuple : tuples) {
					
					String[] row = tuple.split(";");
					
					String destinationIp = row[0];
					int metric = Integer.parseInt(row[1]);
					
					addRowToRouterTable(destinationIp, metric, source);
					//sendRouterTableNow();
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
			System.out.println("MENSAGEM INVÁLIDA: Mensagem vazia");
			return false;
		}
			
					
		if(msg.length() == 1) {
			if(msg.charAt(0) != '!') {
				System.out.println("MENSAGEM INVÁLIDA: Mensagem de tamanho 1 inválida, não é um '!' (Anúncio de roteador) ");
				return false;
			}
		}
			
		
		if(msg.length() > 1) {
			if(msg.charAt(0) != '*') {
				System.out.println("MENSAGEM INVÁLIDA: Indicador de tupla '*' não encontrado na primeira posição.");
				return false;
			} else {				
				String[] tuples = msg.substring(1).split("\\*");
				
				for(String tuple : tuples) {
					
					String[] rowInfo = tuple.split(";");
					
					if(rowInfo.length != 2) {
						System.out.println("MENSAGEM INVÁLIDA: Separado de tupla, ip e métrica não encontrado ou inválido");
						return false;
					}
						
					
					String destinationIp = rowInfo[0];
					String metric = rowInfo[1];
					
					String[] ipParts = destinationIp.split("\\.");
					
					if(ipParts.length != 4) {
						System.out.println("TUPLA INVÁLIDA: IP inválido");
						return false;
					}
						
					
					for(String ip: ipParts) {
						if(Integer.parseInt(ip) < 0 || Integer.parseInt(ip) > 255) {
							System.out.println("TUPLA INVÁLIDA: IP fora da faixa 0-255");
							return false;
						}
							
					}
					
					if(Integer.parseInt(metric) <= 0) {
						System.out.println("TUPLA INVÁLIDA: Métrica 0 ou negativa");
						return false;
					}
						
					
				}
				
			}
		}
		System.out.println("MENSAGEM VÁLIDA:" + msg);
		
		return true;
	}
	
}
