package com.example.aisupportassistant.service;

import com.example.aisupportassistant.dto.CreateUserRequest;
import com.example.aisupportassistant.dto.UserResponse;
import com.example.aisupportassistant.exception.DuplicateResourceException;
import com.example.aisupportassistant.exception.ResourceNotFoundException;
import com.example.aisupportassistant.model.UserDocument;
import com.example.aisupportassistant.repository.UserRepository;
import com.example.aisupportassistant.validation.MongoObjectId;
import java.time.Instant;
import java.util.Locale;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("A user with this email already exists.");
        }

        try {
            return toResponse(userRepository.save(new UserDocument(null, email, Instant.now())));
        } catch (DuplicateKeyException exception) {
            throw new DuplicateResourceException("A user with this email already exists.");
        }
    }

    public UserResponse getUser(String id) {
        MongoObjectId.validate(id, "userId");
        return userRepository.findById(id)
                .map(UserService::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private static UserResponse toResponse(UserDocument user) {
        return new UserResponse(user.id(), user.email(), user.createdAt());
    }
}
