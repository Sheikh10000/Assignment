package com.assessment.demo.service;

import com.assessment.demo.dao.UserTransactionDao;
import com.assessment.demo.exception.UserTransactionExecption;
import com.assessment.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserOperationService {

    @Autowired
    private UserTransactionDao userTransactionDao;

    public Date getRecentTransactionDate(Integer userId) {
        return userTransactionDao.getRecentTransDate(userId);
    }

    public Double getTotalAmount(Integer userId) {
        return userTransactionDao.getTotalAmtForUser(userId);
    }

    public Double getLastTransactionAmt(Integer userId) {
        return userTransactionDao.getLastTransactionAmt(userId);
    }

    public String createUserTransaction(User user) throws UserTransactionExecption {
        Integer integer = userTransactionDao.insertIntoUserTransactionInfo(user.getUserId(), user.getTransactionDate());
        log.info("Inserted Generated Values Is" + integer);
        userTransactionDao.insertIntoUserTransactionAmt(user.getUserId(), user.getTransactionAmt());
        userTransactionDao.calculateTotalAmt(user.getUserId(), user.getTransactionAmt());
        return "Successfully Created User Transaction";
    }
}
