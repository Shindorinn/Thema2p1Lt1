package database;
import database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class DBPortal{
	
	private int stn;
	private String date;
	private String time;
	private float temp;
	private float dewp;
	private float stp;
	private float slp;
	private float visib;
	private float wdsp;
	private float prcp;
	private float sndp;
	private byte frshtt;
	private float cldc;
	private short wndir;
	
	private DBConnection db;
	
	public DBPortal(int stn, String date, String time, float temp, float dewp, 
					float stp, float slp, float visib, float wdsp, float prcp, 
								float sndp, byte frshtt, float cldc, short wndir){
		
		try{
			db = new DBConnection();
			System.out.println("Succesful connection with the database");
		} catch (Exception e){
			System.out.println("Could not make a connection with the database!");
			e.printStackTrace();
		}
		
		this.stn= stn;
		this.date= date;
		this.time= time;
		this.temp= temp;
		this.dewp= dewp;
		this.stp= stp;
		this.slp= slp;
		this.visib= visib;
		this.wdsp= wdsp;
		this.prcp= prcp;
		this.sndp= sndp;
		this.frshtt= frshtt;
		this.cldc= cldc;
		this.wndir= wndir;
		
		System.out.println("DBPortal created");
		
	}
	
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
	
	public synchronized void saveWeatherData(){
		
		/*String timeArray[];
		timeArray = time.split(":");*/
		try{
			System.out.println("Trying to save data!");
			db.runSQLStatement
			("INSERT INTO measurement VALUES (" + stn + ",'" + date + "','" + 
								time + "'," + temp + ","  + dewp + "," + stp + "," + slp + "," + 
								visib + "," + wdsp + "," + prcp + "," + sndp + "," + frshtt + ","
								+ cldc + "," + wndir +")");
			System.out.println("Data saved!");
		} catch (Exception e){
			System.out.println("SQL statement could not be executed");
			e.printStackTrace();
		}
	}
	
	public void getLastWeatherData(int stn, String time){
		
		try{
			System.out.println("SELECT * FROM measurement WHERE stn = (" + stn + ") AND (time BETWEEN CAST(SUBTIME('" + time + "','00:00:30') AS time) AND CAST('" + time + "' AS time))");
			
			db.runSQLQuery("SELECT * FROM measurement WHERE stn = (" + stn + ") AND (time BETWEEN CAST(SUBTIME('" + time + "','00:00:30') AS time) AND CAST('" + time + "' AS time))");
			
			//return db.runSQLQuery("SELECT * FROM measurement WHERE stn = " + stn + " AND time BETWEEN '" + time + "' AND SUBTIME('" + time + "','00:00:30')");
			
		} catch (Exception e){
			System.out.println("Er ging iets fout bij het ophalen van de gegevens");
			e.printStackTrace();
			
		}
	}
		
	
}
