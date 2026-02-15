package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.HotelRequestDto;
import com.example.hotel_booking.dto.HotelResponseDto;
import com.example.hotel_booking.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createHotelTest() throws Exception {

        HotelRequestDto dto =
                new HotelRequestDto();

        dto.setName("Taj Hotel");
        dto.setLocation("Chennai");
        dto.setDescription("Luxury");
        dto.setPricePerNight(5000.0);
        dto.setRating(4.5);

        when(hotelService.createHotel(any()))
                .thenReturn(
                        "Hotel Created Successfully");

        mockMvc.perform(post("/api/hotels/create")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(
                                "Hotel Created Successfully"));
    }


    @Test
    void getAllHotelsTest() throws Exception {

        HotelResponseDto res =
                HotelResponseDto.builder()
                        .id(1L)
                        .name("Taj")
                        .location("Chennai")
                        .description("Luxury")
                        .pricePerNight(5000.0)
                        .rating(4.5)
                        .build();

        when(hotelService.getAllHotels())
                .thenReturn(List.of(res));

        mockMvc.perform(get("/api/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Taj"));
    }


    @Test
    void getByIdTest() throws Exception {

        HotelResponseDto res =
                HotelResponseDto.builder()
                        .id(1L)
                        .name("Taj")
                        .location("Chennai")
                        .description("Luxury")
                        .pricePerNight(5000.0)
                        .rating(4.5)
                        .build();

        when(hotelService.getHotelById(1L))
                .thenReturn(res);

        mockMvc.perform(get("/api/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Taj"));
    }
}