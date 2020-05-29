package com.faq.faq.controller;

import com.faq.faq.Usecases.PopulateFaqUseCase;
import com.faq.faq.models.FAQCategoryEntity;
import com.faq.faq.models.FAQEntity;
import com.faq.faq.persistence.response.FAQResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FaqController {

    private PopulateFaqUseCase populateFaqUseCase;

    @Autowired
    public FaqController(PopulateFaqUseCase populateFaqUseCase) {
        this.populateFaqUseCase = populateFaqUseCase;
    }

    @GetMapping(value = "/security-questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public FAQResponse getSecurityQuestionList() {
           return populateFaqUseCase.getFAQByCategory("01");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FAQResponse> getAllFaqs() {
        return populateFaqUseCase.getAllFAQs();
    }
}
