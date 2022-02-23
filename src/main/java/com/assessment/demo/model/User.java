package com.assessment.demo.model;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

public class User {
    private Integer userId;
    private Date transactionDate;
    private Double transactionAmt;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmt() {
        return transactionAmt;
    }

    public void setTransactionAmt(Double transactionAmt) {
        this.transactionAmt = transactionAmt;
    }

    public User() {
    }

    public User(Integer userId, Date transactionDate, Double transactionAmt) {
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.transactionAmt = transactionAmt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", transactionDate=" + transactionDate +
                ", transactionAmt=" + transactionAmt +
                '}';
    }
}


