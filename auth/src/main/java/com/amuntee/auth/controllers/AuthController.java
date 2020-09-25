package com.amuntee.auth.controllers;

import com.amuntee.auth.models.User;
import com.amuntee.auth.repositories.UserRepository;
import com.amuntee.auth.requests.LoginRequest;
import com.amuntee.common.auth.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth")
@Slf4j
public class AuthController {
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("login")
    public String authenticate(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        var token = jwtProvider.generateToken(request.getUsername(), authorities);
        return String.format("%s%s", jwtProvider.getPrefix(), token);
    }

    @PostMapping("register")
    public boolean register(@RequestBody  LoginRequest request) {
        try {
            var user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }

        return true;
    }
}
