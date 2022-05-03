package com.eage.test;

import com.eage.validation.Application;
import com.eage.validation.exception.BadRequest;
import com.eage.validation.request.ValidationRequest;
import com.eage.validation.response.ValidationResponse;
import com.eage.validation.service.ValidationService;
import lombok.extern.java.Log;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log
public class ApplicationTests {

    @Autowired
    private ValidationService validationService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getQuestion() {
        ValidationRequest request =  validationService.getQuestion();
        log.info("Question="+request.getQuestion());
        Assert.assertNotNull("The object you enter return null", request);
    }

    @Test
    public void validateService(){
    	String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSmFuZSBEb2UiLCJlbWFpbCI6ImphbmVAZXhhbXBsZS5jb20iLCJzdWIiOiJqYW5lIiwianRpIjoiMDMzYjBkNGEtZDRkMy00NGQwLThhMGQtYTcwZjlmZmZiYzcwIiwiaWF0IjoxNjUxNTk4MTE5LCJleHAiOjE2NTE1OTg0MTl9.fq6IN218RrNgA2--qvyjOZ9nGUIytUJgIhwNCLmhI9o";
        ValidationResponse invalid = ValidationResponse.builder()
                .answer(97).question("Please sum the numbers 3,6,8").token(token).build();

        Assert.assertThrows(BadRequest.class,()->validationService.validate(invalid));
        log.info("------------checking invalid answer----------------");
        ValidationResponse valid = ValidationResponse.builder()
                .answer(17).question("Please sum the numbers 3,6,8").token(token).build();

        Assertions.assertThatCode(()->validationService.validate(valid))
                .doesNotThrowAnyException();
        log.info("------------checking valid answer----------------");
    }
}