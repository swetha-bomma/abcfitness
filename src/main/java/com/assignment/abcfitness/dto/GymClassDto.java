package com.assignment.abcfitness.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GymClassDto {

	private String className;

	private Long club;

	// @JsonDeserialize(using = LocalDateDeserializer.class)
	// @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate startDate;

	// @JsonFormat(pattern = "dd/MM/yyyy")
	// @JsonDeserialize(using = LocalDateDeserializer.class)
	// @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate endDate;

	@Schema(type = "string", example = "20:03:03")
	private LocalTime startTime;

	private Integer durationInMinutes;

	private Integer capacity;

}
