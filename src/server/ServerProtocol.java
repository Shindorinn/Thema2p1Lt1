package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import logic.ParserCorrector;

public class ServerProtocol implements Runnable{

	private Socket client;
	private boolean running;
	private Thread thread;
	
	public ServerProtocol(Socket client){
		running = true;
		this.client = client;
		this.thread = new Thread(this);
		thread.start();
	}
	
	private void handleClient(Socket client){
		
		try{
			// From Server to Client
	        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
	        // From Client to Server
	        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	        
	        // The variables containing the information received from the Client        
	        StringBuffer fromClient = new StringBuffer();
	        String input;
	        	 
			while ((input = in.readLine()) != null) {
				 System.out.println("input : |" + input);
				 
				 fromClient.append(input);
				 if(input.equals("</WEATHERDATA>")){
					 new ParserCorrector(fromClient);
					 fromClient = new StringBuffer();
				 }
				 
				 input = null;
			}
			
			System.out.println(fromClient.toString());
			// End the connection
			out.close();
	        in.close();
	        client.close();
	        
		}catch(IOException ioex){
			ioex.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(running){
			handleClient(client);
		}
	}
}
