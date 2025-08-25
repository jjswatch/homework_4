package controller;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.Users;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Users currentUser;
	
	public MainFrame(Users users) {
		this.currentUser = users;

		// Top bar with title + clock
    	JPanel topBar = new JPanel(new BorderLayout());

    	// Clock
    	JLabel clockLabel = new JLabel();
    	clockLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    	topBar.add(clockLabel, BorderLayout.EAST);

    	// Timer to update every second
    	javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
    	    clockLabel.setText(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
    	});
    	timer.start();

    	add(topBar, BorderLayout.NORTH);

		setTitle("公寓大廈管理系統 - 登入人員 " + users.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		setLocationRelativeTo(null);
		
		JTabbedPane tabs = new JTabbedPane();
		
		// 只限Admin登入管理
		if ("admin".equalsIgnoreCase(users.getRole())) {
			tabs.addTab("管理員", new ManagersPanel());
			tabs.addTab("單元表", new UnitPanel());
			tabs.addTab("住戶表", new ResidentPanel());
		}
		
		// 所有使用者皆可查看
		tabs.addTab("收入表", new PaymentsPanel(currentUser));
		tabs.addTab("支出表", new ExpensesPanel(currentUser));
		tabs.addTab("報告表", new ReportsPanel(currentUser));

		add(tabs);
	}

}
