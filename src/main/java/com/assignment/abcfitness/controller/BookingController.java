package com.assignment.abcfitness.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.abcfitness.dto.BookingDto;
import com.assignment.abcfitness.dto.BookingResponseDto;
import com.assignment.abcfitness.dto.SearchCriteria;

import com.assignment.abcfitness.service.BookingService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/slot-booking")
    public ResponseEntity<String> bookClass(@RequestBody BookingDto bookingDto) {
        bookingService.bookClass(bookingDto);
        // return bookingResponse;
        return new ResponseEntity<>("Slot is Booked Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getBookingDetais")
    public ResponseEntity<List<BookingResponseDto>> getBookingDetails(
            @Parameter(schema = @Schema(type = "string", format = "date", example = "2025-01-25")) @RequestParam(name = "startDate", required = false) LocalDate startDate,

            @Parameter(schema = @Schema(type = "string", format = "date", example = "2025-01-25")) @RequestParam(name = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "memberName", required = false) String memberName) {
        List<BookingResponseDto> BookingDetails = bookingService.getBookedSlotsDetails(memberName, startDate, endDate);
        return ResponseEntity.ok().body(BookingDetails);

    }

}
