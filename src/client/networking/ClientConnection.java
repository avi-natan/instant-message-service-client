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
	
	private String remoteHost;
	private int remotePort;
	
	private Socket socket;
	
	private InputStream in;
	private OutputStream out;
	
	private boolean terminated;
	
	public ClientConnection() {}
	
	public ClientConnection(IMSClient gui, String remoteHost, int remotePort) {
		super();
		this.gui = gui;
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
