package com.assessment.demo.controller;

import com.assessment.demo.exception.UserTransactionExecption;
import com.assessment.demo.model.User;
import com.assessment.demo.service.UserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class UserOperationController {

    @Autowired
    private UserOperationService userOperationService;

    @GetMapping("/user/{userid}/lasttransdate")
    public ResponseEntity<Date> getRecentTransactionDate(@PathVariable("userid") Integer userId)
            throws UserTransactionExecption {
        ResponseEntity<Date> result;
        try {
            result = new ResponseEntity<Date>(userOperationService.
                    getRecentTransactionDate(userId), HttpStatus.OK);
        } catch (Exception e) {
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @GetMapping("/user/{userid}/lasttransamt")
    public ResponseEntity<Double> getLastTransactionAmt(@PathVariable("userid") Integer userId)
            throws UserTransactionExecption {
        ResponseEntity<Double> result;
        try {
            result = new ResponseEntity<Double>(userOperationService.
                    getLastTransactionAmt(userId), HttpStatus.OK);
        } catch (Exception e) {
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @GetMapping("/user/{userid}/totalamt")
    public ResponseEntity<Double> getTotalAmount(@PathVariable("userid") Integer userId)
            throws UserTransactionExecption {
        ResponseEntity<Double> result;
        try {
            result = new ResponseEntity<Double>(userOperationService.getTotalAmount(userId), HttpStatus.OK);
        } catch (Exception e) {
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/user/createusertrans")
    public ResponseEntity<String> createUserTransaction(@RequestBody User user) throws UserTransactionExecption {
        ResponseEntity<String> result;
        try {
            result = new ResponseEntity<String>(userOperationService.createUserTransaction(user), HttpStatus.OK);
        } catch (Exception e) {
            result = new ResponseEntity<>("Error Creating User Transaction \n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
