package database;
import database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import domain.Measurement;
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
	private short wnddir;
	
	private DBConnection db;
	
	public DBPortal(Measurement mes){
		
		try{
			db = new DBConnection();
			System.out.println("Succesful connection with the database");
		} catch (Exception e){
			System.out.println("Could not make a connection with the database!");
			e.printStackTrace();
		}
		
		this.stn= mes.getStn();
		this.date= mes.getDate();
		this.time= mes.getTime();
		this.temp= mes.getTemp();
		this.dewp= mes.getDewp();
		this.stp= mes.getStp();
		this.slp= mes.getSlp();
		this.visib= mes.getVisib();
		this.wdsp= mes.getWdsp();
		this.prcp= mes.getPrcp();
		this.sndp= mes.getSndp();
		this.frshtt= mes.getFrshtt();
		this.cldc= mes.getCldc();
		this.wnddir= mes.getWnddir();
		
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
								+ cldc + "," + wnddir +")");
			System.out.println("Data saved!");
		} catch (Exception e){
			System.out.println("SQL statement could not be executed");
			e.printStackTrace();
		}
	}
	
	public synchronized ArrayList getLastWeatherData(int stn, String time){
		
		try{
			
			System.out.println("SELECT * FROM measurement WHERE stn = (" + stn + ") AND (time BETWEEN CAST(SUBTIME('" + time + "','00:00:30') AS time) AND CAST('" + time + "' AS time))");	
			return db.runSQLQuery("SELECT * FROM measurement WHERE stn = (" + stn + ") AND (time BETWEEN CAST(SUBTIME('" + time + "','00:00:30') AS time) AND CAST('" + time + "' AS time))");
		
		} catch (Exception e){
			System.out.println("Er ging iets fout bij het ophalen van de gegevens");
			e.printStackTrace();
			return null;	
		}
	}
		
	
}
