package com.example.aisupportassistant.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document("messages")
@CompoundIndex(name = "conversation_created_at_idx", def = "{'conversationId': 1, 'createdAt': 1}")
public record MessageDocument(
        @Id String id,
        @Field(targetType = FieldType.OBJECT_ID)
        String conversationId,
        MessageRole role,
        String content,
        Instant createdAt) {
}
