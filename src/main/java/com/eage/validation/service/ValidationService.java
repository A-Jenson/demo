package com.eage.validation.service;

import com.eage.validation.exception.BadRequest;
import com.eage.validation.request.ValidationRequest;
import com.eage.validation.response.ValidationResponse;
import com.eage.validation.util.Constants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;

@Service
public class ValidationService {
    Random randomGenerator = new Random();

    public void validate(ValidationResponse request) {
        String question = request.getQuestion();
        int answer = request.getAnswer();
        int sum = calculate(question);
        if(sum != answer){
            throw new BadRequest("Incorrect answer");
        }
    }

    private int calculate(String question) {
        String[] numbers = question.split(" ");
        Integer sum =  Arrays.stream(numbers[numbers.length-1].split(","))
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
        return sum;
    }

    public ValidationRequest getQuestion() {
        return  ValidationRequest.builder().question(generateQuestion()).build();
    }

    private String generateQuestion() {
        String question = Constants.Request.QUESTION;
        int i = 0;
        while (i < 3) {
            question = question.concat(String.valueOf(randomGenerator.nextInt(9) + 1));
            question = i < 2 ? question.concat(",") : question;
            i++;
        }
        return question;
    }
}
