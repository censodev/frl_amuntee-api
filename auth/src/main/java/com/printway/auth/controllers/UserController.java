package com.printway.auth.controllers;

import com.printway.auth.models.User;
import com.printway.auth.repositories.UserRepository;
import com.printway.auth.requests.UserStoreRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("")
    public Page<User> list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int limit,
                           @RequestParam(defaultValue = "id") String orderBy,
                           @RequestParam(defaultValue = "asc") String order) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        return userRepository.findAll(PageRequest.of(page, limit, sort));
    }

    @GetMapping("{id}")
    public User findOne(@PathVariable() long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("")
    public User add(@RequestBody UserStoreRequest request) {
        try {
            var user = objectMapper.convertValue(request, User.class);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            return userRepository.save(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("{id}")
    public User update(@PathVariable() long id,
                       @RequestBody UserStoreRequest request) {
        try {
            var user = objectMapper.convertValue(request, User.class);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setId(id);
            return userRepository.save(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @DeleteMapping("{id}")
    public User delete(@PathVariable() long id) {
        try {
            var user = userRepository.findById(id).orElse(null);
            if (user == null)
                return null;
            user.setStatus(0);
            return userRepository.save(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
