package com.pembayaran.controller;

import com.pembayaran.dto.RegisDto;
import com.pembayaran.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Map> registration (@RequestBody RegisDto data){
        return userService.registration(data);
    }

    @GetMapping("/profile")
    public Map getProfile() throws AuthenticationException {
        return userService.getProfile();
    }

    @PutMapping("/profile/update")
    public ResponseEntity<Map>updateProfile (@RequestBody Map data) throws AuthenticationException{
        return userService.updateData(data);
    }
}
