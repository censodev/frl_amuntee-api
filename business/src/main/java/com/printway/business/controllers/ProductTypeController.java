package com.printway.business.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.printway.business.models.ProductType;
import com.printway.business.repositories.ProductTypeRepository;
import com.printway.business.requests.ProductTypeStoreRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/product-type")
@Slf4j
public class ProductTypeController {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("")
    public Page<ProductType> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int limit,
                                  @RequestParam(defaultValue = "id") String orderBy,
                                  @RequestParam(defaultValue = "asc") String order) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        return productTypeRepository.findAll(PageRequest.of(page, limit, sort));
    }

    @PostMapping("")
    public ProductType add(@RequestBody ProductTypeStoreRequest request) {
        try {
            var prd = objectMapper.convertValue(request, ProductType.class);
            return productTypeRepository.save(prd);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("{id}")
    public ProductType update(@PathVariable() int id,
                            @RequestBody ProductTypeStoreRequest request) {
        try {
            var prd = objectMapper.convertValue(request, ProductType.class);
            prd.setId(id);
            return productTypeRepository.save(prd);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
