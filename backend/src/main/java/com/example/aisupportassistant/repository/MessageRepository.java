package com.example.aisupportassistant.repository;

import com.example.aisupportassistant.model.MessageDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<MessageDocument, String> {

    Page<MessageDocument> findByConversationIdOrderByCreatedAtAsc(
            String conversationId,
            Pageable pageable);
}
