package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBConnection {
	
	public DBConnection() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
	}
		
	public Connection getConnection() throws SQLException{			//Hoort private
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/unwdmi", "root", "");
		System.out.println("DBConnection made, oh f**k yeah^^");
		return conn;
		
	}
	
	public ResultSet runSQLQuery(String query){
		return null;
	}
	
	public void runSQLStatement(String query) throws SQLException { 
		
		Connection conn = getConnection();
		Statement s = conn.createStatement();
		s.executeUpdate(query);
		conn.close();
		
	}
}
