package com.printway.httpfile.services;

import com.printway.httpfile.models.File;
import com.printway.httpfile.repositories.FileRepository;
import com.printway.httpfile.utils.FileStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            log.warn("Could not initialize folder for upload or folder exists");
        }
    }

    @Override
    public String save(MultipartFile file) {
        try {
            var fileName = generateFileName(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            var dbFile = new File();
            dbFile.setName(fileName);
            dbFile.setType(file.getContentType());
            dbFile.setSize(file.getSize());
            dbFile.setStatus(FileStatus.USING);
            fileRepository.save(dbFile);
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            var resource = new UrlResource(root.resolve(filename).toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private String generateFileName(String originName) {
        var now =  String.valueOf(new Date().getTime());
        var random = String.format("%06d", new Random().nextInt(999999));
        return now
                .concat("_")
                .concat(random)
                .concat("_")
                .concat(originName.toLowerCase());
    }
}