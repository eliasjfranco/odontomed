package com.odontomed.config;

import com.odontomed.model.User;
import com.odontomed.service.Impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    AuthServiceImpl repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User usuario = repository.findByEmail(s);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new org.springframework.security.core.userdetails.User(null,null,null);
    }
}
