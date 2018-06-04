package threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import services.RouterService;

public class Receiver extends Thread {
	
	private boolean running;
	private byte[] buf = new byte[256];
	
	public Receiver() {
		
		if(RouterService.socket == null) {
			try {
				RouterService.socket = new DatagramSocket(5000);
			} catch(SocketException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		this.running = true;
		System.out.println("Escutando na porta 5000 por pacotes");
		while(running) {
			try {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				RouterService.socket.receive(packet);
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				System.out.println("Pacote recebido do ip " + address.toString() + ":" + port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
