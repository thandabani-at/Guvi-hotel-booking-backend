package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.SearchResponseDto;
import com.example.hotel_booking.entity.*;
import com.example.hotel_booking.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private SearchService searchService;

    private Hotel hotel;
    private Room room;

    @BeforeEach
    void setUp() {

        hotel = Hotel.builder()
                .id(1L)
                .name("Taj Hotel")
                .location("Chennai")
                .description("Luxury")
                .pricePerNight(5000.0)
                .rating(4.5)
                .build();


        room = new Room();
        room.setId(101L);
        room.setRoomType("DELUXE");
        room.setPricePerNight(2500.0);
        room.setHotel(hotel);
    }


    @Test
    void searchAvailableRoomsTest() {

        LocalDate checkIn =
                LocalDate.now();

        LocalDate checkOut =
                LocalDate.now().plusDays(2);



        when(hotelRepository
                .findByLocationContainingIgnoreCase(
                        "Chennai"))
                .thenReturn(List.of(hotel));

        when(roomRepository.findByHotel(hotel))
                .thenReturn(List.of(room));


        when(bookingRepository
                .findByRoomAndCheckOutDateAfterAndCheckInDateBefore(
                        eq(room),
                        eq(checkIn),
                        eq(checkOut)))
                .thenReturn(List.of());



        List<SearchResponseDto> result =
                searchService.searchAvailableRooms(
                        "Chennai",
                        checkIn,
                        checkOut
                );


        assertEquals(1, result.size());

        SearchResponseDto res =
                result.get(0);

        assertEquals("Taj Hotel",
                res.getHotelName());

        assertEquals("DELUXE",
                res.getRoomType());

        assertEquals(2500.0,
                res.getPricePerNight());
    }

    @Test
    void searchUnavailableRoomTest() {

        LocalDate checkIn =
                LocalDate.now();

        LocalDate checkOut =
                LocalDate.now().plusDays(2);

        Booking booking =
                new Booking();

        when(hotelRepository
                .findByLocationContainingIgnoreCase(
                        "Chennai"))
                .thenReturn(List.of(hotel));

        when(roomRepository.findByHotel(hotel))
                .thenReturn(List.of(room));


        when(bookingRepository
                .findByRoomAndCheckOutDateAfterAndCheckInDateBefore(
                        eq(room),
                        eq(checkIn),
                        eq(checkOut)))
                .thenReturn(List.of(booking));

        List<SearchResponseDto> result =
                searchService.searchAvailableRooms(
                        "Chennai",
                        checkIn,
                        checkOut
                );

        assertTrue(result.isEmpty());
    }
}