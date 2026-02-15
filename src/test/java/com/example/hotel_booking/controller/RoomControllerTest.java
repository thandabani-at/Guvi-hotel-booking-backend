package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.RoomRequestDto;
import com.example.hotel_booking.dto.RoomResponseDto;
import com.example.hotel_booking.service.RoomService;
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

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createRoomTest() throws Exception {

        when(roomService.createRoom(any()))
                .thenReturn("Room created successfully");

        RoomRequestDto dto =
                new RoomRequestDto();

        dto.setRoomNumber("101");
        dto.setRoomType("DELUXE");
        dto.setPrice(2500.0);
        dto.setAvailable(true);
        dto.setHotelId(1L);

        mockMvc.perform(post("/api/rooms/create")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(
                                "Room created successfully"));
    }

    @Test
    void getRoomsByHotelTest() throws Exception {

        RoomResponseDto res =
                RoomResponseDto.builder()
                        .id(101L)
                        .roomNumber("101")
                        .roomType("DELUXE")
                        .price(2500.0)
                        .available(true)
                        .hotelName("Taj Hotel")
                        .build();

        when(roomService.getRoomsByHotel(1L))
                .thenReturn(List.of(res));

        mockMvc.perform(
                        get("/api/rooms/hotel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].hotelName")
                        .value("Taj Hotel"));
    }

    @Test
    void availableRoomsTest() throws Exception {

        RoomResponseDto res =
                RoomResponseDto.builder()
                        .id(101L)
                        .roomNumber("101")
                        .roomType("DELUXE")
                        .price(2500.0)
                        .available(true)
                        .hotelName("Taj Hotel")
                        .build();

        when(roomService.getAvailableRooms())
                .thenReturn(List.of(res));

        mockMvc.perform(
                        get("/api/rooms/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].available")
                        .value(true));
    }
}