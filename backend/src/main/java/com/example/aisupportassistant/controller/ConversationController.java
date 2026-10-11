package com.example.aisupportassistant.controller;

import com.example.aisupportassistant.dto.ConversationResponse;
import com.example.aisupportassistant.dto.CreateConversationRequest;
import com.example.aisupportassistant.dto.CreateMessageRequest;
import com.example.aisupportassistant.dto.MessageResponse;
import com.example.aisupportassistant.dto.PageResponse;
import com.example.aisupportassistant.service.ConversationService;
import com.example.aisupportassistant.service.MessageService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    private final MessageService messageService;

    public ConversationController(
            ConversationService conversationService,
            MessageService messageService) {
        this.conversationService = conversationService;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ConversationResponse> createConversation(
            @Valid @RequestBody CreateConversationRequest request) {
        ConversationResponse conversation = conversationService.createConversation(request);
        return ResponseEntity.created(
                URI.create("/api/conversations/" + conversation.id())).body(conversation);
    }

    @GetMapping("/{id}")
    public ConversationResponse getConversation(@PathVariable String id) {
        return conversationService.getConversation(id);
    }

    @GetMapping
    public PageResponse<ConversationResponse> getConversations(
            @RequestParam String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return conversationService.getConversations(userId, page, size);
    }

    @PostMapping("/{conversationId}/messages")
    public ResponseEntity<MessageResponse> createMessage(
            @PathVariable String conversationId,
            @Valid @RequestBody CreateMessageRequest request) {
        MessageResponse message = messageService.createMessage(conversationId, request);
        return ResponseEntity.created(URI.create(
                "/api/conversations/" + conversationId + "/messages/" + message.id())).body(message);
    }

    @GetMapping("/{conversationId}/messages")
    public PageResponse<MessageResponse> getMessages(
            @PathVariable String conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return messageService.getMessages(conversationId, page, size);
    }

    @GetMapping("/{conversationId}/messages/{messageId}")
    public MessageResponse getMessage(
            @PathVariable String conversationId,
            @PathVariable String messageId) {
        return messageService.getMessage(conversationId, messageId);
    }
}
