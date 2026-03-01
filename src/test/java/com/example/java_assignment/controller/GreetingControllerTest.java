package com.example.java_assignment.controller;

import com.example.java_assignment.service.GreetingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GreetingController.class)
public class GreetingControllerTest {

    private final static String FINAL_URL = "/api/assignment/hello-world";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GreetingService greetingService;

    @ParameterizedTest(name = "name=\"{0}\" → 200 Hello")
    @ValueSource(strings = {"alice", "Alice", "ALICE", "bob", "Charlie", "mike", "Mike", "a", "A", "m", "M"})
    @DisplayName("Names starting with A–M should return 200 with greeting")
    void Test_A_namesInFirstHalf_return200(String name) throws Exception {
        Mockito.when(greetingService.getGreeting(name)).thenReturn(GreetingService.INFO_MESSAGE_HELLO_ALICE);
        mockMvc.perform(get(FINAL_URL).param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(GreetingService.INFO_MESSAGE_HELLO_ALICE));
    }

    @ParameterizedTest(name = "name=\"{0}\" → 400 Invalid Input")
    @ValueSource(strings = {"nancy", "Nancy", "ZACH", "oscar", "zeus", "n", "N", "z", "Z"})
    @DisplayName("Names starting with N–Z should return 400")
    void Test_B_namesInSecondHalf_return400(String name) throws Exception {
        Mockito.when(greetingService.getGreeting(name)).thenThrow(new IllegalArgumentException(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
        mockMvc.perform(get(FINAL_URL).param("name", name))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
    }

    @Test
    @DisplayName("Missing name parameter should return 400")
    void Test_C_missingName_returns400() throws Exception {
        Mockito.when(greetingService.getGreeting(null)).thenThrow(new IllegalArgumentException(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
        mockMvc.perform(get(FINAL_URL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
    }

    @Test
    @DisplayName("Empty name parameter should return 400")
    void Test_D_emptyName_returns400() throws Exception {
        Mockito.when(greetingService.getGreeting("")).thenThrow(new IllegalArgumentException(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
        mockMvc.perform(get(FINAL_URL).param("name", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
    }

    @Test
    @DisplayName("Blank (whitespace-only) name should return 400")
    void Test_E_blankName_returns400() throws Exception {
        Mockito.when(greetingService.getGreeting("   ")).thenThrow(new IllegalArgumentException(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
        mockMvc.perform(get(FINAL_URL).param("name", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
    }

    @Test
    @DisplayName("Name starting with a digit should return 400")
    void Test_F_nameStartingWithDigit_returns400() throws Exception {
        Mockito.when(greetingService.getGreeting("1alice")).thenThrow(new IllegalArgumentException(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
        ;
        mockMvc.perform(get(FINAL_URL).param("name", "1alice"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
    }

    @Test
    @DisplayName("Name starting with special character should return 400")
    void Test_G_nameStartingWithSpecialChar_returns400() throws Exception {
        Mockito.when(greetingService.getGreeting("@lice")).thenThrow(new IllegalArgumentException(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
        mockMvc.perform(get(FINAL_URL).param("name", "@lice"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
    }

    @Test
    @DisplayName("Mixed-case name 'aLICE' should be normalized to 'Alice'")
    void Test_H_mixedCaseName_isNormalized() throws Exception {
        Mockito.when(greetingService.getGreeting("aLICE")).thenReturn(GreetingService.INFO_MESSAGE_HELLO_ALICE);
        mockMvc.perform(get(FINAL_URL).param("name", "aLICE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(GreetingService.INFO_MESSAGE_HELLO_ALICE));
    }

    @Test
    @DisplayName("Response should not contain 'error' key on success")
    void Test_I_successResponse_hasNoErrorKey() throws Exception {
        Mockito.when(greetingService.getGreeting("alice")).thenReturn(GreetingService.INFO_MESSAGE_HELLO_ALICE);
        mockMvc.perform(get(FINAL_URL).param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    @DisplayName("Error response should not contain 'message' key")
    void Test_J_errorResponse_hasNoMessageKey() throws Exception {
        Mockito.when(greetingService.getGreeting("zara")).thenThrow(new IllegalArgumentException(GreetingService.ERROR_MESSAGE_INVALID_INPUT));
        mockMvc.perform(get(FINAL_URL).param("name", "zara"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").doesNotExist());
    }
}
