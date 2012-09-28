package main;
import java.rmi.server.ServerCloneException;
import server.InputServer;
import database.DBConnection;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try{
			DBConnection db = new DBConnection();
			db.getConnection();
		} catch( Exception e){
			
		}
		
		InputServer server = new InputServer();
		while(true);
		// TODO Auto-generated method stub
		
	}

}
