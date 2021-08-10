package com.odontomed.controller;

import com.odontomed.dto.request.RegisterRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {


    @PostMapping()
    public ResponseEntity<?> register(@Valid RegisterRequestDto dto){
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
