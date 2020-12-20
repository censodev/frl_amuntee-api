package com.printway.gateway.config;

import com.printway.common.auth.JwtProvider;
import com.printway.gateway.filters.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                    .and()
                .addFilterBefore(new JwtTokenAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                        .antMatchers(jwtProvider.getUri(), "/api/product/**", "/api/store/**", "/api/file/**").permitAll()
                        .antMatchers(
                                "/api/user/**", "/api/supplier/**", "/api/dispute/**", "/api/config/**")
                                .hasRole("ADMIN")
                        .anyRequest().authenticated();
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }
}
