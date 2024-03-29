package client.gui;

import client.networking.ClientConnection;
import client.networking.WritableGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.NoSuchElementException;

import javax.swing.JLayeredPane;

import java.awt.CardLayout;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * The Instant Message Service (IMS) Server class.
 * 
 * Extends JFrame to provide users with graphical user interface,
 * allowing them to register, log in, and communicate with their
 * peers.
 * 
 * Also implements the WritableGUI interface by providing the
 * {@link ClientConnection} with tools to manipulate its graphical
 * components.
 * 
 * @author Avi
 *
 */
@SuppressWarnings("serial")
public class IMSClient extends JFrame implements WritableGUI {
	
	/*
	 * The underlying connection manager for this client instance.
	 */
	private ClientConnection connection;

	/*
	 * Graphical components of this client instance.
	 */
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
	
	private JPanel panelClient;
		private JPanel panel_profile;
			private JLabel user_name_display;
			private JButton logout_button;
			private JTextField add_username;
			private JButton add_button;
		private JPanel panel_friends;
			private DefaultListModel<String> friends_list_model;
			private JList<String> friends_list;
		private JPanel panel_chat;
			private JScrollPane chat_scroller;
				private JTextArea chat_field;
		private JPanel panel_message;
			private JTextField message_field;
			private JButton send_button;

	
	/*
	 * Variables used for the registration panel		
	 */
	private boolean un;
	private boolean em;
	private boolean pw;
	private boolean cpw;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println("Hello IMS Client!");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IMSClient frame = new IMSClient();
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
	public IMSClient() {
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
		p1UsernameInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(p1UsernameInput.getText().length() == 0) {
					un = false;
				} else {
					un = true;
				}
				if(un && pw) {
					p1LogInButton.setEnabled(true);
				} else {
					p1LogInButton.setEnabled(false);
				}
			}
		});
		
		p1PasswordLabel = new JLabel("Password");
		p1PasswordLabel.setBounds(10, 180, 100, 30);
		panel1.add(p1PasswordLabel);
		p1PasswordLabel.setForeground(new Color(128, 128, 128));
		p1PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		p1PasswordInput = new JPasswordField();
		p1PasswordInput.setBounds(10, 210, 380, 30);
		panel1.add(p1PasswordInput);
		p1PasswordInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p1PasswordInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(String.valueOf(p1PasswordInput.getPassword()).length() == 0) {
					pw = false;
				} else {
					pw = true;
				}
				if(un && pw) {
					p1LogInButton.setEnabled(true);
				} else {
					p1LogInButton.setEnabled(false);
				}
			}
		});
		
		p1LogInButton = new JButton("Log In");
		p1LogInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIn(p1UsernameInput.getText(), String.valueOf(p1PasswordInput.getPassword()));
			}
		});
		p1LogInButton.setBounds(10, 418, 380, 50);
		p1LogInButton.setEnabled(false);
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
		    	p1UsernameInput.setText("");
				p1PasswordInput.setText("");
				un = em = pw = cpw = false;
				p1LogInButton.setEnabled(false);
		        switchPanels(panelNewAccount);
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
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
		    	p1UsernameInput.setText("");
				p1PasswordInput.setText("");
				un = em = pw = cpw = false;
				p1LogInButton.setEnabled(false);
		        switchPanels(panelForgotPassword);
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
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
		p2UsernameInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(p2UsernameInput.getText().length() == 0) {
					un = false;
				} else {
					un = true;
				}
				if(un && em && pw && cpw) {
					p2CreateAccountButton.setEnabled(true);
				} else {
					p2CreateAccountButton.setEnabled(false);
				}
			}
		});
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
		p2EmailInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(p2EmailInput.getText().length() == 0) {
					em = false;
				} else {
					em = true;
				}
				if(un && em && pw && cpw) {
					p2CreateAccountButton.setEnabled(true);
				} else {
					p2CreateAccountButton.setEnabled(false);
				}
			}
		});
		panel2.add(p2EmailInput);
		
		p2PasswordLabel = new JLabel("Password");
		p2PasswordLabel.setForeground(Color.GRAY);
		p2PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2PasswordLabel.setBounds(10, 260, 100, 30);
		panel2.add(p2PasswordLabel);
		
		p2PasswordInput = new JPasswordField();
		p2PasswordInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2PasswordInput.setBounds(10, 290, 380, 30);
		p2PasswordInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(String.valueOf(p2PasswordInput.getPassword()).length() == 0) {
					pw = false;
				} else {
					pw = true;
				}
				if(un && em && pw && cpw) {
					p2CreateAccountButton.setEnabled(true);
				} else {
					p2CreateAccountButton.setEnabled(false);
				}
			}
		});
		panel2.add(p2PasswordInput);
		
		p2ConfirmPasswordLabel = new JLabel("Confirm Password");
		p2ConfirmPasswordLabel.setForeground(Color.GRAY);
		p2ConfirmPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2ConfirmPasswordLabel.setBounds(10, 340, 150, 30);
		panel2.add(p2ConfirmPasswordLabel);
		
		p2ConfirmPasswordInput = new JPasswordField();
		p2ConfirmPasswordInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2ConfirmPasswordInput.setBounds(10, 370, 380, 30);
		p2ConfirmPasswordInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(String.valueOf(p2ConfirmPasswordInput.getPassword()).length() == 0) {
					cpw = false;
				} else {
					cpw = true;
				}
				if(un && em && pw && cpw) {
					p2CreateAccountButton.setEnabled(true);
				} else {
					p2CreateAccountButton.setEnabled(false);
				}
			}
		});
		panel2.add(p2ConfirmPasswordInput);
		
		p2CreateAccountButton = new JButton("Create Account");
		p2CreateAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createAccount(p2UsernameInput.getText(), p2EmailInput.getText(), String.valueOf(p2PasswordInput.getPassword()), String.valueOf(p2ConfirmPasswordInput.getPassword()));
			}
		});
		p2CreateAccountButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		p2CreateAccountButton.setBounds(10, 418, 380, 50);
		p2CreateAccountButton.setEnabled(false);
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
		    	p2UsernameInput.setText("");
				p2EmailInput.setText("");
				p2PasswordInput.setText("");
				p2ConfirmPasswordInput.setText("");
				un = em = pw = cpw = false;
				p2CreateAccountButton.setEnabled(false);
		        switchPanels(panelLogIn);
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
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
		p3UsernameInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(String.valueOf(p3UsernameInput.getText()).length() == 0) {
					un = false;
				} else {
					un = true;
				}
				if(un && em) {
					p3SendMyPassword.setEnabled(true);
				} else {
					p3SendMyPassword.setEnabled(false);
				}
			}
		});
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
		p3EmailInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(String.valueOf(p3EmailInput.getText()).length() == 0) {
					em = false;
				} else {
					em = true;
				}
				if(un && em) {
					p3SendMyPassword.setEnabled(true);
				} else {
					p3SendMyPassword.setEnabled(false);
				}
			}
		});
		panel3.add(p3EmailInput);
		
		p3SendMyPassword = new JButton("Send me my password");
		p3SendMyPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		p3SendMyPassword.setBounds(10, 418, 380, 50);
		p3SendMyPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("To implement password recovery");
			}
		});
		p3SendMyPassword.setEnabled(false);
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
		    	p2UsernameInput.setText("");
		    	p3UsernameInput.setText("");
		    	p3EmailInput.setText("");
				un = em = pw = cpw = false;
				p3SendMyPassword.setEnabled(false);
		        switchPanels(panelLogIn);
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
		    }
		});
		
		panelClient = new JPanel();
		layeredPane.add(panelClient, "panelClient");
		panelClient.setName("panelClient");
		panelClient.setLayout(null);
		
		panel_profile = new JPanel();
		panel_profile.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_profile.setBounds(510, 0, 274, 98);
		panelClient.add(panel_profile);
		panel_profile.setLayout(null);
		
		logout_button = new JButton("Logout");
		logout_button.setBounds(175, 11, 90, 30);
		logout_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logOut();
			}
		});
		panel_profile.add(logout_button);
		
		user_name_display = new JLabel("New label");
		user_name_display.setHorizontalAlignment(SwingConstants.CENTER);
		user_name_display.setBounds(10, 11, 137, 30);
		panel_profile.add(user_name_display);
		
		add_button = new JButton("Add");
		add_button.setBounds(175, 56, 90, 30);
		add_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewFriend(add_username.getText());
			}
		});
		add_button.setEnabled(false);
		panel_profile.add(add_button);
		
		add_username = new JTextField();
		add_username.setBounds(10, 56, 137, 30);
		add_username.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(add_username.getText().length() == 0) {
					add_button.setEnabled(false);
				} else {
					add_button.setEnabled(true);
				}
			}
		});
		panel_profile.add(add_username);
		add_username.setColumns(10);
		
		panel_friends = new JPanel();
		panel_friends.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_friends.setBounds(510, 102, 274, 459);
		panelClient.add(panel_friends);
		panel_friends.setLayout(null);
		
		friends_list = new JList<String>();
		friends_list.setBackground(new Color(240, 240, 240));
		friends_list.setBorder(new LineBorder(Color.LIGHT_GRAY));
		friends_list.setBounds(0, 0, 274, 459);
		friends_list_model =  new DefaultListModel<String>();
		friends_list.setModel(friends_list_model);
		friends_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					FriendPopupMenu menu = new FriendPopupMenu(friends_list.locationToIndex(e.getPoint()));
					menu.show(e.getComponent(), e.getX(), e.getY());
				} else if(SwingUtilities.isLeftMouseButton(e)) {
					System.out.println("Selected friend: " + friends_list_model.get(friends_list.locationToIndex(e.getPoint())));
					connection.touchFriend("SELECTFRIEND", friends_list_model.get(friends_list.locationToIndex(e.getPoint())));
					chat_field.setText(connection.getFriendChatHistory(friends_list_model.get(friends_list.locationToIndex(e.getPoint()))));
				}
			}
		});
		panel_friends.add(friends_list);
		
		panel_chat = new JPanel();
		panel_chat.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_chat.setBounds(0, 0, 507, 484);
		panelClient.add(panel_chat);
		panel_chat.setLayout(null);
		
		chat_field = new JTextArea();
		chat_field.setBackground(Color.WHITE);
		chat_field.setEditable(false);
		
		chat_scroller = new JScrollPane(chat_field);
		chat_scroller.setBounds(25, 26, 446, 416);
		chat_scroller.setBackground(Color.WHITE);
		panel_chat.add(chat_scroller);
		
		panel_message = new JPanel();
		panel_message.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_message.setBounds(0, 488, 507, 73);
		panelClient.add(panel_message);
		panel_message.setLayout(null);
		
		message_field = new JTextField(); // TODO: should not be editable if there is no selected friend.
		message_field.setBounds(10, 11, 406, 51);
		message_field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(message_field.getText().length() == 0) {
					send_button.setEnabled(false);
				} else {
					send_button.setEnabled(true);
				}
			}
		});
		panel_message.add(message_field);
		message_field.setColumns(10);
		
		send_button = new JButton("Send");
		send_button.setBounds(426, 11, 70, 51);
		send_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage(message_field.getText());
				message_field.setText("");
				send_button.setEnabled(false);
			}

		});
		send_button.setEnabled(false);
		panel_message.add(send_button);
		layeredPane.setName("layeredPane");
		
		
		
	}
	
	/**
	 * Creates a new account - namely registers a new user.
	 * 
	 * The method performs an input check (for now it's matching of the password
	 * and the confirmed password) and if the input is valid the method creates
	 * a new {@link ClientConnection} and tries to establish a connection to the server
	 * by using the {@link ClientConnection#handshake()} method with the
	 * "REGISTER" IMS protocol keyword.
	 * 
	 * If a connection to the server was established, the method cleans the create
	 * new account panel and switches switches panels to the client panel.
	 * 
	 * @param username - The registering username. 
	 * @param email - The registering email.
	 * @param password - The registering password.
	 * @param confirmedPassword - A confirmation of the registering password.
	 */
	public void createAccount(String username, String email, String password, String confirmedPassword) {
		if(!password.equals(confirmedPassword)) {
			System.out.println("Password and confirmed password not the same: " + password + ", " + confirmedPassword);
		} else {
			System.out.println("creating account: " + username + ", " + email + ", " + password + ", " + confirmedPassword);
			connection = new ClientConnection(this, username, email, password, "localhost", 8877);
			boolean status = connection.handshake("REGISTER");
			if(status) {
				p2UsernameInput.setText("");
				p2EmailInput.setText("");
				p2PasswordInput.setText("");
				p2ConfirmPasswordInput.setText("");
				un = em = pw = cpw = false;
				p2CreateAccountButton.setEnabled(false);
				user_name_display.setText(username);
				switchPanels(panelClient);
			}
			
		}
		
	}

	/**
	 * Logs in an existing user to the service.
	 * 
	 * The method creates a new {@link ClientConnection} and tries to establish
	 * a connection to the server by using the {@link ClientConnection#handshake()}
	 * method with the "LOGIN" IMS protocol keyword.
	 * 
	 * If a connection to the server was established, the method cleanes the login
	 * panel and switches switches panels to the client panel.
	 * 
	 * @param username - The login username.
	 * @param password - The login password.
	 */
	public void logIn(String username, String password) {
		connection = new ClientConnection(this, username, "", password, "localhost", 8877);
		boolean status = connection.handshake("LOGIN");
		if(status) {
			p1UsernameInput.setText("");
			p1PasswordInput.setText("");
			un = em = pw = cpw = false;
			p1LogInButton.setEnabled(false);
			user_name_display.setText(username);
			switchPanels(panelClient);
		}
	}
	
	/**
	 * This method uses the {@link #addFriendCallback} to perform the addition
	 * for each friend specified in the input, since the last step of adding a
	 * new friend that is performed in the said method is similar to the action
	 * that needs to be done when adding the friends after a login.
	 */
	@Override
	public void initFriends(String[] friends) {
		friends_list_model.clear();
		for(int i = 1; i < friends.length; i=i+2) {
			addFriendCallback(friends[i]);
		}
		
	}
	
	/**
	 * Terminates the connection associated with this client session by
	 * calling {@link ClientConnection#terminate}, cleans the client panel
	 * and returns the GUI to the login screen.
	 */
	public void logOut() {
		connection.terminate();
		user_name_display.setText("");
		add_username.setText("");
		add_button.setEnabled(false);
		friends_list_model.clear();
		chat_field.setText("");
		message_field.setText("");
		send_button.setEnabled(false);
		switchPanels(panelLogIn);
	}
	
	/**
	 * Hides the current showing panel and shows the panel that is given
	 * as an input.
	 * 
	 * @param panel - The panel to show.
	 */
	public void switchPanels(JPanel panel) {
		cardlayout.show(layeredPane, panel.getName());
	}
	
	/**
	 * Adds a friend with the specified name from the clients friends,
	 * by calling the {@link ClientConnection#touchFriend} method with the
	 * IMS protocol "ADDFRIEND" keyword.
	 * 
	 * @param name - The name of the friend to be added
	 * 
	 */
	private void addNewFriend(String name) {
		System.out.println("adding friend: " + name);
		connection.touchFriend("ADDFRIEND", name);
		// will not wait for an answer. handling will happen in the run loop.
	}
	
	/**
	 * This method adds the friend with the specified name - it adds his name
	 * to the friends list, and sets the chat field text to be the chat history
	 * of the client with the friend.
	 * 
	 * @param friend - The friend to add.
	 */
	@Override
	public void addFriendCallback(String friend) {
		friends_list_model.add(0, friend);
		chat_field.setText(connection.getFriendChatHistory(friend));
		connection.setSelectedFriend(friend);
	}
	
	/**
	 * Removes a friend with the specified name from the clients friends,
	 * by calling the {@link ClientConnection#touchFriend} method with the
	 * IMS protocol "REMOVEFRIEND" keyword.
	 * 
	 * @param name - The name of the friend to be removed.
	 */
	private void removeFriend(String name) {
		System.out.println("removing friend: " + name);
		connection.touchFriend("REMOVEFRIEND", name);
	}
	
	/**
	 * This method removes the friend with the specified name - it removes his name
	 * from the friends list, and selects the first friend in the list if there
	 * is any (selects him and shows his chat field).
	 * 
	 * @param friend - The name of the removed friend.
	 */
	@Override
	public void removeFriendCallback(String friend) {
		friends_list_model.removeElement(friend);
		try {
			String firstFriend = friends_list_model.firstElement();
			chat_field.setText(connection.getFriendChatHistory(firstFriend));
			connection.setSelectedFriend(firstFriend);
		} catch (NoSuchElementException e) {
			chat_field.setText("");
			connection.setSelectedFriend("");
		}
	}
	
	/**
	 * Uses the {@link ClientConnection} class to send a message that the user wrote
	 * to one of his friends, to the IMS server.
	 * 
	 * @param message - The user message to be sent.
	 */
	private void sendMessage(String message) {
		connection.sendMessage(message);
	}
	
	
	/**
	 * A pop up menu that appears when the user right clicks on a friend name from
	 * his list. The menu displays additional actions that the user can perform on
	 * the selected friend, such as deleting.
	 *  
	 * @author Avi
	 *
	 */
	public class FriendPopupMenu extends JPopupMenu {
		int elementIndex;
		JMenuItem item;
		public FriendPopupMenu(int elementIndex) {
			this.elementIndex = elementIndex;
			item = new JMenuItem("Remove Friend");
			item.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					removeFriend(friends_list_model.get(elementIndex));
				}
			});
			add(item);
		}
	}
	
	/**
	 * The method displays the input string onto the chat_field component by
	 * appending it to the component followed by a line separator.
	 */
	@Override
    public void write(String s) {
		chat_field.append(s + System.lineSeparator());
    }
}
