package com.assignment.abcfitness.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

    private String memberName;

    // @JsonFormat(pattern = "dd/MM/yyyy")
    // @JsonDeserialize(using = LocalDateDeserializer.class)
    // @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    // @JsonFormat(pattern = "dd/MM/yyyy")
    // @JsonDeserialize(using = LocalDateDeserializer.class)
    // @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;

}
