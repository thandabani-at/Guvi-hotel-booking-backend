package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.PaymentRequestDto;
import com.example.hotel_booking.dto.PaymentResponseDto;
import com.example.hotel_booking.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void makePaymentTest() throws Exception {


        PaymentRequestDto req =
                new PaymentRequestDto();

        req.setBookingId(1L);
        req.setAmount(5000.0);
        req.setPaymentMethod("RAZORPAY");

        /* ---------- RESPONSE ---------- */
        PaymentResponseDto res =
                PaymentResponseDto.builder()
                        .paymentId(10L)
                        .bookingId(1L)
                        .amount(5000.0)
                        .paymentMethod("RAZORPAY")
                        .paymentStatus("SUCCESS")
                        .transactionId("TXN123")
                        .paymentDate(LocalDateTime.now())
                        .build();

        when(paymentService.makePayment(any()))
                .thenReturn(res);


        mockMvc.perform(post("/api/payments")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId")
                        .value(10))
                .andExpect(jsonPath("$.paymentStatus")
                        .value("SUCCESS"));
    }
}