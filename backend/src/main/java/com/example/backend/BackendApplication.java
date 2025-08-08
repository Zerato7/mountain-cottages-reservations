package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.backend.config.EncryptionProperties;
import com.example.backend.config.FileUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({EncryptionProperties.class, FileUploadProperties.class})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
