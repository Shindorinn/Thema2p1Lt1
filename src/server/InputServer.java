package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class InputServer implements Runnable{
	// The thread the server will run on
	@SuppressWarnings("unused")
	private Thread thread;
	
	private boolean running;
	
	
	// The port the server will run on
	private Integer port;
	// The object representing the socket where the server listens
	private ServerSocket server;
	// The object representing the socket where the client connects with
	private Socket client;
	// The list of connected Clients that are being handled
	private ArrayList<ServerProtocol> clients;
	
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
        clients.add(protocol);
	}
	
	private void init() {
		try {
			// Initialize everything
			port = 6100;
			server = new ServerSocket(port);
			client = null;
			running = true;
			clients = new ArrayList<ServerProtocol>();
			//Finally add this object to a thread.
			thread = new Thread(this);
			
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port + ".");
            System.exit(1);
		}
	}

	
}
