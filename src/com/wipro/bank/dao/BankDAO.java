package com.wipro.bank.dao;

import java.sql.*;
import com.wipro.bank.bean.TransferBean;

public class BankDAO {

    public boolean validateAccount(Connection con, String accountNumber) throws SQLException {
        String query = "SELECT 1 FROM ACCOUNT_TBL WHERE ACCOUNT_NUMBER=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, accountNumber.trim());
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public float findBalance(Connection con, String accountNumber) throws SQLException {
        String query = "SELECT BALANCE FROM ACCOUNT_TBL WHERE ACCOUNT_NUMBER=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, accountNumber.trim());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getFloat(1);
        }
        return -1;
    }

    public boolean updateBalance(Connection con, String accountNumber, float newBalance) throws SQLException {
        String query = "UPDATE ACCOUNT_TBL SET BALANCE=? WHERE ACCOUNT_NUMBER=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setFloat(1, newBalance);
        ps.setString(2, accountNumber.trim());
        return ps.executeUpdate() > 0;
    }

    public int generateSequenceNumber(Connection con) throws SQLException {
        String query = "SELECT TRANSACTIONID_SEQ.NEXTVAL FROM dual";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        throw new SQLException("Sequence not generated");
    }

    public boolean transferMoney(Connection con, TransferBean bean) throws SQLException {
        String query = "INSERT INTO TRANSFER_TBL VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, bean.getTransactionId());
        ps.setString(2, bean.getFromAccountNumber());
        ps.setString(3, bean.getToAccountNumber());
        ps.setDate(4, new java.sql.Date(bean.getDateOfTransaction().getTime()));
        ps.setFloat(5, bean.getAmount());

        return ps.executeUpdate() > 0;
    }
}
