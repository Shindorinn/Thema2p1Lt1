package main;
import java.rmi.server.ServerCloneException;
import server.InputServer;
import database.DBConnection;
import database.DBPortal;

public class MainArjan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DBPortal dbp = new DBPortal(10010, "Date", "15:45:30", 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, (byte)0, 0.0f, (short) 0);
		dbp.saveWeatherData();
		
	}

}
