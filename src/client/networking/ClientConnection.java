package client.networking;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import client.gui.IMSClient;
import client.protocol.IMSProtocol;

/**
 * The class that is responsible for the connection of this client with
 * the server, managing the traffic and the client-side state of the current
 * logged user, his friends, the chat and his chat histories with his friends.
 * 
 * It is connected to his server-side counterpart, the ClientHandler, and
 * shares data with it about the state of the logged client, and also
 * communicates system and user messages.
 * 
 * It uses the {@link WriteableGUI} interface to make required changes to the
 * GUI that the user interacts with (e.g. updating the chat area after a
 * user message has been received).
 * 
 * @author Avi
 *
 */
public class ClientConnection implements Runnable {
	
	private IMSClient gui;
	private String username;
	private String email;
	private String password;
	private String remoteHost;
	private int remotePort;
	
	private Socket socket;
	
	private InputStream in;
	private OutputStream out;
	
	private boolean terminated;
	
	private String selectedFriend;
	
	private Map<String, StringBuilder> friendsChatHistories;
	
	/**
	 * The constructor. Called from the {@link IMSClient} at login/register time.
	 * It constructs the ClientConnection with the given parameters and tries to open
	 * a socket and get its input and output streams (On error it throws exception).<br>
	 * <br>
	 * After constructing, the {@link #handshake} method is called, where the client
	 * initialization completes, after receiving more initialization data from the
	 * server.<br>
	 * <br>
	 * The initialization varies depending on the IMS protocol keyword sent in the
	 * handshake method ("REGISTER"/"LOGIN").
	 * 
	 * @param gui - A pointer to the {@link WriteaBleGUI} client GUI that created this ClientConnection.
	 * @param username - The username given by the user.
	 * @param email - the email given by the user. Empty if the IMSP keywords was "LOGIN".
	 * @param password - The password given by the user.
	 * @param remoteHost - The IP address of the server. 
	 * @param remotePort - The port number of the server.
	 */
	public ClientConnection(IMSClient gui, String username, String email, String password, String remoteHost, int remotePort) {
		super();
		this.gui = gui;
		this.username = username;
		this.email = email;
		this.password = password;
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		try {
			this.socket = new Socket(this.remoteHost, this.remotePort);
			this.in = socket.getInputStream();
			this.out = socket.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.terminated = false;
		this.selectedFriend = "";
		this.friendsChatHistories = new HashMap<>();
		this.friendsChatHistories.put(this.username, new StringBuilder());
	}

	/**
	 * The main loop of the client connection. The client connection listens in this loop
	 * for any messages received from its server side counterpart. After parsing a message
	 * according to the {@link IMSProtocol} standard, it uses {@link #processMessage} to
	 * process the message accordingly.
	 * <br>
	 * The method runs while it is not terminated, as indicated by the terminated boolean member.
	 * 
	 */
	@Override
	public void run() {
		// main running loop
		while(!this.terminated) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			try {
				line = br.readLine();
				byte[] b = line.getBytes();
				if(line != null && b[0] != -56) {
					String[] message = IMSProtocol.bytesToMessage(b);
					processMessage(message);
				} else {
					this.terminated = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Processes a message received by the input stream.
	 * 
	 * @param message - The message received by the input stream.
	 */
	private void processMessage(String[] message) {
		switch(message[0]) {
		case "WELCOME":
			this.friendsChatHistories.get(this.username).append(message[1] + System.lineSeparator());
			gui.write(message[1]);
			break;
		case "ADDFRIEND":
			System.out.println(message[0] + " " + message[1] + ": " + message[2]);
			if(message[1].equals("SUCCESS")) {
				this.friendsChatHistories.put(message[2], new StringBuilder());
				this.friendsChatHistories.get(message[2]).append(message[0] + " " + message[1] + ": " + message[2] + System.lineSeparator());
				gui.addFriendCallback(message[2]);
			}
			break;
		case "REMOVEFRIEND":
			System.out.println(message[0] + " " + message[1] + ": " + message[2]);
			if(message[1].equals("SUCCESS")) {
				this.friendsChatHistories.remove(message[2]);
				gui.removeFriendCallback(message[2]);
			}
			break;
		case "MESSAGE":
			this.friendsChatHistories.get(message[1]).append(message[1] + ": " + message[2] + System.lineSeparator());
			if(message[1].equals(selectedFriend)) gui.write(message[1] + ": " + message[2]);
			break;
		default:
			break;	
		}
	}
	
	/**
	 * Completes the ClientConnection initialization by requesting data from
	 * its server-side counterpart ClientHandler.<br>
	 * If the IMS protocol keyword is "REGISTER", the expected reply from the server
	 * is "SUCCESS". Otherwise, the expected reply is "SUCCESS" followed by
	 * an array of this client friends names and their chat history, all in 
	 * byte array that can be converted to string array using the {@link IMSProtocol}.
	 * <br><br>
	 * If a "SUCCESS" has been received, the client connection initializes the client
	 * friends list (On a newly registered client the list would be empty), and then
	 * starts the {@link #run} method in a new thread, and finally returning true,
	 * indicating that a connection was successfully made, meaning that this client
	 * is ready to communicate with its server side counterpart. 
	 * 
	 * @param method - The IMS protocol keyword to be used ("REGISTER"/"LOGIN").
	 * 
	 * @return True if a connection was established.
	 */
	public boolean handshake(String method) {
		String[] message = new String[4];
		message[0] = method;
		message[1] = this.username;
		message[2] = this.email;
		message[3] = this.password;
		byte[] initParams = IMSProtocol.messageToBytes(message);
		
		try {
			out.write(initParams);
		} catch (IOException e) {
			System.out.println("something wrong");
		}
		
		// wait for response and then do something
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		try {
			line = br.readLine();
			byte[] b = line.getBytes();
			if(line != null && b[0] != -56) {
				String[] reply = IMSProtocol.bytesToMessage(b);
				if(reply[0].equals("SUCCESS")) {
					for(int i = 1; i < reply.length; i=i+2) {
						this.friendsChatHistories.put(reply[i], new StringBuilder(reply[i+1]));
					}
					gui.initFriends(reply);
					new Thread(this).start();
					System.out.println("successfull " + method + " as " + this.username);
					return true;
				} else {
					System.out.println("could not " + method + " as " + this.username);
					return false;
				}
			} else {
				this.terminated = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Manipulates a certain friend's status according to 3 types initiated
	 * by the GUI: "ADDFRIEND", "REMOVEFRIEND", "SELECTFRIEND".<br>
	 * ADDFRIEND will result in adding a friend, if there is a registered 
	 * user with the same name.<br>
	 * SELECTFRIEND will transfer the communication focus to the selected friend,
	 * and update the client chat accordingly.<br>
	 * REMOVEFRIEND will result in removing a friend from the client's friends,
	 * and also removing the client from the friend's friend list.<br>
	 * 
	 * With both ADDFRIEND and REMOVEFRIEND, a system message is sent to the server,
	 * since adding or removing a friend requires some server side actions.
	 * With SELECTFRIEND, all the changes required are done on the client side.
	 * 
	 * @param method
	 * @param friendName
	 */
	public void touchFriend(String method, String friendName) {
		
		if(method.equals("SELECTFRIEND")) {
			this.selectedFriend = friendName;
			return;
		}
		
		String[] message = new String[2];
		message[0] = method;
		message[1] = friendName;
		byte[] touchFriendParams = IMSProtocol.messageToBytes(message);
		
		try {
			out.write(touchFriendParams);
		} catch (IOException e) {
			System.out.println("something wrong");
		}
		
		// finish here. response handling will happen in the run loop.
		
		
	}
	
	/**
	 * Sends the user message to the focused friend (i.e. the last selected friend
	 * before sending the message).<br> 
	 * The method takes the raw message msg and constructs an IMS protocol message
	 * with the format:<br>
	 * <br>
	 * MESSAGE &lt;selected friend name&gt; &lt;raw message&gt; <br>
	 * <br>
	 * and then uses {@link IMSProtocol} to turn it into byte array.<br>
	 * <br>
	 * It then sends the byte array to its server side counterpart and appends the 
	 * original message to the chat history of the selected friend with this client
	 * and also appends the message to the chat area in the GUI.
	 * 
	 * @param msg - The raw user message.
	 */
	public void sendMessage(String msg) {
		System.out.println("sending to " + this.selectedFriend + ": " + msg);
		String[] message = new String[3];
		message[0] = "MESSAGE";
		message[1] = this.selectedFriend;
		message[2] = msg;
		byte[] messageBytes = IMSProtocol.messageToBytes(message);
		
		this.friendsChatHistories.get(this.selectedFriend).append(this.username + ": " + msg + System.lineSeparator());
		gui.write(this.username + ": " + msg);
		
		try {
			out.write(messageBytes);
		} catch (IOException e) {
			System.out.println("something wrong");
		}
		
		// finish here. response handling will happen in the run loop.
	}

	/**
	 * Sets the 'terminated' boolean indicator of this client connection to true,
	 * causing the loop in the {@link #run} method to stop.
	 * Also sends a terminating message to its server side counterpart.
	 */
	public void terminate() {
		this.terminated = true;
		try {
			byte[] b = {-56, 10};
			out.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the String representing the chat history for a certain friend.
	 * 
	 * @param friend - The name of the friend who;s chat history is requested.
	 * @return The requested friend's chat history, as a string.
	 */
	public String getFriendChatHistory(String friend) {
		return this.friendsChatHistories.get(friend).toString();
	}
	
	/*
	 * Setters
	 */
	public void setSelectedFriend(String friend) { this.selectedFriend = friend; }

}
