package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.SearchResponseDto;
import com.example.hotel_booking.service.SearchService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @Test
    void searchApiTest() throws Exception {

        SearchResponseDto res =
                SearchResponseDto.builder()
                        .hotelId(1L)
                        .hotelName("Taj Hotel")
                        .location("Chennai")
                        .roomId(101L)
                        .roomType("DELUXE")
                        .pricePerNight(2500.0)
                        .build();

        when(searchService.searchAvailableRooms(
                "Chennai",
                java.time.LocalDate.parse("2026-02-20"),
                java.time.LocalDate.parse("2026-02-22")
        )).thenReturn(List.of(res));

        mockMvc.perform(
                        get("/api/search")
                                .param("location",
                                        "Chennai")
                                .param("checkIn",
                                        "2026-02-20")
                                .param("checkOut",
                                        "2026-02-22")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].hotelName")
                        .value("Taj Hotel"))
                .andExpect(jsonPath("$[0].roomType")
                        .value("DELUXE"));
    }
}