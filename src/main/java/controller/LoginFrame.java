package controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Users;
import service.impl.ManagerServiceImpl;
import service.impl.UsersServiceImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		new ManagerServiceImpl();
		setTitle("登入 - 公寓大廈管理系統");
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
		panel.setBounds(533, 10, 541, 661);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("帳號 : ");
		lblNewLabel_1.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		lblNewLabel_1.setBounds(57, 246, 120, 30);
		panel.add(lblNewLabel_1);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		usernameField.setBounds(187, 246, 200, 30);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("密碼 : ");
		lblNewLabel_1_1.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(57, 286, 120, 30);
		panel.add(lblNewLabel_1_1);
		
		
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		passwordField.setBounds(187, 286, 200, 30);
		panel.add(passwordField);
		
		
		JButton btnLogin = new JButton("登入");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				
				Users users = new UsersServiceImpl().getUserByUsernameAndPassword(username, password);
				
				if (users != null) {
					JOptionPane.showMessageDialog(null, "歡迎 " + users.getUsername());
					new MainFrame(users).setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "無效的帳號或密碼！", "錯誤", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLogin.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		btnLogin.setBounds(233, 427, 100, 40);
		panel.add(btnLogin);
		
		JButton btnSignin = new JButton("註冊新帳號");
		btnSignin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SignupFrame().setVisible(true);
				dispose();
			}
		});
		btnSignin.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		btnSignin.setBounds(187, 536, 200, 40);
		panel.add(btnSignin);

	}
}
