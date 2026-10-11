package com.example.aisupportassistant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateConversationRequest(
        @NotBlank
        @Pattern(regexp = "^[a-fA-F0-9]{24}$", message = "must be a 24-character MongoDB ObjectId")
        String userId,
        @NotBlank @Size(max = 120) String title) {
}
