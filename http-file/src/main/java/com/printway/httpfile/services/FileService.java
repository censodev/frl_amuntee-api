package com.printway.httpfile.services;

import com.printway.httpfile.models.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    void init();
    File save(MultipartFile file);
    Resource load(String filename);
}
