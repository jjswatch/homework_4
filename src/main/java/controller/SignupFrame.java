package controller;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Unit;
import model.Users;
import service.impl.UnitServiceImpl;
import service.impl.UsersServiceImpl;

import javax.swing.ImageIcon;

public class SignupFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField fullnameField;
	private JComboBox<String> unitComboBox;

	public SignupFrame() {
		setTitle("註冊 - 公寓大廈管理系統");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Logo = new JLabel("");
		Logo.setBounds(0, 0, 542, 661);
		ImageIcon originalIcon = new ImageIcon(SignupFrame.class.getResource("/images/Logo.png"));
		Image scaledImage = originalIcon.getImage().getScaledInstance(Logo.getWidth(), Logo.getHeight(), Image.SCALE_SMOOTH);
		Logo.setIcon(new ImageIcon(scaledImage));
		contentPane.add(Logo);
		
		JPanel panel = new JPanel();
		panel.setBounds(543, 0, 541, 661);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("帳號 : ");
		lblNewLabel_1.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		lblNewLabel_1.setBounds(57, 133, 120, 30);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("密碼 : ");
		lblNewLabel_1_1.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(57, 173, 120, 30);
		panel.add(lblNewLabel_1_1);
			
		JLabel lblNewLabel_1_2 = new JLabel("全名 : ");
		lblNewLabel_1_2.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		lblNewLabel_1_2.setBounds(57, 213, 120, 30);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("單元 : ");
		lblNewLabel_1_2_1.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		lblNewLabel_1_2_1.setBounds(57, 253, 120, 30);
		panel.add(lblNewLabel_1_2_1);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		usernameField.setBounds(187, 133, 200, 30);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		passwordField.setBounds(187, 173, 200, 30);
		panel.add(passwordField);
		
		fullnameField = new JTextField();
		fullnameField.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		fullnameField.setColumns(10);
		fullnameField.setBounds(187, 213, 200, 30);
		panel.add(fullnameField);
		
		unitComboBox = new JComboBox<>();
		unitComboBox.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		unitComboBox.setBounds(187, 253, 200, 30);
		panel.add(unitComboBox);
		
		UnitServiceImpl unitService = new UnitServiceImpl();
		List<Unit> units = unitService.getAllUnit();
		
		unitComboBox.removeAllItems(); // 清空避免重複
		for (Unit unit : units) {
			unitComboBox.addItem(unit.getUnit());
		}
		
		
		JButton btnSignin = new JButton("註冊");
		btnSignin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				String fullname = fullnameField.getText();
				String unit = (String) unitComboBox.getSelectedItem();
				String role = "Resident";
				
				if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "請輸入完整資訊！", "錯誤", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
				
				// 取得 unitId
				UnitServiceImpl unitService = new UnitServiceImpl();
				int unitId = unitService.getAllUnit().stream()
		                .filter(u -> u.getUnit().equals(unit))
		                .findFirst()
		                .get().getId();
			
				UsersServiceImpl usersService = new UsersServiceImpl();
				
				// 先檢查住戶是否存在
				if (!usersService.getUserByNameAndUnit(fullname, unitId)) {
					JOptionPane.showMessageDialog(null, "住戶資料不存在，請確認姓名與門牌！", "錯誤", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// 再檢查帳號是否已存在
		        if (usersService.getUserByUsername(username)) {
		            JOptionPane.showMessageDialog(null, "帳號已被使用！", "錯誤", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        // 新增帳號
		        Users users = new Users();
				users.setUsername(username);
		        users.setPassword(password);
		        users.setFullname(fullname);
		        users.setUnitId(unitId);
		        users.setRole(role);
				
		        if (usersService.addUser(users)) {
		            JOptionPane.showMessageDialog(null, "註冊成功！", "訊息", JOptionPane.INFORMATION_MESSAGE);
		            new LoginFrame().setVisible(true);
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(null, "註冊失敗！", "錯誤", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnSignin.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		btnSignin.setBounds(233, 427, 100, 40);
		panel.add(btnSignin);
		
		JButton btnCancel = new JButton("取消");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new LoginFrame().setVisible(true);
				dispose();
			}
		});
		btnCancel.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		btnCancel.setBounds(233, 526, 100, 40);
		panel.add(btnCancel);

	}
}
