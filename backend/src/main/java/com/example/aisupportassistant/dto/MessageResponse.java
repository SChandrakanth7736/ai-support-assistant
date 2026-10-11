package com.example.aisupportassistant.dto;

import com.example.aisupportassistant.model.MessageRole;
import java.time.Instant;

public record MessageResponse(
        String id,
        String conversationId,
        MessageRole role,
        String content,
        Instant createdAt) {
}
