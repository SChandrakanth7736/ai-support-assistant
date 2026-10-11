package com.example.aisupportassistant.repository;

import com.example.aisupportassistant.model.ConversationDocument;
import java.time.Instant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConversationRepository extends MongoRepository<ConversationDocument, String> {

    Page<ConversationDocument> findByUserIdOrderByUpdatedAtDesc(String userId, Pageable pageable);

    @Query("{ '_id': ?0 }")
    @Update("{ '$set': { 'updatedAt': ?1 } }")
    long updateUpdatedAtById(String id, Instant updatedAt);
}
