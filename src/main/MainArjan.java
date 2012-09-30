package main;
import java.rmi.server.ServerCloneException;
import java.sql.ResultSet;


import server.InputServer;
import database.DBConnection;
import database.DBPortal;


public class MainArjan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*DBPortal dbp = new DBPortal(10010, "2009-09-13", "15:45:30", 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, (byte)0, 0.0f, (short) 0);
		dbp.saveWeatherData();
		DBPortal dbp3 = new DBPortal(10010, "2009-09-13", "15:43:30", 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, (byte)0, 0.0f, (short) 0);
		dbp3.saveWeatherData();
		DBPortal dbp4 = new DBPortal(10010, "2009-09-13", "15:45:25", 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, (byte)0, 0.0f, (short) 0);
		dbp4.saveWeatherData();*/
		DBPortal dbp2 = new DBPortal();
		dbp2.getLastWeatherData(10010, "15:45:30");
		
		
	}

}
