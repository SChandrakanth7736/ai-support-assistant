package com.example.aisupportassistant.validation;

import com.example.aisupportassistant.exception.InvalidRequestException;
import java.util.regex.Pattern;

public final class MongoObjectId {

    private static final Pattern FORMAT = Pattern.compile("^[a-fA-F0-9]{24}$");

    private MongoObjectId() {
    }

    public static void validate(String id, String fieldName) {
        if (id == null || !FORMAT.matcher(id).matches()) {
            throw new InvalidRequestException(fieldName + " must be a 24-character MongoDB ObjectId.");
        }
    }
}
