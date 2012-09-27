package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InputServer implements Runnable{
	// The thread the server will run on
	private Thread thread;
	private boolean running;
	
	
	// The port the server will run on
	private Integer port;
	// The object representing the socket where the server listens
	private ServerSocket server;
	// The object respresenting the socket where the client connects with
	private Socket client;
	
	
	public InputServer(){
		init();
	}

	@Override
	public void run() {
		while(running){
			listen();
		}
	}
	
	private void listen(){
		try {
            client = server.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
		
		System.out.println("Client connected to server.");
		
		handleClient(client);
	}
	
	private void handleClient(Socket client){
        ServerProtocol protocol = new ServerProtocol(client);
	}
	
	private void init() {
		try {
			port = 6100;
			server = new ServerSocket(port);
			client = null;
			running = true;
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port + ".");
            System.exit(1);
		}
	}

	
}
