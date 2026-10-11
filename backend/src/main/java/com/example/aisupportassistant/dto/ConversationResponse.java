package com.example.aisupportassistant.dto;

import java.time.Instant;

public record ConversationResponse(
        String id,
        String userId,
        String title,
        Instant createdAt,
        Instant updatedAt) {
}
