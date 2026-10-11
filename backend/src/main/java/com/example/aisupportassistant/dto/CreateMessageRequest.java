package com.example.aisupportassistant.dto;

import com.example.aisupportassistant.model.MessageRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateMessageRequest(
        @NotNull MessageRole role,
        @NotBlank @Size(max = 10000) String content) {
}
