package runnables;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import services.RouterService;

public class Receiver implements Runnable {
	
	private boolean running;

	public Receiver() {
		
		if(RouterService.socket == null) {
			try {
				RouterService.socket = new DatagramSocket(5000);
			} catch(SocketException e) {
				e.printStackTrace();
			}
		}
		this.running = true;
	}
	
	public void run() {
		
		System.out.println("Escutando na porta 5000");
		while(running) {
			System.out.println("recebendo");
			try {
				byte[] buf = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				RouterService.socket.receive(packet);
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				System.out.println("Pacote recebido do ip " + address.getHostAddress() + ":" + port);
				
				String data = new String(packet.getData(), StandardCharsets.UTF_8);
				
				RouterService.messageReceived(data, address.getHostAddress());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
