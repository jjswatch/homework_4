package controller;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.Tools;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UnitPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final DefaultTableModel model = new DefaultTableModel(new Object[] {"ID", "棟別", "樓層", "門號"}, 0);
	private final JTable table = new JTable(model);

	public UnitPanel() {
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel actions = new JPanel();
		
		JButton add = new JButton("新增");
		add.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Tools.addUnit();
				Tools.reloadUnitsTable(table);
			}
		});
		
		JButton edit = new JButton("編輯");
		edit.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Tools.editUnit(table);
				Tools.reloadUnitsTable(table);
			}
		});
		
		JButton del = new JButton("刪除");
		del.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		del.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Tools.deleteUnit(table);
				Tools.reloadUnitsTable(table);
			}
		});
		
		actions.add(add);
		actions.add(edit);
		actions.add(del);
		add(actions, BorderLayout.SOUTH);
		
		Tools.reloadUnitsTable(table);
	}
}
