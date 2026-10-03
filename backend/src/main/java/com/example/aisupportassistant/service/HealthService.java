package com.example.aisupportassistant.service;

import com.example.aisupportassistant.dto.HealthResponse;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public HealthResponse getHealth() {
        return new HealthResponse("UP");
    }
}
