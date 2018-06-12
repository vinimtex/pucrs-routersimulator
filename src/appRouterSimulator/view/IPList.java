package appRouterSimulator.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import appRouterSimulator.MainApp;
import services.RouterService;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class IPList extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	private JButton btnSetIpList;

	public IPList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(4, 2, 2, 4));
		setContentPane(contentPane);
		
		JLabel lblDigiteIpVizinhos = new JLabel("O arquivo IPList.txt não foi encontrado no diretório ou está vazio, digite abaixo a lista dos ip vizinhos linha por linha");
		contentPane.add(lblDigiteIpVizinhos);
		
		textArea = new JTextArea();
		textArea.setToolTipText("Digite os IP dos vizinhos");
		textArea.setRows(50);
		contentPane.add(textArea);
		textArea.setColumns(20);
		
		btnSetIpList = new JButton("Definir IP dos Vizinhos");
		btnSetIpList.addActionListener(new btnSetIpListAction());
		contentPane.add(btnSetIpList);
	}
	
	private class btnSetIpListAction implements ActionListener {

	        public void actionPerformed(ActionEvent e) {
	        	String[] ipList = textArea.getText().split("\\s+");
	        	
	        	for(String ip: ipList) {
	        		MainApp.ipList.add(ip);
	        	}

	            setVisible(false);
	            GetIpWindow frame = new GetIpWindow();
	            frame.setTitle("RouterSimulator - Digite Seu IP");
				frame.setVisible(true);
	        }
	}

}
