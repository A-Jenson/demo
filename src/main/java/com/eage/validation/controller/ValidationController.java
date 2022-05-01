package com.eage.validation.controller;

import com.eage.validation.request.ValidationRequest;
import com.eage.validation.response.ValidationResponse;
import com.eage.validation.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController {

    @Autowired
    private ValidationService validationService;

    @PostMapping("/validate")
    public void doValidate(@RequestBody ValidationResponse request) throws Exception {
        validationService.validate(request);
    }

    @GetMapping("/request")
    public ValidationRequest getQuestion() throws Exception {
        return validationService.getQuestion();
    }
}
