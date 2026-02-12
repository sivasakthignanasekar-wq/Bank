package com.wipro.bank.bean;

import java.util.Date;

public class TransferBean {

    private int transactionId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private Date dateOfTransaction;
    private float amount;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    // Getter & Setter for From Account
    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        if (fromAccountNumber != null) {
            this.fromAccountNumber = fromAccountNumber.trim();
        }
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        if (toAccountNumber != null) {
            this.toAccountNumber = toAccountNumber.trim();
        }
    }

    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
