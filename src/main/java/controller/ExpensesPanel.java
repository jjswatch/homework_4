package controller;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Users;
import service.impl.ExpenseServiceImpl;
import service.impl.ResidentServiceImpl;
import util.Tools;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ExpensesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","類別","總額","日期","描述"}, 0);
    private final JTable table = new JTable(model);
    private ResidentServiceImpl residentService = new ResidentServiceImpl();
    private ExpenseServiceImpl expenseService = new ExpenseServiceImpl();

    public ExpensesPanel(Users currentUser) {
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton add = new JButton("新增");
        add.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        add.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        add.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Tools.addExpense(residentService, expenseService);
        		Tools.reloadExpensesTable(table);
        	}
        });
        JButton edit = new JButton("編輯");
        edit.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        edit.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Tools.editExpense(table, residentService, expenseService);
        		Tools.reloadExpensesTable(table);
        	}
        });
        JButton del = new JButton("刪除");
        del.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        del.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Tools.deleteExpense(table);
        		Tools.reloadExpensesTable(table);
        	}
        });
        
        // 依角色控制按鈕顯示
        if ("Admin".equals(currentUser.getRole())) {
        	actions.add(add); 
            actions.add(edit); 
            actions.add(del);
        }
        
        add(actions, BorderLayout.SOUTH);

        Tools.reloadExpensesTable(table);
    }
}
