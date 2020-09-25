package com.amuntee.gateway.config;

import com.amuntee.common.auth.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.amuntee.gateway.filters.JwtTokenAuthenticationFilter;
import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                        // make sure we use stateless session; session won't be used to store user's state.
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                        // handle an authorized attempts
                        .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                        // Add a filter to validate the tokens with every request
                        .addFilterAfter(new JwtTokenAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                // authorization requests config
                .authorizeRequests()
                        // allow all who are accessing "auth" service
                        .antMatchers(jwtProvider.getUri()).permitAll()
                        // must be an admin if trying to access admin area (authentication is also required here)
                        .antMatchers("/api/admin/**").hasRole("ADMIN")
                        // Any other request must be authenticated
                        .anyRequest().authenticated();
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }
}
