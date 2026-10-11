package com.example.aisupportassistant.controller;

import com.example.aisupportassistant.model.ConversationDocument;
import com.example.aisupportassistant.model.MessageDocument;
import com.example.aisupportassistant.model.MessageRole;
import com.example.aisupportassistant.model.UserDocument;
import com.example.aisupportassistant.repository.ConversationRepository;
import com.example.aisupportassistant.repository.MessageRepository;
import com.example.aisupportassistant.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersistenceApiTest {

    private static final String USER_ID = "64b000000000000000000001";
    private static final String CONVERSATION_ID = "64b000000000000000000002";
    private static final String MESSAGE_ID = "64b000000000000000000003";
    private static final Instant NOW = Instant.parse("2026-01-01T12:00:00Z");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private ConversationRepository conversationRepository;

    @MockitoBean
    private MessageRepository messageRepository;

    @Test
    void createsUserAndNormalizesEmail() throws Exception {
        when(userRepository.existsByEmail("learner@example.com")).thenReturn(false);
        when(userRepository.save(any(UserDocument.class)))
                .thenReturn(new UserDocument(USER_ID, "learner@example.com", NOW));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"Learner@Example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users/" + USER_ID))
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value("learner@example.com"));
    }

    @Test
    void rejectsInvalidUserEmail() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"not-an-email\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void rejectsMalformedUserId() throws Exception {
        mockMvc.perform(get("/api/users/not-an-object-id"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(
                        "userId must be a 24-character MongoDB ObjectId."));
    }

    @Test
    void createsConversationForExistingUser() throws Exception {
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        when(conversationRepository.save(any(ConversationDocument.class)))
                .thenReturn(new ConversationDocument(
                        CONVERSATION_ID, USER_ID, "Getting started", NOW, NOW));

        mockMvc.perform(post("/api/conversations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + USER_ID + "\",\"title\":\"Getting started\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/conversations/" + CONVERSATION_ID))
                .andExpect(jsonPath("$.userId").value(USER_ID))
                .andExpect(jsonPath("$.title").value("Getting started"));
    }

    @Test
    void rejectsConversationForUnknownUser() throws Exception {
        when(userRepository.existsById(USER_ID)).thenReturn(false);

        mockMvc.perform(post("/api/conversations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + USER_ID + "\",\"title\":\"Getting started\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found."));
    }

    @Test
    void listsConversationsWithPagination() throws Exception {
        ConversationDocument conversation =
                new ConversationDocument(CONVERSATION_ID, USER_ID, "Getting started", NOW, NOW);
        when(conversationRepository.findByUserIdOrderByUpdatedAtDesc(
                eq(USER_ID), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(conversation), PageRequest.of(0, 20), 1));

        mockMvc.perform(get("/api/conversations")
                        .param("userId", USER_ID)
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(CONVERSATION_ID))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void rejectsOversizedPageRequests() throws Exception {
        mockMvc.perform(get("/api/conversations")
                        .param("userId", USER_ID)
                        .param("size", "101"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("size must be between 1 and 100."));
    }

    @Test
    void persistsMessageAndUpdatesConversationActivity() throws Exception {
        when(conversationRepository.findById(CONVERSATION_ID))
                .thenReturn(java.util.Optional.of(
                        new ConversationDocument(CONVERSATION_ID, USER_ID, "Getting started", NOW, NOW)));
        when(messageRepository.save(any(MessageDocument.class)))
                .thenReturn(new MessageDocument(
                        MESSAGE_ID, CONVERSATION_ID, MessageRole.USER, "How does this work?", NOW));

        mockMvc.perform(post("/api/conversations/" + CONVERSATION_ID + "/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"role\":\"USER\",\"content\":\"How does this work?\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        "Location",
                        "/api/conversations/" + CONVERSATION_ID + "/messages/" + MESSAGE_ID))
                .andExpect(jsonPath("$.conversationId").value(CONVERSATION_ID))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.content").value("How does this work?"));

        verify(conversationRepository).updateUpdatedAtById(eq(CONVERSATION_ID), any(Instant.class));
    }

    @Test
    void rejectsMessageWithUnknownRole() throws Exception {
        mockMvc.perform(post("/api/conversations/" + CONVERSATION_ID + "/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"role\":\"VISITOR\",\"content\":\"Hello\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getsConversationById() throws Exception {
        when(conversationRepository.findById(CONVERSATION_ID))
                .thenReturn(java.util.Optional.of(
                        new ConversationDocument(CONVERSATION_ID, USER_ID, "Getting started", NOW, NOW)));

        mockMvc.perform(get("/api/conversations/" + CONVERSATION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CONVERSATION_ID))
                .andExpect(jsonPath("$.userId").value(USER_ID));
    }

    @Test
    void listsMessagesInConversationWithPagination() throws Exception {
        MessageDocument message = new MessageDocument(
                MESSAGE_ID, CONVERSATION_ID, MessageRole.USER, "How does this work?", NOW);
        when(conversationRepository.existsById(CONVERSATION_ID)).thenReturn(true);
        when(messageRepository.findByConversationIdOrderByCreatedAtAsc(
                eq(CONVERSATION_ID), eq(PageRequest.of(0, 50))))
                .thenReturn(new PageImpl<>(List.of(message), PageRequest.of(0, 50), 1));

        mockMvc.perform(get("/api/conversations/" + CONVERSATION_ID + "/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(MESSAGE_ID))
                .andExpect(jsonPath("$.content[0].conversationId").value(CONVERSATION_ID))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void getsMessageWithinItsConversation() throws Exception {
        when(conversationRepository.existsById(CONVERSATION_ID)).thenReturn(true);
        when(messageRepository.findById(MESSAGE_ID)).thenReturn(java.util.Optional.of(
                new MessageDocument(
                        MESSAGE_ID, CONVERSATION_ID, MessageRole.USER, "How does this work?", NOW)));

        mockMvc.perform(get(
                        "/api/conversations/" + CONVERSATION_ID + "/messages/" + MESSAGE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MESSAGE_ID))
                .andExpect(jsonPath("$.conversationId").value(CONVERSATION_ID));
    }
}
