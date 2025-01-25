package com.assignment.abcfitness.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class_master")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassForClub {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "class_id")
	private Long classId;

	@Column(name = "class_name", nullable = false)
	private String className;

	@Column(name = "club_id", nullable = false)
	private Long clubId;

	@Column(name = "start_date", nullable = false)
	// @JsonFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	// @JsonFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate endDate;

	@Column(name = "start_time", nullable = false)
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime startTime;

	@Column(name = "duration", nullable = false)
	private Integer durationInMinutes;

	@Column(name = "capacity", nullable = false)
	private Integer capacity;
	@OneToMany(mappedBy = "createClassForClub", cascade = CascadeType.ALL)
	private List<Booking> bookings;

}
