package client.networking;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import client.gui.IMSClient;

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
		while(!this.terminated) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			try {
				line = br.readLine();
				byte[] b = line.getBytes();
				if(line != null && b[0] != -56) {
					gui.write(line);
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
	
	public boolean register() {
		byte[] bDelim = {-55};
		byte[] bUsername = this.username.getBytes();
		byte[] bEmail = this.email.getBytes();
		byte[] bPassword = this.password.getBytes();
		
		byte[] initParams = new byte[1 + this.username.length() + 1 + this.email.length() + 1 + this.password.length() + 1];
		int runner = 0;
		
		initParams[runner++] = bDelim[0];
		for(int i = 0; i < this.username.length(); i++) {
			initParams[runner++] = bUsername[i];
		}
		initParams[runner++] = bDelim[0];
		for(int i = 0; i < this.email.length(); i++) {
			initParams[runner++] = bEmail[i];
		}
		initParams[runner++] = bDelim[0];
		for(int i = 0; i < this.password.length(); i++) {
			initParams[runner++] = bPassword[i];
		}
		initParams[runner] = (byte)10;
		
		try {
			out.write(initParams);
		} catch (IOException e) {
			System.out.println("something wrong");
		}
		
		//TODO: wait for response and then do something
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		try {
			line = br.readLine();
			byte[] b = line.getBytes();
			if(line != null && b[0] != -56) {
				if(line.equals("SUCCESS")) {
					new Thread(this).start();
					System.out.println("successfully registered client " + this.username);
					return true;
				} else {
					System.out.println("could not register client " + this.username);
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
