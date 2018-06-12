package appRouterSimulator.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import appRouterSimulator.models.RouterTableModel;
import services.RouterService;

import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;

public class RouterTable extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public RouterTableModel rtModel;
	private JScrollPane scrollPane;
	private JLabel routerTableTitleLabel;
	/**
	 * Create the frame.
	 */
	public RouterTable() {
		rtModel = RouterTableModel.getInstance();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		table = new JTable(rtModel);
		scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		routerTableTitleLabel = new JLabel("Tabela de Roteamento - " + RouterService.router.getIp());
		routerTableTitleLabel.setFont(new Font("Open Sans", Font.PLAIN, 20));
		contentPane.add(routerTableTitleLabel, BorderLayout.NORTH);
	}

}
