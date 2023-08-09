package com.vtm;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "VTMProject API"))
public class VTMProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(VTMProjectApplication.class);
    }
}