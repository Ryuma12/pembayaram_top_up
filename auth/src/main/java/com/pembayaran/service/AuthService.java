package com.pembayaran.service;

import com.pembayaran.dto.LoginDto;
import com.pembayaran.model.User;
import com.pembayaran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Map> login(LoginDto loginDto){
        Map response = new HashMap<>();
        Boolean emailStatus = loginDto.getEmail().matches("^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$");
        if (!emailStatus){
            response.put("status", 102);
            response.put("message", "Parameter email tidak sesuai format");
            response.put("data", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(loginDto.getEmail()).get();
            String token =  jwtService.generateToken(user);
            Map tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            response.put("status", 0);
            response.put("message", "Login Sukses");
            response.put("data", tokenMap);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("status", 103);
            response.put("message", "Username atau password salah");
            response.put("data", null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
