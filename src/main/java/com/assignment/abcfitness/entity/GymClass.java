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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gymclasses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GymClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "class_id")
	private Long classId;

	@Column(name = "class_name", nullable = false)
	private String className;

	// @Column(name = "club_id", nullable = false)
	// private Long clubId;

	@ManyToOne
	@JoinColumn(name = "club_id")
	private Gym club;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Column(name = "start_time", nullable = false)
	private LocalTime startTime;

	@Column(name = "duration", nullable = false)
	private Integer durationInMinutes;

	@Column(name = "capacity", nullable = false)
	private Integer capacity;
	@OneToMany(mappedBy = "createClassForClub", cascade = CascadeType.ALL)
	private List<Booking> bookings;

}
