package client.networking;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import client.gui.IMSClient;
import client.protocol.IMSProtocol;

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
	
	public ClientConnection() {}
	
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
	}

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
	
	private void processMessage(String[] message) throws IOException {
		switch(message[0]) {
		case "WELCOME":
			gui.write(message[1]);
			break;
		case "ADDFRIEND":
			gui.write(message[0] + " " + message[1] + ": " + message[2]);
			if(message[1].equals("SUCCESS")) gui.addFriendCallback(message[2]);
			break;
		case "REMOVEFRIEND":
			gui.write(message[0] + " " + message[1] + ": " + message[2]);
			if(message[1].equals("SUCCESS")) gui.removeFriendCallback(message[2]);
		default:
			break;	
		}
	}
	
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
				if(line.equals("SUCCESS")) {
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
	
	public void touchFriend(String method, String friendName) {
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

	public void terminate() {
		this.terminated = true;
		try {
			byte[] b = {-56, 10};
			out.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
