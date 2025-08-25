package controller;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Users;
import service.impl.PaymentServiceImpl;
import service.impl.ResidentServiceImpl;
import util.Tools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PaymentsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","住戶","總額","日期","項目","描述"}, 0);
    private final JTable table = new JTable(model);
    
    private ResidentServiceImpl residentService = new ResidentServiceImpl();
	private PaymentServiceImpl paymentService = new PaymentServiceImpl();
    
    public PaymentsPanel(Users currentUser) {
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actions = new JPanel();
        
        JButton add = new JButton("新增");
        add.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        add.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Tools.addPayment(residentService, paymentService);
        		Tools.reloadPyamentsTable(table);
        	}
        });
        
        JButton edit = new JButton("編輯");
        edit.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        edit.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Tools.editPayment(table, residentService, paymentService);
        		Tools.reloadPyamentsTable(table);
        	}
        });
        
        JButton del = new JButton("刪除");
        del.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        del.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Tools.deletePayment(table);
        		Tools.reloadPyamentsTable(table);
        	}
        });
        
        // 依角色控制按鈕顯示
        if ("Admin".equals(currentUser.getRole())) {
        	actions.add(add); 
            actions.add(edit); 
            actions.add(del);
        }

        add(actions, BorderLayout.SOUTH);

        Tools.reloadPyamentsTable(table);
    }
}
