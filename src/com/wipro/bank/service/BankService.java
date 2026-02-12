package com.wipro.bank.service;

import java.sql.Connection;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.dao.BankDAO;
import com.wipro.bank.util.DBUtil;
import com.wipro.bank.util.InsufficientFundsException;

public class BankService {

    public String checkBalance(String accountNumber) {
        try (Connection con = DBUtil.getDBConnection()) {
            BankDAO dao = new BankDAO();

            if (dao.validateAccount(con, accountNumber)) {
                float balance = dao.findBalance(con, accountNumber);
                return "BALANCE IS: " + balance;
            } else {
                return "ACCOUNT NUMBER IS INVALID";
            }
        } catch (Exception e) {
            return "ERROR";
        }
    }

    public String transfer(TransferBean bean) {

        if (bean == null) return "INVALID";

        Connection con = null;

        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            BankDAO dao = new BankDAO();

            if (!dao.validateAccount(con, bean.getFromAccountNumber()) ||
                !dao.validateAccount(con, bean.getToAccountNumber())) {
                return "INVALID ACCOUNT";
            }

            float fromBalance = dao.findBalance(con, bean.getFromAccountNumber());

            if (fromBalance < bean.getAmount()) {
                throw new InsufficientFundsException();
            }

            float toBalance = dao.findBalance(con, bean.getToAccountNumber());

            boolean debit = dao.updateBalance(con,
                    bean.getFromAccountNumber(),
                    fromBalance - bean.getAmount());

            boolean credit = dao.updateBalance(con,
                    bean.getToAccountNumber(),
                    toBalance + bean.getAmount());

            bean.setTransactionId(dao.generateSequenceNumber(con));

            boolean insert = dao.transferMoney(con, bean);

            if (debit && credit && insert) {
                con.commit();
                return "SUCCESS";
            } else {
                con.rollback();
                return "FAILED";
            }

        } catch (InsufficientFundsException e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            return e.toString();

        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            return "ERROR";

        } finally {
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}
