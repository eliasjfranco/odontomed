package com.odontomed.jwt;

import com.odontomed.service.Impl.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    private UserServiceImpl userService;

    @Autowired
    private TokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String header = req.getHeader(HEADER_STRING);
        String username = null;
        String token = null;

        if(header != null && header.startsWith(TOKEN_PREFIX)){
            token = header.substring(7);
            try {
                username = jwtTokenProvider.getUsernameFromToken(token);
            }   catch (IllegalArgumentException e){
                logger.error("Se produjo un error al obtener usuario del token");
            }   catch (ExpiredJwtException e){
                logger.error("El token ha expirado");
            }   catch (SignatureException e){
                logger.error("Autenticacion fallo. Username o Password no es valido");
            }
        }   else {
            logger.warn("No se encontro la cadena Bearer");
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtTokenProvider.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        jwtTokenProvider.getAuthenticationToken(token, SecurityContextHolder.getContext().getAuthentication(), );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("Autenticado user" + username + ", configurado contexto de seguridad");
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(req,res);
    }
}
