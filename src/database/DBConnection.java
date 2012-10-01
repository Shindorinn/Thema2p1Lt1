package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import domain.Measurement;


public class DBConnection {
	
	public DBConnection() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
	}
		
	public Connection getConnection() throws SQLException{			//Hoort private
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/unwdmi", "root", "");
		return conn;
		
	}
	
	protected ArrayList runSQLQuery(String query) throws SQLException{
		
		ArrayList<Measurement> ar = new ArrayList();
		Connection conn = getConnection();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query);
		
		while (rs.next()) {
		
			
			int stn = rs.getInt("stn");
            String date = rs.getString("date");
            String time = rs.getString("time");
            float temp = rs.getFloat("temp");
            float dewp = rs.getFloat("dewp");
            float stp = rs.getFloat("stp");
            float slp = rs.getFloat("slp");
            float visib = rs.getFloat("visib");
            float wdsp = rs.getFloat("wdsp");
            float prcp = rs.getFloat("prcp");
            float sndp = rs.getFloat("sndp");
            byte frshtt = rs.getByte("frshtt");
            float cldc = rs.getInt("cldc");
            short wnddir = rs.getShort("wnddir");
            ar.add(new Measurement(stn, date, time, temp, dewp, 
					stp, slp, visib, wdsp, prcp, 
					sndp, frshtt, cldc, wnddir));
            System.out.println("Added result to list");
		}
		rs.close();
		conn.close();
		System.out.println("Result list ready");
		return ar;
		
	}
	
	protected void runSQLStatement(String query) throws SQLException { 
		
		Connection conn = getConnection();
		Statement s = conn.createStatement();
		s.executeUpdate(query);
		conn.close();
		
	}
}
