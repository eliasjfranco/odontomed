package com.odontomed.service.Interface;

import com.odontomed.dto.request.LoginRequestDto;
import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.dto.response.RegisterResponseDto;
import com.odontomed.exception.EmailAlreadyRegistered;
import com.odontomed.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

public interface IUser extends UserDetailsService {

    UserDetails loadUserByUsername(String email);

    RegisterResponseDto createUser(RegisterRequestDto dto) throws IOException, EmailAlreadyRegistered;

    String login(LoginRequestDto dto);
}
