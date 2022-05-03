package com.eage.validation.service;

import com.eage.validation.exception.BadRequest;
import com.eage.validation.request.ValidationRequest;
import com.eage.validation.response.ValidationResponse;
import com.eage.validation.util.Constants;
import com.eage.validation.util.JWTUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Random;

@Service
public class ValidationService {
    Random randomGenerator = new Random();

    public void validate(ValidationResponse request) {
    	boolean token = validateToken(request.getToken());
	        if(true) {
	        String question = request.getQuestion();
	        int answer = request.getAnswer();
	        int sum = calculate(question);
		        if(sum != answer){
		            throw new BadRequest("Incorrect answer");
		        }
	        }else {
	        	throw new BadRequest("Invalid Token");
	        }
    }
    
    private  boolean validateToken(String token){
    	Jws<Claims>  result = JWTUtil.validateToken(token);
    	System.out.print(result);
    	return (ObjectUtils.isEmpty(result) ? false : true) ;
    }

    private int calculate(String question) {
        String[] numbers = question.split(" ");
        Integer sum =  Arrays.stream(numbers[numbers.length-1].split(","))
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
        return sum;
    }

    public ValidationRequest getQuestion() {
        return  ValidationRequest.builder().question(generateQuestion()).token(generateJWT()).build();
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
    
    private String generateJWT() {
    	return JWTUtil.createToken();
    }
}
