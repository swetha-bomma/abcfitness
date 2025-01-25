package com.assignment.abcfitness.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.abcfitness.dto.BookingDto;
import com.assignment.abcfitness.dto.BookingResponseDto;
import com.assignment.abcfitness.dto.SearchCriteria;
import com.assignment.abcfitness.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/slot-booking")
    public ResponseEntity<String> bookClass(@RequestBody BookingDto bookingDto) {
        ResponseEntity<String> bookingResponse = bookingService.bookClass(bookingDto);
        return bookingResponse;
    }

    @GetMapping("/getBookingDetais")
    public ResponseEntity<List<BookingResponseDto>> getBookingDetails(@RequestBody SearchCriteria searchCriteria) {
        List<BookingResponseDto> BookingDetails = bookingService.getBookedSlotsDetails(searchCriteria);
        return ResponseEntity.ok().body(BookingDetails);

    }

}
