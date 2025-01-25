package com.assignment.abcfitness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ABC Fitness", description = "Demo Project to manage their courses, classes, members."))
public class AbcfitnessApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbcfitnessApplication.class, args);
	}

}
