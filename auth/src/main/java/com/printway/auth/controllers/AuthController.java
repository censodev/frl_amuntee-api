package com.printway.auth.controllers;

import com.printway.auth.models.CustomUserDetails;
import com.printway.auth.models.User;
import com.printway.auth.repositories.UserRepository;
import com.printway.auth.requests.AuthLoginRequest;
import com.printway.auth.requests.AuthRegisterRequest;
import com.printway.auth.requests.AuthResetPasswordRequest;
import com.printway.common.auth.Credentials;
import com.printway.common.auth.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
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

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("login")
    public Object authenticate(@RequestBody AuthLoginRequest request) {
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
        var user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        var credentials = new Credentials();
        credentials.setUid(user.getId());
        credentials.setCode(user.getCode());
        credentials.setUsername(user.getUsername());
        credentials.setFullname(user.getFullname());
        credentials.setAuthorities(authorities);
        return new Object() {
            public final String token = jwtProvider.generateToken(credentials);
            public final Credentials user = credentials;
        };
    }

    @PostMapping("register")
    public boolean register(@RequestBody AuthRegisterRequest request,
                            @RequestParam int role) {
        try {
            if (!request.getConfirmPassword().equals(request.getPassword()))
                throw new Exception("Password != Confirm password");

            var user = new User();
            user.setCode(request.getCode());
            user.setFullname(request.getFullname());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhone(request.getPhone());
            user.setEmail(request.getEmail());
            user.setStatus(1);

            switch (role) {
                case 1:
                    user.setRoleId(1); break;
                case 2:
                default:
                    user.setRoleId(2); break;
            }

            userRepository.save(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }

        return true;
    }

    @PostMapping("request-password")
    public ResponseEntity<Object> requestPassword(@RequestParam String email) {
        try {
            var user = userRepository.findByEmail(email);
            if (user == null)
                throw new Exception("Email not found");

            var resetCode = String.valueOf(new Random().nextInt(999999));
            user.setResetCode(resetCode);
            userRepository.save(user);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@printway.com");
            message.setTo(user.getEmail());
            message.setSubject("Printway Admin | Reset password");
            message.setText("Reset Code: " + resetCode);
            emailSender.send(message);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(500).body(ex.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody AuthResetPasswordRequest request) {
        try {
            if (!request.getPassword().equals(request.getConfirmPassword()))
                throw new Exception("Confirm password is not similar to password");
            var user = userRepository.findByCodeAndResetCode(request.getCode(), request.getResetCode());
            if (user == null)
                throw new Exception("Reset code is invalid");

            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setResetCode(null);
            userRepository.save(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(500).body(ex.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
