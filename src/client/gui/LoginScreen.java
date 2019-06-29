package client.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;

@SuppressWarnings("serial")
public class LoginScreen extends JFrame {

	private JPanel contentPane;
	private JLayeredPane layeredPane;
	private CardLayout cardlayout = new CardLayout(0, 0);
	
	private JPanel panelLogIn;
		private JPanel panel1;
			private JLabel p1LoginLabel;
			private JSeparator p1Separator;
			private JLabel p1UsernameLabel;
			private JTextField p1UsernameInput;
			private JLabel p1PasswordLabel;
			private JPasswordField p1PasswordInput;
			private JButton p1LogInButton;
		private JLabel p1CreateNewAccountButton;
		private JLabel p1ForgotMyPasswordButton;
	
	private JPanel panelNewAccount;
		private JPanel panel2;
			private JLabel p2CreateNewAccountLabel;
			private JSeparator p2Separator;
			private JLabel p2UsernameLabel;
			private JTextField p2UsernameInput;
			private JLabel p2EmailLabel;
			private JTextField p2EmailInput;
			private JLabel p2PasswordLabel;
			private JPasswordField p2PasswordInput;
			private JLabel p2ConfirmPasswordLabel;
			private JPasswordField p2ConfirmPasswordInput;
			private JButton p2CreateAccountButton;
		private JLabel p2BackButton;
		
	private JPanel panelForgotPassword;
		private JPanel panel3;
			private JLabel p3ForgotMyPasswordLabel;
			private JSeparator p3Separator;
			private JLabel p3UsernameLabel;
			private JTextField p3UsernameInput;
			private JLabel p3EmailLabel;
			private JTextField p3EmailInput;
			private JButton p3SendMyPassword;
		private JLabel p3BackButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen();
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
	public LoginScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 784, 561);
		contentPane.add(layeredPane);
		layeredPane.setLayout(cardlayout);
		
		panelLogIn = new JPanel();
		layeredPane.add(panelLogIn, "panelLogIn");
		panelLogIn.setName("panelLogIn");
		panelLogIn.setLayout(null);
		
		panel1 = new JPanel();
		panel1.setBounds(192, 30, 400, 480);
		panel1.setBorder(new LineBorder(new Color(192, 192, 192)));
		panelLogIn.add(panel1);
		panel1.setLayout(null);
		
		p1LoginLabel = new JLabel("Log In");
		p1LoginLabel.setBounds(100, 20, 200, 40);
		panel1.add(p1LoginLabel);
		p1LoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		p1LoginLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
		
		p1Separator = new JSeparator();
		p1Separator.setForeground(new Color(192, 192, 192));
		p1Separator.setBackground(new Color(255, 255, 255));
		p1Separator.setBounds(10, 80, 380, 2);
		panel1.add(p1Separator);
		
		p1UsernameLabel = new JLabel("Username");
		p1UsernameLabel.setBounds(10, 100, 100, 30);
		panel1.add(p1UsernameLabel);
		p1UsernameLabel.setForeground(new Color(128, 128, 128));
		p1UsernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		p1UsernameInput = new JTextField();
		p1UsernameInput.setBounds(10, 130, 380, 30);
		panel1.add(p1UsernameInput);
		p1UsernameInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p1UsernameInput.setToolTipText("");
		p1UsernameInput.setColumns(10);
		
		p1PasswordLabel = new JLabel("Password");
		p1PasswordLabel.setBounds(10, 180, 100, 30);
		panel1.add(p1PasswordLabel);
		p1PasswordLabel.setForeground(new Color(128, 128, 128));
		p1PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		p1PasswordInput = new JPasswordField();
		p1PasswordInput.setBounds(10, 210, 380, 30);
		panel1.add(p1PasswordInput);
		p1PasswordInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		p1LogInButton = new JButton("Log In");
		p1LogInButton.setBounds(10, 418, 380, 50);
		panel1.add(p1LogInButton);
		p1LogInButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		p1CreateNewAccountButton = new JLabel("Create new account");
		p1CreateNewAccountButton.setBounds(192, 520, 150, 30);
		panelLogIn.add(p1CreateNewAccountButton);
		p1CreateNewAccountButton.setForeground(new Color(30, 144, 255));
		p1CreateNewAccountButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p1CreateNewAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p1CreateNewAccountButton.addMouseListener(new MouseAdapter() {
			 
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        System.out.println("p1CreateNewAccountButton mouse click");
		        switchPanels(panelNewAccount);
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	System.out.println("p1CreateNewAccountButton mouse enter");
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	System.out.println("p1CreateNewAccountButton mouse exit");
		    }
		});
		
		p1ForgotMyPasswordButton = new JLabel("Forgot my password");
		p1ForgotMyPasswordButton.setBounds(442, 520, 150, 30);
		panelLogIn.add(p1ForgotMyPasswordButton);
		p1ForgotMyPasswordButton.setHorizontalAlignment(SwingConstants.RIGHT);
		p1ForgotMyPasswordButton.setForeground(new Color(30, 144, 255));
		p1ForgotMyPasswordButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p1ForgotMyPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p1ForgotMyPasswordButton.addMouseListener(new MouseAdapter() {
			 
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        System.out.println("p1ForgotMyPasswordButton mouse click");
		        switchPanels(panelForgotPassword);
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	System.out.println("p1ForgotMyPasswordButton mouse enter");
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	System.out.println("p1ForgotMyPasswordButton mouse exit");
		    }
		});
		
		panelNewAccount = new JPanel();
		layeredPane.add(panelNewAccount, "panelNewAccount");
		panelNewAccount.setName("panelNewAccount");
		panelNewAccount.setLayout(null);
		
		panel2 = new JPanel();
		panel2.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel2.setBounds(192, 30, 400, 480);
		panelNewAccount.add(panel2);
		panel2.setLayout(null);
		
		p2CreateNewAccountLabel = new JLabel("Create new account");
		p2CreateNewAccountLabel.setBounds(15, 20, 370, 40);
		p2CreateNewAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		p2CreateNewAccountLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
		panel2.add(p2CreateNewAccountLabel);
		
		p2Separator = new JSeparator();
		p2Separator.setBounds(10, 80, 380, 2);
		p2Separator.setForeground(Color.LIGHT_GRAY);
		p2Separator.setBackground(Color.WHITE);
		panel2.add(p2Separator);
		
		p2UsernameLabel = new JLabel("Username");
		p2UsernameLabel.setBounds(10, 100, 100, 30);
		p2UsernameLabel.setForeground(Color.GRAY);
		p2UsernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel2.add(p2UsernameLabel);
		
		p2UsernameInput = new JTextField();
		p2UsernameInput.setBounds(10, 130, 380, 30);
		p2UsernameInput.setToolTipText("");
		p2UsernameInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2UsernameInput.setColumns(10);
		panel2.add(p2UsernameInput);
		
		p2EmailLabel = new JLabel("Email");
		p2EmailLabel.setForeground(Color.GRAY);
		p2EmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2EmailLabel.setBounds(10, 180, 100, 30);
		panel2.add(p2EmailLabel);
		
		p2EmailInput = new JTextField();
		p2EmailInput.setToolTipText("");
		p2EmailInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2EmailInput.setColumns(10);
		p2EmailInput.setBounds(10, 210, 380, 30);
		panel2.add(p2EmailInput);
		
		p2PasswordLabel = new JLabel("Password");
		p2PasswordLabel.setForeground(Color.GRAY);
		p2PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2PasswordLabel.setBounds(10, 260, 100, 30);
		panel2.add(p2PasswordLabel);
		
		p2PasswordInput = new JPasswordField();
		p2PasswordInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2PasswordInput.setBounds(10, 290, 380, 30);
		panel2.add(p2PasswordInput);
		
		p2ConfirmPasswordLabel = new JLabel("Confirm Password");
		p2ConfirmPasswordLabel.setForeground(Color.GRAY);
		p2ConfirmPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2ConfirmPasswordLabel.setBounds(10, 340, 150, 30);
		panel2.add(p2ConfirmPasswordLabel);
		
		p2ConfirmPasswordInput = new JPasswordField();
		p2ConfirmPasswordInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2ConfirmPasswordInput.setBounds(10, 370, 380, 30);
		panel2.add(p2ConfirmPasswordInput);
		
		p2CreateAccountButton = new JButton("Create Account");
		p2CreateAccountButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		p2CreateAccountButton.setBounds(10, 418, 380, 50);
		panel2.add(p2CreateAccountButton);
		
		p2BackButton = new JLabel("Back");
		p2BackButton.setBounds(192, 520, 150, 30);
		panelNewAccount.add(p2BackButton);
		p2BackButton.setForeground(new Color(30, 144, 255));
		p2BackButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2BackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p2BackButton.addMouseListener(new MouseAdapter() {
			 
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        System.out.println("p2BackButton mouse click");
		        switchPanels(panelLogIn);
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	System.out.println("p2BackButton mouse enter");
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	System.out.println("p2BackButton mouse exit");
		    }
		});
		
		panelForgotPassword = new JPanel();
		layeredPane.add(panelForgotPassword, "panelForgotPassword");
		panelForgotPassword.setName("panelForgotPassword");
		panelForgotPassword.setLayout(null);
		
		panel3 = new JPanel();
		panel3.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel3.setBounds(192, 30, 400, 480);
		panelForgotPassword.add(panel3);
		panel3.setLayout(null);
		
		p3ForgotMyPasswordLabel = new JLabel("Forgot My Password");
		p3ForgotMyPasswordLabel.setBounds(10, 20, 380, 40);
		p3ForgotMyPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		p3ForgotMyPasswordLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
		panel3.add(p3ForgotMyPasswordLabel);
		
		p3Separator = new JSeparator();
		p3Separator.setBounds(10, 80, 380, 2);
		p3Separator.setForeground(Color.LIGHT_GRAY);
		p3Separator.setBackground(Color.WHITE);
		panel3.add(p3Separator);
		
		p3UsernameLabel = new JLabel("Username");
		p3UsernameLabel.setBounds(10, 100, 100, 30);
		p3UsernameLabel.setForeground(Color.GRAY);
		p3UsernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel3.add(p3UsernameLabel);
		
		p3UsernameInput = new JTextField();
		p3UsernameInput.setBounds(10, 130, 380, 30);
		p3UsernameInput.setToolTipText("");
		p3UsernameInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p3UsernameInput.setColumns(10);
		panel3.add(p3UsernameInput);
		
		p3EmailLabel = new JLabel("Email");
		p3EmailLabel.setForeground(Color.GRAY);
		p3EmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p3EmailLabel.setBounds(10, 180, 100, 30);
		panel3.add(p3EmailLabel);
		
		p3EmailInput = new JTextField();
		p3EmailInput.setToolTipText("");
		p3EmailInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p3EmailInput.setColumns(10);
		p3EmailInput.setBounds(10, 210, 380, 30);
		panel3.add(p3EmailInput);
		
		p3SendMyPassword = new JButton("Send me my password");
		p3SendMyPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		p3SendMyPassword.setBounds(10, 418, 380, 50);
		panel3.add(p3SendMyPassword);
		
		p3BackButton = new JLabel("Back");
		p3BackButton.setBounds(192, 520, 150, 30);
		panelForgotPassword.add(p3BackButton);
		p3BackButton.setForeground(new Color(30, 144, 255));
		p3BackButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p3BackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p3BackButton.addMouseListener(new MouseAdapter() {
			 
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        System.out.println("p3BackButton mouse click");
		        switchPanels(panelLogIn);
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	System.out.println("p3BackButton mouse enter");
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	System.out.println("p3BackButton mouse exit");
		    }
		});
		
		
		
	}
	
	public void switchPanels(JPanel panel) {
		cardlayout.show(layeredPane, panel.getName());
	}
}
