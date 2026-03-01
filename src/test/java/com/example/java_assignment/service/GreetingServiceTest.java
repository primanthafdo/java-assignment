package com.example.java_assignment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GreetingServiceTest {

    private GreetingService greetingService;

    @BeforeEach
    void setUp() {
        greetingService = new GreetingService();
    }

    @ParameterizedTest(name = "getGreeting(\"{0}\") → INFO_MESSAGE")
    @ValueSource(strings = {"alice", "Alice", "ALICE", "bob", "Charlie", "mike", "Mike", "a", "A", "m", "M"})
    @DisplayName("Names starting with A–M should return hello message")
    void test_namesAtoM_returnInfoMessage(String name) {
        String result = greetingService.getGreeting(name);
        assertEquals(GreetingService.INFO_MESSAGE_HELLO_ALICE, result);
    }

    @ParameterizedTest(name = "getGreeting(\"{0}\") → IllegalArgumentException")
    @ValueSource(strings = {"nancy", "Nancy", "ZACH", "oscar", "zeus", "n", "N", "z", "Z"})
    @DisplayName("Names starting with N–Z should throw IllegalArgumentException")
    void test_namesNtoZ_throwException(String name) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> greetingService.getGreeting(name));
        assertEquals(GreetingService.ERROR_MESSAGE_INVALID_INPUT, ex.getMessage());
    }

    @Test
    @DisplayName("Null name should throw IllegalArgumentException")
    void test_nullName_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> greetingService.getGreeting(null));
        assertEquals(GreetingService.ERROR_MESSAGE_INVALID_INPUT, ex.getMessage());
    }

    @Test
    @DisplayName("Empty name should throw IllegalArgumentException")
    void test_emptyName_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> greetingService.getGreeting(""));
        assertEquals(GreetingService.ERROR_MESSAGE_INVALID_INPUT, ex.getMessage());
    }

    @Test
    @DisplayName("Blank (whitespace-only) name should throw IllegalArgumentException")
    void test_blankName_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> greetingService.getGreeting("   "));
        assertEquals(GreetingService.ERROR_MESSAGE_INVALID_INPUT, ex.getMessage());
    }

    @Test
    @DisplayName("Name starting with digit should throw IllegalArgumentException")
    void test_nameStartingWithDigit_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> greetingService.getGreeting("1alice"));
        assertEquals(GreetingService.ERROR_MESSAGE_INVALID_INPUT, ex.getMessage());
    }

    @Test
    @DisplayName("Name starting with special character should throw IllegalArgumentException")
    void test_nameStartingWithSpecialChar_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> greetingService.getGreeting("@lice"));
        assertEquals(GreetingService.ERROR_MESSAGE_INVALID_INPUT, ex.getMessage());
    }

    @Test
    @DisplayName("Mixed-case name starting with A–M should return hello message")
    void test_mixedCaseName_returnsInfoMessage() {
        assertEquals(GreetingService.INFO_MESSAGE_HELLO_ALICE, greetingService.getGreeting("aLICE"));
    }

    @Test
    @DisplayName("Single letter 'a' should return hello message")
    void test_singleLetterA_returnsInfoMessage() {
        assertEquals(GreetingService.INFO_MESSAGE_HELLO_ALICE, greetingService.getGreeting("a"));
    }

    @Test
    @DisplayName("Single letter 'n' should throw IllegalArgumentException")
    void test_singleLetterN_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> greetingService.getGreeting("n"));
    }
}