package com.example.aisupportassistant.service;

import com.example.aisupportassistant.dto.ConversationResponse;
import com.example.aisupportassistant.dto.CreateConversationRequest;
import com.example.aisupportassistant.dto.PageResponse;
import com.example.aisupportassistant.exception.InvalidRequestException;
import com.example.aisupportassistant.exception.ResourceNotFoundException;
import com.example.aisupportassistant.model.ConversationDocument;
import com.example.aisupportassistant.repository.ConversationRepository;
import com.example.aisupportassistant.repository.UserRepository;
import com.example.aisupportassistant.validation.MongoObjectId;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    private static final int MAX_PAGE_SIZE = 100;

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public ConversationService(
            ConversationRepository conversationRepository,
            UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public ConversationResponse createConversation(CreateConversationRequest request) {
        if (!userRepository.existsById(request.userId())) {
            throw new ResourceNotFoundException("User not found.");
        }

        Instant now = Instant.now();
        ConversationDocument saved = conversationRepository.save(
                new ConversationDocument(null, request.userId(), request.title().trim(), now, now));
        return toResponse(saved);
    }

    public ConversationResponse getConversation(String id) {
        MongoObjectId.validate(id, "conversationId");
        return conversationRepository.findById(id)
                .map(ConversationService::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found."));
    }

    public PageResponse<ConversationResponse> getConversations(
            String userId,
            int page,
            int size) {
        MongoObjectId.validate(userId, "userId");
        PageRequest pageRequest = pageRequest(page, size);
        Page<ConversationResponse> result = conversationRepository
                .findByUserIdOrderByUpdatedAtDesc(userId, pageRequest)
                .map(ConversationService::toResponse);
        return PageResponse.from(result);
    }

    static PageRequest pageRequest(int page, int size) {
        if (page < 0) {
            throw new InvalidRequestException("page must be zero or greater.");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new InvalidRequestException("size must be between 1 and " + MAX_PAGE_SIZE + ".");
        }
        return PageRequest.of(page, size);
    }

    private static ConversationResponse toResponse(ConversationDocument conversation) {
        return new ConversationResponse(
                conversation.id(),
                conversation.userId(),
                conversation.title(),
                conversation.createdAt(),
                conversation.updatedAt());
    }
}
