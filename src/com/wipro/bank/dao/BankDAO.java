package com.wipro.bank.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.util.DBUtil;

public class BankDAO {
	public int generateSequenceNumber() {
	    String query = "SELECT TRANSACTIONID_SEQ.NEXTVAL FROM dual";

	    try (Connection con = DBUtil.getDBConnection();
	         PreparedStatement ps = con.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    throw new RuntimeException("Unable to generate transaction ID");
	}
 public boolean validateAccount(String accountNumber)
 {
	 
      Connection con=null;
      boolean isValid=false;
      PreparedStatement ps = null;
      ResultSet rs = null;
      
	 try {
	  con = DBUtil.getDBConnection();
	  String query="SELECT * FROM ACCOUNT_TBL WHERE Account_Number =?";
	  ps=con.prepareStatement(query);
	  ps.setString(1, accountNumber);
	  
	  rs=ps.executeQuery();
	  if(rs.next()){
		  isValid=true;
	  }
		  
	  }catch(SQLException e)
	 {
		  System.out.println(e);
	 }
	  return isValid;
	 
 }
 public float findBalance(String accountNumber)
 {
	 if(validateAccount(accountNumber)) {
		
	 try {
		 Connection con=DBUtil.getDBConnection();
		 String query="SELECT balance FROM ACCOUNT_TBL WHERE Account_Number=?";
		 PreparedStatement ps = con.prepareStatement(query);
		 ps.setString(1,accountNumber);
		 ResultSet rs=ps.executeQuery();
		 rs.next();
		float balance= rs.getFloat(1);
		return balance;
		 }
	 catch(SQLException e)
	 {
		 return -1;
	 }
	 }
	 return -1;	 	 
 
 }
 public boolean updateBalance(String accountNumber,float newBalance)
 {
	 try {
		 Connection con=DBUtil.getDBConnection();
		 String query="UPDATE ACCOUNT_TBL SET balance=? WHERE Account_Number=?";
		 PreparedStatement ps = con.prepareStatement(query);
		 ps.setFloat(1,newBalance);
		 ps.setString(2, accountNumber);
		 int rows=ps.executeUpdate();
		
		 if(rows > 0)
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	 }catch(SQLException e)
	 {
		 System.out.println(e);
	 }
	 return false;
 }
 public boolean transferMoney(TransferBean transferBean) {
	    try {
	        transferBean.setTransactionId(generateSequenceNumber());

	        Connection con = DBUtil.getDBConnection();
	        String query = "INSERT INTO TRANSFER_TBL VALUES (?,?,?,?,?)";
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1, transferBean.getTransactionId());
	        ps.setString(2, transferBean.getFromAccountNumber());
	        ps.setString(3, transferBean.getToAccountNumber());
	        ps.setDate(4, new java.sql.Date(transferBean.getDateOfTransaction().getTime()));
	        ps.setFloat(5, transferBean.getAmount());
	        int rows = ps.executeUpdate();
	        return rows > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
}