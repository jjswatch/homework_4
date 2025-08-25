package controller;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import service.impl.ResidentServiceImpl;
import service.impl.UnitServiceImpl;
import util.Tools;

public class ResidentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final DefaultTableModel model = new DefaultTableModel(new Object[] {"ID", "姓名", "電話", "Email", "單元"}, 0);
	private final JTable table = new JTable(model);
	
	private ResidentServiceImpl residentService = new ResidentServiceImpl();
	private UnitServiceImpl unitService = new UnitServiceImpl();
	
	public ResidentPanel() {
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel actions = new JPanel();
		
		JButton add = new JButton("新增");
		add.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Tools.addResident();
				Tools.reloadResidentsTable(table);
			}
		});
		
		JButton edit = new JButton("編輯");
		edit.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Tools.editResident(table, residentService, unitService);
				Tools.reloadResidentsTable(table);
			}
		});
		
		JButton del = new JButton("刪除");
		del.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		del.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Tools.deleteResident(table);
				Tools.reloadResidentsTable(table);
			}
		});
		
		actions.add(add);
		actions.add(edit);
		actions.add(del);
		add(actions, BorderLayout.SOUTH);
		
		Tools.reloadResidentsTable(table);
	}
}
