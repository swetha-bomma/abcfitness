package com.assignment.abcfitness.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.assignment.abcfitness.dto.CreateClassDto;
import com.assignment.abcfitness.entity.CreateClassForClub;
import com.assignment.abcfitness.repository.CreateClassRepository;
import com.assignment.abcfitness.utility.Validations;

import jakarta.transaction.Transactional;

@Service
public class CreateClassForClubService {

	@Autowired
	CreateClassRepository createClassRepository;

	@Transactional
	public ResponseEntity<String> CreateClassforClub(@RequestBody CreateClassDto createClassDto) {
		String message = "";
		CreateClassForClub entity = new CreateClassForClub();
		try {
			ResponseEntity<String> FieldValidationResponse = fieldValidation(createClassDto);
			if (!Optional.ofNullable(FieldValidationResponse).isEmpty()) {
				return FieldValidationResponse;
			}

			if (!Validations.isFutureDate(createClassDto.getStartDate())) {
				return ResponseEntity.badRequest().body("Start date must be in the future dates.");
			}

			if (!Validations.isFutureDate(createClassDto.getEndDate())) {
				return ResponseEntity.badRequest().body("End date must be in the future dates.");
			}

			if (!Validations.isEndDateAfterStartDate(createClassDto.getStartDate(), createClassDto.getEndDate())) {
				return ResponseEntity.badRequest().body("End date must be greater than start date.");
			}
			boolean isClassExists = createClassRepository.isClassExistsBetweenDates(createClassDto.getStartDate(),
					createClassDto.getEndDate());

			if (isClassExists) {
				return ResponseEntity.badRequest().body("Class Already Exists between the given StartDate and EndDate");
			}
			BeanUtils.copyProperties(createClassDto, entity);

			createClassRepository.save(entity);
			message = "Class created successfully!";
			return new ResponseEntity<>(message, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error creating class: " + e.getMessage();
			return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	public static ResponseEntity<String> fieldValidation(CreateClassDto createClassDto) {
		String message = "";
		if (Optional.ofNullable(createClassDto.getClubId()).isEmpty()) {
			message = "Club Id is Required to Create a Class";

		} else if (Optional.ofNullable(createClassDto.getClassName()).isEmpty()) {
			message = "Class Name Required to Create a Class";
		} else if (Optional.ofNullable(createClassDto.getStartDate()).isEmpty()) {
			message = "Start Date Required to Create a Class";
		} else if (Optional.ofNullable(createClassDto.getEndDate()).isEmpty()) {
			message = "End Date Required to Create a Class";
		} else if (Optional.ofNullable(createClassDto.getStartTime()).isEmpty()) {
			message = "Start Time Required to Create a Class";
		} else if (Optional.ofNullable(createClassDto.getDurationInMinutes()).isEmpty()) {
			message = "Duration is Required to Create a Class";
		} else if (Optional.ofNullable(createClassDto.getCapacity()).isEmpty()) {
			message = "Capacity is Required to Create a Class";
		}

		if (!message.isEmpty()) {
			return ResponseEntity.badRequest().body(message);
		}

		return null;

	}

}
