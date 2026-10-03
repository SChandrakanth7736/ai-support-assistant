package com.example.aisupportassistant.controller;

import com.example.aisupportassistant.dto.HealthResponse;
import com.example.aisupportassistant.dto.QuestionResponse;
import com.example.aisupportassistant.service.HealthService;
import com.example.aisupportassistant.service.QuestionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final HealthService healthService;
    private final QuestionService questionService;

    public ApiController(HealthService healthService, QuestionService questionService) {
        this.healthService = healthService;
        this.questionService = questionService;
    }

    @GetMapping("/health")
    public HealthResponse getHealth() {
        return healthService.getHealth();
    }

    @GetMapping("/questions")
    public List<QuestionResponse> getQuestions() {
        return questionService.getQuestions();
    }
}
