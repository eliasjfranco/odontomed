package com.odontomed.service.Interface;

import com.odontomed.dto.request.LoginRequestDto;
import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.dto.response.RegisterResponseDto;
import com.odontomed.exception.EmailAlreadyRegistered;
import com.odontomed.exception.NotRegisteredException;
import com.odontomed.model.Jwt;
import com.odontomed.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.Optional;

public interface IUser  {

    Optional<User> getByEmail(String email);

    Jwt login(LoginRequestDto dto) throws NotRegisteredException;

    RegisterResponseDto saveUser(RegisterRequestDto dto) throws EmailAlreadyRegistered, IOException;

    /*UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    User getUser(String email);

    User getUserById(Long id);*/
}
