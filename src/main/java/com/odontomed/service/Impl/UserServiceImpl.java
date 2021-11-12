package com.odontomed.service.Impl;


import com.odontomed.config.SendgridConfig;
import com.odontomed.dto.request.LoginRequestDto;
import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.dto.request.UpdateUserRequestDto;
import com.odontomed.dto.response.RegisterResponseDto;
import com.odontomed.dto.response.UserInfoResponseDto;
import com.odontomed.exception.*;

import com.odontomed.jwt.JwtProvider;
import com.odontomed.jwt.JwtTokenFilter;
import com.odontomed.model.*;
import com.odontomed.repository.UserRepository;
import com.odontomed.service.Interface.IUser;
import com.odontomed.util.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements IUser, UserDetailsService{

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ProjectionFactory projectionFactory;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider provider;
    @Autowired
    private FormatDate formatDate;
    @Autowired
    private SendgridConfig sendgrid;
    @Autowired
    private UserRepository userRepository;

    public static final String BEARER = "Bearer ";

    @Override
    public Optional<User> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getByEmail(s).get();
        if(user == null)
            throw new UsernameNotFoundException(messageSource.getMessage("user.error.not.found",null, Locale.getDefault()));
        return UsuarioMain.build(user);
    }

    @Override
    public RegisterResponseDto saveUser(RegisterRequestDto dto) throws EmailAlreadyRegistered, DniAlreadyRegistered {
        if(repository.findByEmail(dto.getEmail()).isPresent())
            throw new EmailAlreadyRegistered(messageSource.getMessage("user.error.email.registered",null,Locale.getDefault()));
        if(repository.findByDni(dto.getDni()).isPresent())
            throw new DniAlreadyRegistered(messageSource.getMessage("user.error.dni.registered", null, Locale.getDefault()));
        User user = new User();
        user = dto.getUserFromDto();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setFecha(formatDate.stringToDate(dto.getFecha()));



        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.getByRolNombre(ERole.ROLE_USER).get());
        user.setRole(roleSet);

        sendgrid.emailBienvenida(dto.getEmail(), user.getFirstname(), user.getLastname());

        return projectionFactory.createProjection(RegisterResponseDto.class, repository.save(user));

    }

    @Override
    public Jwt login(LoginRequestDto dto) {
        Jwt jwt = new Jwt();
        if(repository.findByEmail(dto.getEmail()).isEmpty()) throw new NotRegisteredException(
                messageSource.getMessage("login.error.email.not.registered", null, Locale.getDefault())
        );

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        jwt.setToken(provider.generateToken(authentication));
        return jwt;

    }

    @Override
    public Stream<UserInfoResponseDto> getAllUser(HttpServletRequest req) throws InvalidUserException {
        if(!isAdmin(req))
            throw new InvalidUserException(messageSource.getMessage("user.error.not.authorization",null,Locale.getDefault()));
        List<User> usuarios = repository.findAll();
        return usuarios.stream().map(user -> projectionFactory.createProjection(UserInfoResponseDto.class, user));
    }

    @Override
    public UserInfoResponseDto getInformationUser(String firstname, String lastname, HttpServletRequest req) throws InvalidUserException {
        if(!isAdmin(req))
            throw new InvalidUserException(messageSource.getMessage("user.error.not.authorization", null, Locale.getDefault()));
        User user = repository.findByName(firstname, lastname);
        if(user == null)
            throw new InvalidUserException(messageSource.getMessage("user.error.not.found.name",null,Locale.getDefault()));
        return projectionFactory.createProjection(UserInfoResponseDto.class, user);
    }

    @Override
    public UserInfoResponseDto updateInformationUser(String tel, HttpServletRequest req) throws InvalidUserException, UserNotFoundException {
        User user = getUserByToken(req);
        user.setTel(tel);
        return projectionFactory.createProjection(UserInfoResponseDto.class, repository.save(user));
    }

    Boolean isAdmin(HttpServletRequest req){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(ERole.ROLE_ADMIN.toString())))
            return true;
        return false;
    }

    private User getUserByToken(HttpServletRequest req){
        JwtTokenFilter jwtFilter = new JwtTokenFilter();
        JwtProvider jwtProvider = new JwtProvider();
        String token = jwtFilter.getToken(req);
        String username = jwtProvider.getNombreUsuarioFromToken(token);
        return userRepository.findByEmail(username).get();
    }
}
