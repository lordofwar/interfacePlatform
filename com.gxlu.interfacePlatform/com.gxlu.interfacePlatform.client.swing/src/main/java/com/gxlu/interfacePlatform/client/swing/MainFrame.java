package com.gxlu.interfacePlatform.client.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import java.awt.Font;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnStart;
	private JButton btnStop;
	private JButton btnRefresh;
	private JButton btnDelete;
	private JButton btnAdd;
	private JButton btnSave;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 801, 603);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setRowHeight(24);
		table.setFont(new Font("ËÎÌו", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"ID", "Name", "Code", "MetaCategory", "StartTime", "Interval", "Comment", "Status", "isActive"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(111);
		table.getColumnModel().getColumn(2).setPreferredWidth(175);
		table.getColumnModel().getColumn(3).setPreferredWidth(534);
		table.getColumnModel().getColumn(4).setPreferredWidth(118);
		table.getColumnModel().getColumn(5).setPreferredWidth(65);
		table.getColumnModel().getColumn(6).setPreferredWidth(227);
		table.getColumnModel().getColumn(8).setPreferredWidth(71);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		btnStart = new JButton("Start");
		btnStart.setIcon(new ImageIcon(MainFrame.class.getResource("/icon/Start.png")));
		panel.add(btnStart);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		
		btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		btnStop.setIcon(new ImageIcon(MainFrame.class.getResource("/icon/stop.png")));
		panel.add(btnStop);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel.add(horizontalGlue_1);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(MainFrame.class.getResource("/icon/Add.gif")));
		toolBar.add(btnAdd);
		
		btnDelete = new JButton("Remove");
		btnDelete.setIcon(new ImageIcon(MainFrame.class.getResource("/icon/Delete.gif")));
		toolBar.add(btnDelete);
		
		btnSave = new JButton("Save");
		btnSave.setIcon(new ImageIcon(MainFrame.class.getResource("/icon/Save.gif")));
		toolBar.add(btnSave);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.setIcon(new ImageIcon(MainFrame.class.getResource("/icon/refresh.png")));
		toolBar.add(btnRefresh);
	}

	public JButton getBtnStart() {
		return btnStart;
	}
	public JButton getBtnStop() {
		return btnStop;
	}
	public JButton getBtnRefresh() {
		return btnRefresh;
	}
	public JTable getTable() {
		return table;
	}
	public JButton getBtnDelete() {
		return btnDelete;
	}
	public JButton getBtnAdd() {
		return btnAdd;
	}
	public JButton getBtnSave() {
		return btnSave;
	}
}
