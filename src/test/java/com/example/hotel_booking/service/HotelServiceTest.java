package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.HotelRequestDto;
import com.example.hotel_booking.dto.HotelResponseDto;
import com.example.hotel_booking.entity.Hotel;
import com.example.hotel_booking.repository.HotelRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    private Hotel hotel;

    @BeforeEach
    void setUp() {

        hotel = Hotel.builder()
                .id(1L)
                .name("Taj Hotel")
                .location("Chennai")
                .description("Luxury Stay")
                .pricePerNight(5000.0)
                .rating(4.5)
                .build();
    }


    @Test
    void createHotelTest() {

        HotelRequestDto dto =
                new HotelRequestDto();

        dto.setName("Taj Hotel");
        dto.setLocation("Chennai");
        dto.setDescription("Luxury");
        dto.setPricePerNight(5000.0);
        dto.setRating(4.5);

        when(hotelRepository.save(any()))
                .thenReturn(hotel);

        String response =
                hotelService.createHotel(dto);

        assertEquals(
                "Hotel Created Successfully",
                response);
    }


    @Test
    void getAllHotelsTest() {

        when(hotelRepository.findAll())
                .thenReturn(List.of(hotel));

        List<HotelResponseDto> list =
                hotelService.getAllHotels();

        assertEquals(1, list.size());
        assertEquals("Taj Hotel",
                list.get(0).getName());
    }


    @Test
    void getHotelByIdTest() {

        when(hotelRepository.findById(1L))
                .thenReturn(Optional.of(hotel));

        HotelResponseDto dto =
                hotelService.getHotelById(1L);

        assertEquals("Taj Hotel",
                dto.getName());
    }


    @Test
    void updateHotelTest() {

        HotelRequestDto dto =
                new HotelRequestDto();

        dto.setName("Updated Hotel");
        dto.setLocation("Madurai");
        dto.setDescription("Updated Desc");
        dto.setPricePerNight(3000.0);
        dto.setRating(4.0);

        when(hotelRepository.findById(1L))
                .thenReturn(Optional.of(hotel));

        String response =
                hotelService.updateHotel(1L, dto);

        assertEquals(
                "Hotel Updated Successfully",
                response);
    }


    @Test
    void deleteHotelTest() {

        String response =
                hotelService.deleteHotel(1L);

        verify(hotelRepository)
                .deleteById(1L);

        assertEquals(
                "Hotel Deleted Successfully",
                response);
    }
}