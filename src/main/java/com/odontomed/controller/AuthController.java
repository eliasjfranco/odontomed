package com.odontomed.controller;

import com.odontomed.dto.request.LoginRequestDto;
import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto dto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(dto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto dto){
        try {
            System.out.println(dto.getEmail());
            System.out.println(dto.getPassword());
            return ResponseEntity.ok(service.login(dto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
