package com.assignment.abcfitness.ExcetionHandling;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiException extends RuntimeException {

	private String message;
	private HttpStatusCode statusCode;

}
