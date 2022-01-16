package com.odontomed.controller;

import com.odontomed.dto.request.InfoPersonaRequestDto;
import com.odontomed.dto.request.LoginRequestDto;
import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.dto.request.UpdatePwdRequestDto;
import com.odontomed.exception.DniAlreadyRegistered;
import com.odontomed.exception.EmailAlreadyRegistered;
import com.odontomed.model.Jwt;
import com.odontomed.service.Interface.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUser service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto dto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(dto));
        }catch (EmailAlreadyRegistered e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (DniAlreadyRegistered d){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(d.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto dto){
        try {
            return ResponseEntity.ok(service.login(dto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

    }

    @PostMapping("/modificarPassword")
    public ResponseEntity<?> cambiarPwd(@Valid @RequestBody UpdatePwdRequestDto dto){
        try{
            return ResponseEntity.ok(service.cambiarPwd(dto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> isLogged(HttpServletRequest req){
        try{
            return ResponseEntity.ok(service.checkLogin(req));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(false);
        }
    }

    @PostMapping("/info")
    public ResponseEntity<?> infoPaciente(@Valid @RequestBody InfoPersonaRequestDto dto){
        try{
            return ResponseEntity.ok(service.datosUser(dto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
