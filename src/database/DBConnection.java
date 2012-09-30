package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBConnection {
	
	public DBConnection() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
	}
		
	public Connection getConnection() throws SQLException{			//Hoort private
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/unwdmi", "root", "");
		return conn;
		
	}
	
	public ArrayList runSQLQuery(String query) throws SQLException{
		
		ArrayList ar = new ArrayList();
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
            System.out.println("Adding results to list");
		}
		rs.close();
		conn.close();
		return ar;
		
	}
	
	public void runSQLStatement(String query) throws SQLException { 
		
		Connection conn = getConnection();
		Statement s = conn.createStatement();
		s.executeUpdate(query);
		conn.close();
		
	}
}
