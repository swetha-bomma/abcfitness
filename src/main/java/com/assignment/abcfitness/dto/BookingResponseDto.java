package com.assignment.abcfitness.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {

    private String className;
    private LocalTime classStartTime;
    private LocalDate bookingDate;
    private String memberName;

}
