package com.printway.business.services;

import com.printway.business.models.Dispute;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface DisputeService {
    List<Dispute> save(MultipartFile file);
    boolean hasCSVFormat(MultipartFile file);
    List<Dispute> csvToDisputes(InputStream is);
}
