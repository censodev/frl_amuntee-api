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
                .addFilterBefore(new JwtTokenAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                        // allow all who are accessing "auth" service
                        .antMatchers(jwtProvider.getUri()).permitAll()
                        // must be an admin if trying to access admin area (authentication is also required here)
//                        .antMatchers("/api/user/**").hasRole("ADMIN")
                        // Any other request must be authenticated
//                        .anyRequest().authenticated();
                        .anyRequest().permitAll()
        ;
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }
}
