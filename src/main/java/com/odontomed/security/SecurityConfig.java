package com.odontomed.security;

import com.odontomed.jwt.JwtEntryPoint;
import com.odontomed.jwt.JwtTokenFilter;
import com.odontomed.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;



    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImpl).passwordEncoder(encoder());
    }

    @Bean
    public JwtTokenFilter authenticationFilterBean() throws Exception{
        return new JwtTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/s3").permitAll()
                .antMatchers(HttpMethod.GET, "/s3").permitAll()
                .antMatchers(HttpMethod.GET, "/s3/**").permitAll()
                .antMatchers(HttpMethod.GET, "/s3/download").permitAll()
                .antMatchers(HttpMethod.GET,"/turno").hasAnyRole("USER","ADMIN").and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/turno").hasAnyRole("USER","ADMIN").and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/informacion").hasAnyRole("USER","ADMIN").and().authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/informacion").hasAnyRole("ADMIN").and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/blockTurn").hasAnyRole("USER","ADMIN").and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/turno/getInfo").hasAnyRole("USER","ADMIN").and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/info").hasAnyRole("USER","ADMIN").and().authorizeRequests()
                .anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs/**");
        web.ignoring().antMatchers("/swagger.json");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/webjars/**");
    }
}
