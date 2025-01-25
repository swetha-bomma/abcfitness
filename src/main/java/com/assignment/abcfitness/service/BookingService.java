package com.assignment.abcfitness.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.abcfitness.ExcetionHandling.ApiException;
import com.assignment.abcfitness.dto.BookingDto;
import com.assignment.abcfitness.dto.BookingResponseDto;
import com.assignment.abcfitness.dto.SearchCriteria;
import com.assignment.abcfitness.entity.Booking;
import com.assignment.abcfitness.entity.GymClass;
import com.assignment.abcfitness.entity.Member;
import com.assignment.abcfitness.repository.BookingRepository;
import com.assignment.abcfitness.repository.GymClassRepository;
import com.assignment.abcfitness.repository.MemberRepository;
import com.assignment.abcfitness.utility.Validations;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final GymClassRepository createClassRepository;
    private final EntityManager entityManager;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        System.out.println("BookingService initialized!");
        Member.builder().memberName("John").build();

    }

    public Booking bookClass(BookingDto bookingDto) {

        if (Optional.ofNullable(bookingDto.getParticipationDate()).isEmpty()) {

            throw ApiException.builder().message("Participation Date is Required .")
                    .statusCode(HttpStatus.BAD_REQUEST).build();
        }
        if (Optional.ofNullable(bookingDto.getClassId()).isEmpty()) {

            throw ApiException.builder().message("Class Id is Required to book a slot.")
                    .statusCode(HttpStatus.BAD_REQUEST).build();
        }
        if (Optional.ofNullable(bookingDto.getMemberId()).isEmpty()) {
            throw ApiException.builder().message("Member required to book a slot")
                    .statusCode(HttpStatus.BAD_REQUEST).build();

        }

        Member member = memberRepository.findById(bookingDto.getMemberId())
                .orElseThrow(() -> ApiException.builder().message("Member doesn't exists").build());
        if (!Validations.isParticipationDateValid(bookingDto.getParticipationDate())) {
            throw ApiException.builder().message("Participation date must be today or in the future dates .")
                    .statusCode(HttpStatus.BAD_REQUEST).build();
        }
        GymClass ClassExists = createClassRepository.findById(bookingDto.getClassId())
                .orElseThrow(() -> ApiException.builder().message("Class Does not Exists").build());

        if (!Validations.checkParticipateDateBetweenClassDates(bookingDto.getParticipationDate(), ClassExists)) {
            throw ApiException.builder()
                    .message("Slots are not available for selected class on selected date")
                    .build();
        }
        Long numberOfBookingsExists = bookingRepository.getBookingCountByClassAndParticipationDate(
                bookingDto.getClassId(),
                bookingDto.getParticipationDate());
        if (numberOfBookingsExists >= ClassExists.getCapacity()) {
            throw ApiException.builder()
                    .message("Class capacity exceeded.")
                    .build();
        }

        Booking booking = Booking.builder()
                .member(member)
                .createClassForClub(ClassExists)
                .participationDate(bookingDto.getParticipationDate())
                .build();

        bookingRepository.save(booking);

        // return new ResponseEntity<>("Slot is Booked Successfully",
        // HttpStatus.CREATED);
        return booking;
    }

    // public List<BookingResponseDto> getBookedSlotsDetails(SearchCriteria
    // bookingSearchDetails) {
    public List<BookingResponseDto> getBookedSlotsDetails(String memberName, LocalDate startDate, LocalDate endDate) {

        StringBuilder searchQuery = new StringBuilder(
                "select new com.assignment.abcfitness.dto.BookingResponseDto(c.className,c.startTime,b.participationDate,m.memberName) from "
                        +
                        " GymClass c join Booking b on c.classId=b.createClassForClub.classId  join Member m on m.memberId=b.member.memberId where 1=1 ");

        if (startDate != null) {
            searchQuery.append(" and b.participationDate >= :startDate");
        }
        if (endDate != null) {
            searchQuery.append(" and b.participationDate <= :endDate");
        }
        if (memberName != null && !memberName.equals("")) {
            searchQuery.append(" and m.memberName LIKE CONCAT('%',:memberName,'%')");
        }

        TypedQuery<BookingResponseDto> query = entityManager.createQuery(searchQuery.toString(),
                BookingResponseDto.class);
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        if (memberName != null && !memberName.equals("")) {
            query.setParameter("memberName", memberName);
        }

        List<BookingResponseDto> bookingDetails = query.getResultList();

        return bookingDetails;

    }

}
