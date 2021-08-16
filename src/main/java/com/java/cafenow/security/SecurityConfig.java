package com.java.cafenow.security;

import com.java.cafenow.advice.handler.CustomAccessDeniedHandler;
import com.java.cafenow.advice.handler.CustomAuthenticationEntryPoint;
import com.java.cafenow.security.jwt.JwtTokenFilterConfigurer;
import com.java.cafenow.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        //http Basic
        http.httpBasic().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/*/login/**").permitAll()
                .antMatchers("/*/register/**").permitAll()
                .antMatchers("/social/**").permitAll()
                .antMatchers("/*/kakao-info-token").permitAll()
                .anyRequest().hasAnyRole("CLIENT", "ADMIN_NOT_PERMIT", "ADMIN_PERMIT");

        // ExceptionHandling
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

        // 만약 로그인을 진행해주세요.라는 멘트가 나오면
        //프론트는 Refresh 토큰이 존재하는지 확인한다.
        // 1. 있다면 다시 토큰을 발급해야하고
        // 2. 없다면 새로 로그인을 진행해야한다.

        // Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public")

                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
                .antMatchers("/exception/**")
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**");;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
}
