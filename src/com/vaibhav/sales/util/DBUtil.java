package com.vaibhav.sales.util;
import java.sql.*;

public class DBUtil {

private static Connection conn = null;
	
	
	public static Connection getDBConnection() {
		try 
		{
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521", "sys as SYSDBA", "hr");
			return conn;
		}
		catch (SQLException e) 
		{
			System.out.println("Connection could not be estanlished");
			e.printStackTrace();
			return null;
		}
	}
}
