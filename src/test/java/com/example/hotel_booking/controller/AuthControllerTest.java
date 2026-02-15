package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.*;
import com.example.hotel_booking.service.AuthService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void registerApiTest() throws Exception {

        RegisterRequestDto dto =
                new RegisterRequestDto();

        dto.setFullName("Test User");
        dto.setEmail("test@mail.com");
        dto.setPassword("1234");

        when(authService.register(dto))
                .thenReturn(
                        "Customer Registered Successfully"
                );

        mockMvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        mapper.writeValueAsString(dto)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Customer Registered Successfully"
                ));
    }



    @Test
    void loginApiTest() throws Exception {

        LoginRequestDto dto =
                new LoginRequestDto();

        dto.setEmail("test@mail.com");
        dto.setPassword("1234");

        AuthResponseDto response =
                new AuthResponseDto(
                        "mock-token",
                        "CUSTOMER",
                        1L,
                        "test@mail.com"
                );

        when(authService.login(dto))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        mapper.writeValueAsString(dto)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token")
                        .value("mock-token"))
                .andExpect(jsonPath("$.role")
                        .value("CUSTOMER"))
                .andExpect(jsonPath("$.userId")
                        .value(1L));
    }
}