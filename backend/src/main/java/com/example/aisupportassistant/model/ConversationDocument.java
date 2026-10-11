package com.example.aisupportassistant.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("conversations")
@CompoundIndex(name = "user_updated_at_idx", def = "{'userId': 1, 'updatedAt': -1}")
public record ConversationDocument(
        @Id String id,
        @Field(targetType = FieldType.OBJECT_ID)
        String userId,
        String title,
        Instant createdAt,
        Instant updatedAt) {
}
