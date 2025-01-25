package com.assignment.abcfitness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.abcfitness.dto.GymClassDto;
import com.assignment.abcfitness.service.GymClassService;

@RestController
@RequestMapping("/api/class")
public class GymClassController {

	@Autowired
	GymClassService createclassService;

	// @GetMapping("/v1/getclubname")
	public String getClubName() {
		return "ABC Fitness Club ";
	}

	@PostMapping("/create-class")
	public ResponseEntity<String> CreateClassforClub(@RequestBody GymClassDto createClassDto) {
		createclassService.CreateClassforClub(createClassDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Class Created Successfully ");
	}

}
