package com.pembayaran.service;


import com.pembayaran.dto.RegisDto;
import com.pembayaran.model.User;
import com.pembayaran.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

//    @Autowired
//    private AuthHelper authHelper;

    public ResponseEntity<Map> registration(RegisDto data){
        Map response = new HashMap<>();

        Boolean emailStatus = data.getEmail().matches("^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$");
        Boolean checkPassword = data.getPassword() != null && data.getPassword().length() >= 8 ? Boolean.TRUE : Boolean.FALSE;
        if (!emailStatus){
            response.put("status", 102);
            response.put("message", "Parameter email tidak sesuai format");
            response.put("data", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (!checkPassword){
            response.put("status", 102);
            response.put("message", "Parameter email tidak sesuai format");
            response.put("data", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(data.getEmail())){
            response.put("status", 999);
            response.put("message", "Parameter email sudah pernah digunakan");
            response.put("data", null);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        User user = new User(data);
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setRoles("USER");
        userRepository.save(user);

        response.put("status", 0);
        response.put("message", "Registrasi berhasil silahkan login");
        response.put("data", null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Map getProfile() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).get();

        Map response = new HashMap<>();
        response.put("status", 0);
        response.put("message", "Sukses");
        response.put("data", user.getJsonForProfile());
        return response;
    }

    public ResponseEntity<Map> updateData(Map data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).get();

        String firstName = (String) data.get("first_name");
        String lastName = (String) data.get("last_name");

        user.setFirstName(firstName);
        user.setLastName(lastName);

        Map response = new HashMap<>();
        response.put("status", 0);
        response.put("message", "Sukses");
        response.put("data", user.getJsonForProfile());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
