package com.assignment.abcfitness.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.abcfitness.dto.CreateClassDto;
import com.assignment.abcfitness.service.CreateClassForClubService;

@RestController
@RequestMapping("/api/class")
public class CreateClassController {

	@Autowired
	CreateClassForClubService createclassService;

	// @GetMapping("/v1/getclubname")
	public String getClubName() {
		return "ABC Fitness Club ";
	}

	@PostMapping("/create-class")
	public ResponseEntity<String> CreateClassforClub(@RequestBody CreateClassDto createClassDto) {
		ResponseEntity<String> createClassResponse = createclassService.CreateClassforClub(createClassDto);
		return createClassResponse;
	}

}
