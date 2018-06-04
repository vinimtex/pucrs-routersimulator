package threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import appRouterSimulator.MainApp;
import services.RouterService;

public class Sender extends Thread{
	
    private boolean running;
    
    private byte[] buf;
	
	
	public Sender() {
		
		if(RouterService.socket == null) {
			
			try {
				RouterService.socket = new DatagramSocket(5000);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			
		}
		
		this.sendMsg("!"); // envia aviso que o roteador entrou na rede
       
	}
	
	public void run() {
		this.running = true;
		System.out.println("Enviando na porta 5000");
		while(running) {
			
			if(MainApp.ipList.size() > 0) {
				for(String ip: MainApp.ipList) {
					try {
						InetAddress destinationIp = InetAddress.getByName(ip);
						
						String msg = "bombanderson";
						buf = msg.getBytes();
				        DatagramPacket packet = new DatagramPacket(buf, buf.length, destinationIp, 5000);
				        RouterService.socket.send(packet);
				        System.out.println("Pacote enviado para o IP: " + ip);
				        
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("Lista de IP Vazia");
				this.running = false;
			}
			
			
			
		}
	}
	
	public void sendMsg(String msg) {
		for(String ip: MainApp.ipList) {
			
			try {
				InetAddress destinationIp = InetAddress.getByName(ip);
				
				buf = msg.getBytes();
		        DatagramPacket packet = new DatagramPacket(buf, buf.length, destinationIp, 5000);
		        RouterService.socket.send(packet);
		        packet = new DatagramPacket(buf, buf.length);
		        RouterService.socket.receive(packet);
		        
		        System.out.println("Mensagem: " + msg + "  - Foi enviada para o IP: " + ip);
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			
		}
		
	}
}
