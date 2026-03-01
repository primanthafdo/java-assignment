package com.example.java_assignment.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public final static String INFO_MESSAGE_HELLO_ALICE = "Hello Alice";
    public final static String ERROR_MESSAGE_INVALID_INPUT = "Invalid Input";
    private static final char ALPHABET_MIDPOINT = 'n';

    public String getGreeting(String name) {
        if (isNullOrBlank(name)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_INPUT);
        }
        char firstLetter = Character.toLowerCase(name.charAt(0));
        if (!Character.isLetter(firstLetter)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_INPUT);
        }
        if (firstLetter < ALPHABET_MIDPOINT) {
            return INFO_MESSAGE_HELLO_ALICE;
        }
        throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_INPUT);
    }

    private static boolean isNullOrBlank(String value) {
        return value == null || value.trim().isBlank();
    }
}
