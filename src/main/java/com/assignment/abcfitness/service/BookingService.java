package com.assignment.abcfitness.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.assignment.abcfitness.ExcetionHandling.ApiException;
import com.assignment.abcfitness.dto.BookingDto;
import com.assignment.abcfitness.dto.BookingResponseDto;
import com.assignment.abcfitness.dto.SearchCriteria;
import com.assignment.abcfitness.entity.Booking;
import com.assignment.abcfitness.entity.CreateClassForClub;
import com.assignment.abcfitness.entity.Member;
import com.assignment.abcfitness.repository.BookingRepository;
import com.assignment.abcfitness.repository.CreateClassRepository;
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
    private final CreateClassRepository createClassRepository;
    private final EntityManager entityManager;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        System.out.println("BookingService initialized!");
        Member.builder().memberName("John").build();

    }

    public ResponseEntity<String> bookClass(BookingDto bookingDto) {

        if (Optional.ofNullable(bookingDto.getParticipationDate()).isEmpty()) {
            return ResponseEntity.badRequest().body("Participation Date is Required ");
        }
        if (Optional.ofNullable(bookingDto.getClassId()).isEmpty()) {
            return ResponseEntity.badRequest().body("Class Id is Required to book a slot ");
        }
        if (Optional.ofNullable(bookingDto.getMemberId()).isEmpty()) {
            return ResponseEntity.badRequest().body("Member required to book a slot ");
        }

        Member member = memberRepository.findById(bookingDto.getMemberId())
                .orElseThrow(() -> ApiException.builder().message("Member doesn't exists").build());
        if (!Validations.isParticipationDateValid(bookingDto.getParticipationDate())) {
            throw ApiException.builder().message("Participation date must be today or in the future dates .")
                    .statusCode(HttpStatus.BAD_REQUEST).build();
        }
        CreateClassForClub ClassExists = createClassRepository.findById(bookingDto.getClassId())
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

        return new ResponseEntity<>("Slot is Booked Successfully", HttpStatus.CREATED);

    }

    public List<BookingResponseDto> getBookedSlotsDetails(SearchCriteria bookingSearchDetails) {
        StringBuilder searchQuery = new StringBuilder(
                "select new com.assignment.abcfitness.dto.BookingResponseDto(c.className,c.startTime,b.participationDate,m.memberName) from "
                        +
                        " CreateClassForClub c join Booking b on c.classId=b.createClassForClub.classId  join Member m on m.memberId=b.member.memberId where 1=1 ");

        if (bookingSearchDetails.getStartDate() != null) {
            searchQuery.append(" and b.participationDate >= :startDate");
        }
        if (bookingSearchDetails.getEndDate() != null) {
            searchQuery.append(" and b.participationDate <= :endDate");
        }
        if (bookingSearchDetails.getMemberName() != null && !bookingSearchDetails.getMemberName().equals("")) {
            searchQuery.append(" and b.member.memberName LIKE '%' || :memberName || '%'");
        }

        TypedQuery<BookingResponseDto> query = entityManager.createQuery(searchQuery.toString(),
                BookingResponseDto.class);
        if (bookingSearchDetails.getStartDate() != null) {
            query.setParameter("startDate", bookingSearchDetails.getStartDate());
        }
        if (bookingSearchDetails.getEndDate() != null) {
            query.setParameter("endDate", bookingSearchDetails.getEndDate());
        }
        if (bookingSearchDetails.getMemberName() != null && !bookingSearchDetails.getMemberName().equals("")) {
            query.setParameter("memberName", bookingSearchDetails.getMemberName());
        }

        List<BookingResponseDto> bookingDetails = query.getResultList();

        return bookingDetails;

    }

}
