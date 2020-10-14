package com.printway.business.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.printway.business.models.ProductType;
import com.printway.business.models.Supplier;
import com.printway.business.repositories.SupplierRepository;
import com.printway.business.requests.ProductTypeStoreRequest;
import com.printway.business.requests.SupplierStoreRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/supplier")
@Slf4j
public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("")
    public Page<Supplier> list(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int limit,
                               @RequestParam(defaultValue = "id") String orderBy,
                               @RequestParam(defaultValue = "asc") String order) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        return supplierRepository.findAll(PageRequest.of(page, limit, sort));
    }

    @PostMapping("")
    public Supplier add(@RequestBody SupplierStoreRequest request) {
        try {
            var prd = objectMapper.convertValue(request, Supplier.class);
            return supplierRepository.save(prd);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("{id}")
    public Supplier update(@PathVariable() int id,
                              @RequestBody SupplierStoreRequest request) {
        try {
            var prd = objectMapper.convertValue(request, Supplier.class);
            prd.setId(id);
            return supplierRepository.save(prd);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
