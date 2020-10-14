package com.printway.business.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.printway.business.models.Product;
import com.printway.business.models.ProductType;
import com.printway.business.repositories.ProductRepository;
import com.printway.business.repositories.ProductTypeRepository;
import com.printway.business.requests.ProductStoreRequest;
import com.printway.business.requests.ProductTypeStoreRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("")
    public Page<Product> list(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int limit,
                              @RequestParam(defaultValue = "id") String orderBy,
                              @RequestParam(defaultValue = "asc") String order) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        return productRepository.findAll(PageRequest.of(page, limit, sort));
    }

    @GetMapping("{id}")
    public Product findOne(@PathVariable() int id) {
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping("")
    public Product add(@RequestBody ProductStoreRequest request) {
        try {
            var prd = objectMapper.convertValue(request, Product.class);
            prd.setStatus(1);
            return productRepository.save(prd);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("{id}")
    public Product update(@PathVariable() int id,
                       @RequestBody ProductStoreRequest request) {
        try {
            var prd = objectMapper.convertValue(request, Product.class);
            prd.setId(id);
            return productRepository.save(prd);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @DeleteMapping("{id}")
    public Product delete(@PathVariable() int id) {
        try {
            var prd = productRepository.findById(id).orElse(null);
            if (prd == null)
                return null;
            prd.setStatus(0);
            return productRepository.save(prd);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("type")
    public Page<ProductType> listTypes(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int limit,
                                      @RequestParam(defaultValue = "id") String orderBy,
                                      @RequestParam(defaultValue = "asc") String order) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        return productTypeRepository.findAll(PageRequest.of(page, limit, sort));
    }

    @PostMapping("type")
    public ProductType addType(@RequestBody ProductTypeStoreRequest request) {
        try {
            var prd = objectMapper.convertValue(request, ProductType.class);
            return productTypeRepository.save(prd);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("type/{id}")
    public ProductType updateType(@PathVariable() int id,
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
