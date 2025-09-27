package com.learnings.tech_hub;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Career Guidance Hub REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Kavya",
                        email = "kavya@gmail.com"
                )
        )
)
public class TechHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechHubApplication.class, args);
	}

}
