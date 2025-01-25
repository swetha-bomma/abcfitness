package com.assignment.abcfitness.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.assignment.abcfitness.ExcetionHandling.ApiException;
import com.assignment.abcfitness.dto.GymClassDto;
import com.assignment.abcfitness.entity.Gym;
import com.assignment.abcfitness.entity.GymClass;
import com.assignment.abcfitness.repository.GymClassRepository;
import com.assignment.abcfitness.repository.GymRepository;
import com.assignment.abcfitness.utility.Validations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GymClassService {

	private final GymClassRepository gymClassRepository;

	private final GymRepository gymRepository;

	@Transactional
	public GymClass CreateClassforClub(@RequestBody GymClassDto gymClassDto) {

		String FieldValidationResponse = fieldValidation(gymClassDto);
		if (!Optional.ofNullable(FieldValidationResponse).isEmpty()) {
			throw ApiException.builder().message(FieldValidationResponse).statusCode(HttpStatus.BAD_REQUEST).build();
		}

		if (!Validations.isFutureDate(gymClassDto.getStartDate())) {
			throw ApiException.builder().message("Start date must be in the future dates.")
					.statusCode(HttpStatus.BAD_REQUEST).build();

		}

		if (!Validations.isFutureDate(gymClassDto.getEndDate())) {
			throw ApiException.builder().message("End date must be in the future dates.")
					.statusCode(HttpStatus.BAD_REQUEST).build();
		}

		if (!Validations.isEndDateAfterStartDate(gymClassDto.getStartDate(), gymClassDto.getEndDate())) {
			throw ApiException.builder().message("End date must be greater than start date.")
					.statusCode(HttpStatus.BAD_REQUEST).build();

		}
		boolean isClassExists = gymClassRepository.isClassExistsBetweenDates(gymClassDto.getStartDate(),
				gymClassDto.getEndDate(), gymClassDto.getClub());

		if (isClassExists) {
			throw ApiException.builder()
					.message("Class Already Exists between the given StartDate and EndDate for that Club ")
					.statusCode(HttpStatus.BAD_REQUEST).build();

		}

		Gym clubclass = gymRepository.findById(gymClassDto.getClub())
				.orElseThrow(() -> ApiException.builder().message("Club Does not Exists ").build());

		GymClass gymEntity = GymClass.builder().className(gymClassDto.getClassName()).club(clubclass)
				.startDate(gymClassDto.getStartDate())
				.endDate(gymClassDto.getEndDate()).durationInMinutes(gymClassDto.getDurationInMinutes())
				.capacity(gymClassDto.getCapacity()).startTime(gymClassDto.getStartTime()).build();

		gymClassRepository.save(gymEntity);
		return gymEntity;
	}

	public static String fieldValidation(GymClassDto createClassDto) {
		String message = null;
		if (Optional.ofNullable(createClassDto.getClub()).isEmpty()) {
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

		return message;

	}

}
