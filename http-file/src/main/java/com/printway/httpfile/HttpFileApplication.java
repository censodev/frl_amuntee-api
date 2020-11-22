package com.printway.httpfile;

import com.printway.httpfile.services.FileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication
public class HttpFileApplication {
	@Resource
	FileService fileService;

	public static void main(String[] args) {
		SpringApplication.run(HttpFileApplication.class, args);
	}
	@PostConstruct
	public void postConstruct() {
		fileService.init();
	}
}
