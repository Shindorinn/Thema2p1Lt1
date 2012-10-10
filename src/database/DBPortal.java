package database;
import database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import domain.Measurement;
public class DBPortal{
	
	private DBConnection db;
	
	public DBPortal(){
		
		try{
			db = new DBConnection();
			System.out.println("Succesful connection with the database");
		} catch (Exception e){
			System.out.println("Could not make a connection with the database!");
			e.printStackTrace();
		}
		
		System.out.println("DBPortal created");
	}
	
	public synchronized void saveWeatherData(Measurement mes){
		
		/*String timeArray[];
		timeArray = time.split(":");*/
		try{
			System.out.println("Trying to save data!");
			db.runSQLStatement
			("INSERT INTO measurement VALUES (" + mes.getStn() + ",'" + mes.getDate() + "','" + 
					 mes.getTime() + "'," + mes.getTemp() + ","  + mes.getDewp() + "," + mes.getStp() + "," + mes.getSlp() + "," + 
					 mes.getVisib() + "," + mes.getWdsp() + "," + mes.getPrcp() + "," + mes.getSndp() + "," + mes.getFrshtt() + ","
								+ mes.getCldc() + "," + mes.getWnddir() +")");
			System.out.println("Data saved!");
		} catch (Exception e){
			System.out.println("SQL statement could not be executed");
			e.printStackTrace();
		}
	}
	
	public synchronized ArrayList<Measurement> getLastWeatherData(int stn, String time){
		
		try{
			
			//System.out.println("SELECT * FROM measurement WHERE stn = (" + stn + ") AND (time BETWEEN CAST(SUBTIME('" + time + "','00:00:30') AS time) AND CAST('" + time + "' AS time))");	
			ArrayList results = db.runSQLQuery("SELECT * FROM measurement WHERE stn = (" + stn + ") AND (time BETWEEN CAST(SUBTIME('" + time + "','00:00:30') AS time) AND CAST('" + time + "' AS time))");
			
			ArrayList<Measurement> castedResults = new ArrayList<Measurement>();
			
			for(Object o : results){
				castedResults.add((Measurement) o);
			}
			
			return castedResults;
		} catch (Exception e){
			System.out.println("Er ging iets fout bij het ophalen van de gegevens");
			e.printStackTrace();
			return null;	
		}
	}
		
	
}
