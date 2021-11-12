package com.odontomed.service.Interface;

import com.odontomed.dto.request.LoginRequestDto;
import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.dto.request.UpdateUserRequestDto;
import com.odontomed.dto.response.RegisterResponseDto;
import com.odontomed.dto.response.UserInfoResponseDto;
import com.odontomed.exception.*;
import com.odontomed.model.Jwt;
import com.odontomed.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Stream;

public interface IUser  {

    Optional<User> getByEmail(String email);

    Jwt login(LoginRequestDto dto) throws NotRegisteredException;

    RegisterResponseDto saveUser(RegisterRequestDto dto) throws EmailAlreadyRegistered, DniAlreadyRegistered;

    Stream<UserInfoResponseDto> getAllUser(HttpServletRequest req) throws InvalidUserException;

    UserInfoResponseDto getInformationUser(String firstname, String lastname, HttpServletRequest req) throws InvalidUserException;

    UserInfoResponseDto updateInformationUser(String tel, HttpServletRequest req) throws InvalidUserException, UserNotFoundException;
}
