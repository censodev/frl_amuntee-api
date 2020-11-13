package com.printway.business.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.printway.business.models.Config;
import com.printway.business.repositories.ConfigRepository;
import com.printway.business.requests.ConfigStoreRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/config")
@Slf4j
public class ConfigController {
    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("")
    public Page<Config> list(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "1") int limit,
                            @RequestParam(defaultValue = "id") String orderBy,
                            @RequestParam(defaultValue = "asc") String order) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        return configRepository.findAllByStatus(1, PageRequest.of(page, limit, sort));
    }

    @PostMapping("")
    public Config add(@RequestBody ConfigStoreRequest request) {
        try {
            var str = objectMapper.convertValue(request, Config.class);
            str.setStatus(1);
            return configRepository.save(str);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("{id}")
    public Config update(@PathVariable() int id,
                        @RequestBody ConfigStoreRequest request) {
        try {
            var str = objectMapper.convertValue(request, Config.class);
            str.setId(id);
            return configRepository.save(str);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @DeleteMapping("{id}")
    public Config delete(@PathVariable() int id) {
        try {
            var str = configRepository.findById(id).orElse(null);
            if (str == null)
                return null;
            str.setStatus(0);
            return configRepository.save(str);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
