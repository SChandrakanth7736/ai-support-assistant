package com.example.aisupportassistant.service;

import com.example.aisupportassistant.dto.CreateMessageRequest;
import com.example.aisupportassistant.dto.MessageResponse;
import com.example.aisupportassistant.dto.PageResponse;
import com.example.aisupportassistant.exception.ResourceNotFoundException;
import com.example.aisupportassistant.model.ConversationDocument;
import com.example.aisupportassistant.model.MessageDocument;
import com.example.aisupportassistant.repository.ConversationRepository;
import com.example.aisupportassistant.repository.MessageRepository;
import com.example.aisupportassistant.validation.MongoObjectId;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public MessageService(
            ConversationRepository conversationRepository,
            MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public MessageResponse createMessage(String conversationId, CreateMessageRequest request) {
        MongoObjectId.validate(conversationId, "conversationId");
        ConversationDocument conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found."));

        Instant now = Instant.now();
        MessageDocument saved = messageRepository.save(new MessageDocument(
                null,
                conversation.id(),
                request.role(),
                request.content().trim(),
                now));
        conversationRepository.updateUpdatedAtById(conversation.id(), now);
        return toResponse(saved);
    }

    public PageResponse<MessageResponse> getMessages(String conversationId, int page, int size) {
        MongoObjectId.validate(conversationId, "conversationId");
        if (!conversationRepository.existsById(conversationId)) {
            throw new ResourceNotFoundException("Conversation not found.");
        }
        PageRequest pageRequest = ConversationService.pageRequest(page, size);
        Page<MessageResponse> result = messageRepository
                .findByConversationIdOrderByCreatedAtAsc(conversationId, pageRequest)
                .map(MessageService::toResponse);
        return PageResponse.from(result);
    }

    public MessageResponse getMessage(String conversationId, String messageId) {
        MongoObjectId.validate(conversationId, "conversationId");
        MongoObjectId.validate(messageId, "messageId");
        if (!conversationRepository.existsById(conversationId)) {
            throw new ResourceNotFoundException("Conversation not found.");
        }
        MessageDocument message = messageRepository.findById(messageId)
                .filter(candidate -> candidate.conversationId().equals(conversationId))
                .orElseThrow(() -> new ResourceNotFoundException("Message not found."));
        return toResponse(message);
    }

    private static MessageResponse toResponse(MessageDocument message) {
        return new MessageResponse(
                message.id(),
                message.conversationId(),
                message.role(),
                message.content(),
                message.createdAt());
    }
}
