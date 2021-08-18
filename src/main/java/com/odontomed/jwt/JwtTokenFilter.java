package com.odontomed.jwt;

import com.odontomed.service.Impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtTokenFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserServiceImpl userDetailsService;

    // El token esta formado por:
    // cabecera --> Authorization: Bearer token
    //Hace las comprobaciones
    // Este metodo se hace cada vez que se le haga una peticion al sever
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = getToken(request);

            if(token != null && jwtProvider.validateToken(token)){

                String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }catch (Exception e){
            logger.error("Fail en el método doFilter " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    //Obtenemos el token sin Bearer + el espacio
    public String getToken(HttpServletRequest request){

        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;

    }
}
