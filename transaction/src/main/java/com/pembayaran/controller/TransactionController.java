package com.pembayaran.controller;

import com.pembayaran.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PostMapping("/top_up")
    public ResponseEntity<Map> saveTopUp(HttpServletRequest request, @RequestBody Map data){
        return transactionService.saveTopUp(request, data);
    }

    @PostMapping("/transaction")
    public ResponseEntity<Map> saveTransaction(HttpServletRequest request, @RequestBody Map data){
        return transactionService.saveTransaction(request, data);
    }

    @GetMapping("/transaction/history")
    public Map getHistoryTransaction(HttpServletRequest request,
                                     @RequestParam(value = "offset", required = false) Integer page,
                                     @RequestParam(value = "limit", required = false) Integer size){
        return transactionService.getHistoryTransaction(request, page, size);
    }

    @GetMapping("/balance")
    public Map getBalance(HttpServletRequest request){
        return transactionService.getBalance(request);
    }
}
