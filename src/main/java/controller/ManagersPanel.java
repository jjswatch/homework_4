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

public class ManagersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel model;
    
    public ManagersPanel() {
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(new Object[]{"ID", "帳號", "全名", "職務"}, 0);
        table = new JTable(model);
        Tools.reloadManagersTable(table);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton add = new JButton("新增");
        add.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        add.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Tools.addManager();
        		Tools.reloadManagersTable(table);
        	}
        });
        
        JButton edit = new JButton("編輯");
        edit.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        edit.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Tools.editManager(table);
        		Tools.reloadManagersTable(table);
        	}
        });
        
        JButton del = new JButton("刪除");
        del.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        del.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Tools.deleteManager(table);
        		Tools.reloadManagersTable(table);
        	}
        });

        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(del);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
