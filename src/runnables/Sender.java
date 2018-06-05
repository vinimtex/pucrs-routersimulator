package runnables;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import appRouterSimulator.MainApp;
import services.RouterService;

public class Sender implements Runnable{
	
    private boolean running;
    
	public Sender() {
		
		if(RouterService.socket == null) {
			
			try {
				RouterService.socket = new DatagramSocket(5000);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			
		}
		this.running = true;
		this.sendMsg("!"); // envia aviso que o roteador entrou na rede
       
	}
	
	@Override
	public synchronized void run() {
		
		System.out.println("Enviando na porta 5000");
		while(running) {
			
			this.sendRouterTable();
			
			try {
				Thread.sleep(10000); // aguarda 10 segundos para enviar novamente
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
				
		}
	}
	
	public synchronized void sendMsg(String msg) {
		
		for(String ip: MainApp.ipList) {
			
			try {
				InetAddress destinationIp = InetAddress.getByName(ip);
				byte[] buf = msg.getBytes();
		        DatagramPacket packet = new DatagramPacket(buf, buf.length, destinationIp, 5000);
		        RouterService.socket.send(packet);
		        System.out.println("MENSAGEM ENVIADA:" + msg);
		        System.out.println("MENSAGEM ENVIADA DESTINO:" + ip);
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	public synchronized void sendRouterTable() {
		this.sendMsg(RouterService.routerTableStringfy());
	}
}
