package com.odontomed.controller;

import com.odontomed.dto.request.UpdateUserRequestDto;
import com.odontomed.exception.InvalidUserException;
import com.odontomed.exception.UserNotFoundException;
import com.odontomed.service.Interface.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/informacion")
public class InfoPacienteController {

    @Autowired
    private IUser service;

    @GetMapping()
    public ResponseEntity<?> getAllInformacion(HttpServletRequest req){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getAllUser(req));
        }catch (InvalidUserException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping(path = "/{firstname}/{lastname}")
    public ResponseEntity<?> getInformacionUser(@PathVariable("firstname") String firstname, @PathVariable("lastname") String lastname, HttpServletRequest req){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getInformationUser(firstname,lastname,req));
        }catch (InvalidUserException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateContact(@RequestBody UpdateUserRequestDto dto, HttpServletRequest req){
        try{
            return ResponseEntity.status(HttpStatus.resolve(206)).body(service.updateInformationUser(dto, req));
        }catch (InvalidUserException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (UserNotFoundException u){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(u.getMessage());
        }
    }
}
