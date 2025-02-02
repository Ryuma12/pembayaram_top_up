package com.pembayaran.controller;


import com.pembayaran.dto.LoginDto;
import com.pembayaran.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }
}
