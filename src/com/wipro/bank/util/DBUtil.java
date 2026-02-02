package com.wipro.bank.util;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	public static Connection getDBConnection() {
		Connection con=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1522:xe";
			String user="sakthi";
			String pass="system";
			con=DriverManager.getConnection(url,user,pass);
			 return con;
		}
		catch(ClassNotFoundException|SQLException e){
			e.printStackTrace();
			return null;
		}
	

  }
}