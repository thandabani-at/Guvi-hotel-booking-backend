package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.BookingRequestDto;
import com.example.hotel_booking.entity.Booking;
import com.example.hotel_booking.entity.BookingStatus;
import com.example.hotel_booking.service.BookingService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createBookingTest() throws Exception {


        BookingRequestDto req = new BookingRequestDto();
        req.setRoomId(101L);
        req.setCheckInDate(LocalDate.now());
        req.setCheckOutDate(LocalDate.now().plusDays(2));


        Booking booking = Booking.builder()
                .id(1L)
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(2))
                .totalPrice(5000.0)
                .status(BookingStatus.CONFIRMED)
                .build();


        when(bookingService.createBooking(any()))
                .thenReturn(booking);


        mockMvc.perform(post("/api/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalPrice").value(5000.0))
                .andExpect(jsonPath("$.status")
                        .value("CONFIRMED"));
    }
}