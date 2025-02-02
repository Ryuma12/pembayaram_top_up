package com.pembayaran.service;

import com.pembayaran.filter.JwtService;
import com.pembayaran.model.BalanceUser;
import com.pembayaran.model.HistoryTransaction;
import com.pembayaran.model.ServiceProvide;
import com.pembayaran.repository.BalanceUserRepository;
import com.pembayaran.repository.BannerRepository;
import com.pembayaran.repository.HistoryTransactionRepository;
import com.pembayaran.repository.ServiceProvideRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    ServiceProvideRepository serviceProvideRepository;

    @Autowired
    BalanceUserRepository balanceUserRepository;

    @Autowired
    HistoryTransactionRepository historyTransactionRepository;

    @Autowired
    private JwtService jwtService;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Map> saveTopUp(HttpServletRequest request, Map data){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        Number userId = claims.get("user_id", Number.class);

        Map response = new HashMap<>();

        Number topUp = (Number) data.get("top_up_amount");
        if (topUp.doubleValue() < 0){
            response.put("status", 102);
            response.put("message", "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0");
            response.put("data", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        BalanceUser balanceUser = balanceUserRepository.findByUserId(userId.longValue());
        Double balancetmp = 0d;
        if (balanceUser == null){
            balancetmp = topUp.doubleValue();
            balanceUser = new BalanceUser();
            balanceUser.setBalance(new BigDecimal(topUp.toString()));
            balanceUser.setUserId(userId.longValue());
        }else {
            balancetmp = balanceUser.getBalance().doubleValue() + topUp.doubleValue();
            balanceUser.setBalance(new BigDecimal(balancetmp.doubleValue()));
        }
        balanceUserRepository.save(balanceUser);

        HistoryTransaction history = new HistoryTransaction();
        history.setUserId(userId.longValue());
        history.setTotalAmount(new BigDecimal(topUp.toString()));
        history.setTransactionType("TOP_UP");
        history.setDateTransaction(new Date());
        historyTransactionRepository.save(history);

        Map mapBalance = new HashMap<>();
        mapBalance.put("balance",balancetmp);

        response.put("status", 0);
        response.put("message", "Top Up Balance berhasil");
        response.put("data", mapBalance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Map> saveTransaction(HttpServletRequest request, Map data){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        Number userId = claims.get("user_id", Number.class);
        BalanceUser balanceUser = balanceUserRepository.findByUserId(userId.longValue());
        Date date = new Date();

        Map response = new HashMap<>();

        String serviceCode = (String) data.get("service_code");
        ServiceProvide serviceProvide = serviceProvideRepository.findByCodeIgnoreCase(serviceCode);
        if (serviceProvide != null){
            if (balanceUser.getBalance().doubleValue() >= serviceProvide.getPrice().doubleValue()){
                Double cal = balanceUser.getBalance().doubleValue() - serviceProvide.getPrice().doubleValue();
                balanceUser.setBalance(new BigDecimal(cal.doubleValue()));
                balanceUserRepository.save(balanceUser);

                HistoryTransaction history = new HistoryTransaction();
                history.setUserId(userId.longValue());
                history.setServiceProvideId(serviceProvide.getId());
                history.setServiceProvideName(serviceProvide.getName());
                history.setTotalAmount(serviceProvide.getPrice());
                history.setTransactionType("TRANSACTION");
                history.setDateTransaction(date);

                String invoice = "INV"+ date.getTime();
                history.setInvoice(invoice);
                historyTransactionRepository.save(history);

                Map historyMap = history.getJsonForInvoice();

                response.put("status", 0);
                response.put("message", "Top Up Balance berhasil");
                response.put("data", historyMap);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        response.put("status", 102);
        response.put("message", "Service ataus Layanan tidak ditemukan");
        response.put("data", null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public Map getHistoryTransaction (HttpServletRequest request, Integer page, Integer size){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        Number userId = claims.get("user_id", Number.class);

        List<HistoryTransaction> lht = new LinkedList<>();

        Map response = new HashMap<>();
        response.put("status", 0);
        response.put("message", "Get History Berhasil");

        if (page != null && size != null){
            Page<HistoryTransaction> pageHt= historyTransactionRepository.findByUserIdOrderByDateTransactionDesc(userId.longValue(), PageRequest.of(page, size));
            if (CollectionUtils.isNotEmpty(pageHt.getContent())){
                lht = pageHt.getContent();
            }
        }else {
            lht = historyTransactionRepository.findByUserIdOrderByDateTransactionDesc(userId.longValue());
        }

        if (CollectionUtils.isNotEmpty(lht)){
            List<Map> result = new LinkedList<>();
            lht.forEach(e -> result.add(e.getJsonForInvoice()));

            Map data = new HashMap<>();
            data.put("offset", page);
            data.put("limit", size);
            data.put("record", result);
            response.put("data", data);
        }

        return response;
    }


    public Map getBalance(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        Number userId = claims.get("user_id", Number.class);
        BalanceUser balanceUser = balanceUserRepository.findByUserId(userId.longValue());

        Map response = new HashMap<>();
        Map balance = new HashMap<>();
        response.put("status", 0);
        response.put("message", "Top Up Balance berhasil");

        if (balanceUser != null){
            balance.put("balance", balanceUser.getBalance());
            response.put("data", balance);
        }else {
            balance.put("balance", 0);
            response.put("data", balance);
        }

        return response;
    }
}
