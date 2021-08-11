package com.odontomed.controller;

import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.model.User;
import com.odontomed.service.Impl.AuthServiceImpl;
import com.odontomed.service.Interface.IAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthServiceImpl service;

    @PostMapping()
    public ResponseEntity<?> register(@Valid User user){
        try {
            return ResponseEntity.ok(service.save(user));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
