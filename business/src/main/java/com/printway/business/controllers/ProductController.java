package com.printway.business.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.printway.business.models.Product;
import com.printway.business.models.ProductTemplate;
import com.printway.business.repositories.ProductRepository;
import com.printway.business.repositories.ProductTemplateRepository;
import com.printway.business.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTemplateRepository productTemplateRepository;

    @GetMapping("")
    public Page<Product> list(@RequestParam(defaultValue = "0") int createdBy,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int limit,
                              @RequestParam(defaultValue = "id") String orderBy,
                              @RequestParam(defaultValue = "asc") String order) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        if (createdBy == 0)
            return productService.findAll(PageRequest.of(page, limit, sort));
        return productService.findAllByCreatedBy(createdBy, PageRequest.of(page, limit, sort));
    }

    @GetMapping("{id}")
    public Product findOne(@PathVariable() int id) {
        return productService.find(id);
    }

    @PostMapping("")
    public Product add(@RequestBody Product request) {
        try {
            return productService.saveAndSync(request);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("{id}")
    public Product update(@PathVariable() int id,
                          @RequestBody Product request) {
        try {
            return productService.updateAndSync(id, request);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("template")
    public Page<ProductTemplate> listTemplates(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam(defaultValue = "id") String orderBy,
                                               @RequestParam(defaultValue = "asc") String order) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        return productTemplateRepository.findAll(PageRequest.of(page, limit, sort));
    }

    @GetMapping("template/{id}")
    public ProductTemplate findTemplate(@PathVariable() int id) {
        return productTemplateRepository.findById(id).orElse(null);
    }

    @PostMapping("template")
    public ProductTemplate addTemplate(@RequestBody ProductTemplate request) {
        try {
            request.setStatus(1);
            return productTemplateRepository.save(request);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("template/{id}")
    public ProductTemplate updateTemplate(@PathVariable() int id,
                                          @RequestBody ProductTemplate request) {
        try {
            request.setId(id);
            return productTemplateRepository.save(request);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
