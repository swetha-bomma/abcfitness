package com.assignment.abcfitness.utility;

import java.time.LocalDate;

import com.assignment.abcfitness.entity.GymClass;

public class Validations {

    public static boolean isFutureDate(LocalDate date) {

        LocalDate today = LocalDate.now();
        return date.isEqual(today) || date.isAfter(today);
        // return date.isAfter(LocalDate.now());
    }

    public static boolean isEndDateAfterStartDate(LocalDate startDate, LocalDate endDate) {

        return endDate.isAfter(startDate);
    }

    public static boolean isParticipationDateValid(LocalDate participationDate) {
        LocalDate today = LocalDate.now();
        return participationDate.isEqual(today) || participationDate.isAfter(today);
    }

    public static boolean checkParticipateDateBetweenClassDates(LocalDate participationDate,
            GymClass createClassForClub) {
        return !participationDate.isBefore(createClassForClub.getStartDate())
                && !participationDate.isAfter(createClassForClub.getEndDate());
    }

}
