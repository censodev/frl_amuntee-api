package com.printway.business.controllers;

import com.printway.business.models.Dispute;
import com.printway.business.repositories.DisputeRepository;
import com.printway.business.services.DisputeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/dispute")
@Slf4j
public class DisputeController {
    @Autowired
    private DisputeService disputeService;

    @Autowired
    private DisputeRepository disputeRepository;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            var rs = disputeService.save(file);
            return ResponseEntity.ok(rs);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("")
    public Page<Dispute> list(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int limit,
                              @RequestParam(defaultValue = "id") String orderBy,
                              @RequestParam(defaultValue = "asc") String order) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        return disputeRepository.findAll(PageRequest.of(page, limit, sort));
    }
}
