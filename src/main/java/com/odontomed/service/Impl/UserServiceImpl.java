package com.odontomed.service.Impl;


import com.odontomed.dto.request.LoginRequestDto;
import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.dto.response.RegisterResponseDto;
import com.odontomed.exception.EmailAlreadyRegistered;

import com.odontomed.exception.NotRegisteredException;
import com.odontomed.jwt.JwtProvider;
import com.odontomed.model.ERole;
import com.odontomed.model.Role;
import com.odontomed.model.User;
import com.odontomed.model.UsuarioMain;
import com.odontomed.repository.RoleRepository;
import com.odontomed.repository.UserRepository;
import com.odontomed.service.Interface.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements IUser, UserDetailsService{

    @Autowired
    MessageSource messageSource;
    @Autowired
    ProjectionFactory projectionFactory;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    UserRepository repository;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider provider;

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

    /*@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(messageSource.getMessage("user.error.not.found", null, Locale.getDefault()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }*/

    @Override
    public RegisterResponseDto saveUser(RegisterRequestDto dto) throws IOException, EmailAlreadyRegistered {
        if(repository.findByEmail(dto.getEmail()).isPresent())
            throw new EmailAlreadyRegistered(messageSource.getMessage("user.error.email.registered",null,Locale.getDefault()));
        User user = dto.getUserFromDto();
        user.setPassword(encoder.encode(user.getPassword()));

        Role role = roleService.getByRolNombre(ERole.ROLE_USER).get();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRole(roleSet);

        return projectionFactory.createProjection(RegisterResponseDto.class, repository.save(user));

    }

    /*@Override
    public UserDetails loadUserByUsername(String email) {
        User user = repository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException(messageSource.getMessage("user.error.email.not.found",null,Locale.getDefault())));
        return User.build(user);
    }

    public RegisterResponseDto createUser(RegisterRequestDto dto) throws IOException, EmailAlreadyRegistered {
        if(repository.findByEmail(dto.getEmail()).isPresent()){
            throw new EmailAlreadyRegistered(messageSource.getMessage("user.error.email.registered", null, Locale.getDefault()));
        }

        User user = User.builder()
                .email(dto.getEmail())
                .dni(dto.getDni())
                .fecha(format(dto.getFecha()))
                .lastname(dto.getLastname())
                .firstname(dto.getFirstname())
                .tel(dto.getTel())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ERole.ROLE_USER.toString()));
        user.setRole(roles);

        User creation = repository.save(user);

        //Crear servicio de envio de email de bienvenida.

        return projectionFactory.createProjection(RegisterResponseDto.class, repository.save(creation));
    }*/

    @Override
    public String login(LoginRequestDto dto) {
        if(repository.findByEmail(dto.getEmail()).isEmpty()) throw new NotRegisteredException(
                messageSource.getMessage("login.error.email.not.registered", null, Locale.getDefault())
        );

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = provider.generateToken(authentication);
        return token;

    }
}
