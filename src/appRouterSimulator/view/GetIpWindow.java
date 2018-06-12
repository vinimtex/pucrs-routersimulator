package appRouterSimulator.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import services.RouterService;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class GetIpWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnSetRouterIp;

	public GetIpWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(4, 2, 2, 4));
		setContentPane(contentPane);
		
		JLabel lblDigiteSeuIp = new JLabel("Digite seu IP Abaixo");
		contentPane.add(lblDigiteSeuIp);
		
		textField = new JTextField();
		textField.setToolTipText("Digite seu IP");
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnSetRouterIp = new JButton("Rodar RouterSimulator");
		btnSetRouterIp.addActionListener(new btnSetRouterIpAction());
		contentPane.add(btnSetRouterIp);
	}
	
	private class btnSetRouterIpAction implements ActionListener {

	        public void actionPerformed(ActionEvent e) {
	        	RouterService.startRouter(textField.getText());
	            setVisible(false);
	            RouterTable frame = new RouterTable();
	            frame.setTitle("RouterSimulator - Tabela de Roteamento");
				frame.setVisible(true);
	        }
	}

}
