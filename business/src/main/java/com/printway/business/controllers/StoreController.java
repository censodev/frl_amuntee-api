package com.printway.business.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.printway.business.models.Store;
import com.printway.business.repositories.StoreRepository;
import com.printway.business.requests.ProductStoreRequest;
import com.printway.business.requests.StoreStoreRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/store")
@Slf4j
public class StoreController {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("")
    public Page<Store> list(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int limit,
                            @RequestParam(defaultValue = "id") String orderBy,
                            @RequestParam(defaultValue = "asc") String order) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        return storeRepository.findAll(PageRequest.of(page, limit, sort));
    }

    @GetMapping("{id}")
    public Store findOne(@PathVariable() int id) {
        return storeRepository.findById(id).orElse(null);
    }

    @PostMapping("")
    public Store add(@RequestBody StoreStoreRequest request) {
        try {
            var str = objectMapper.convertValue(request, Store.class);
            return storeRepository.save(str);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("{id}")
    public Store update(@PathVariable() int id,
                          @RequestBody StoreStoreRequest request) {
        try {
            var str = objectMapper.convertValue(request, Store.class);
            str.setId(id);
            return storeRepository.save(str);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @DeleteMapping("{id}")
    public Store delete(@PathVariable() int id) {
        try {
            var str = storeRepository.findById(id).orElse(null);
            if (str == null)
                return null;
            str.setStatus(0);
            return storeRepository.save(str);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
