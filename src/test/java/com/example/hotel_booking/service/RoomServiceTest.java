package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.RoomRequestDto;
import com.example.hotel_booking.dto.RoomResponseDto;
import com.example.hotel_booking.entity.Hotel;
import com.example.hotel_booking.entity.Room;
import com.example.hotel_booking.repository.HotelRepository;
import com.example.hotel_booking.repository.RoomRepository;

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
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private RoomService roomService;

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
        room.setRoomNumber("101");
        room.setRoomType("DELUXE");
        room.setPricePerNight(2500.0);
        room.setAvailable(true);
        room.setHotel(hotel);
    }



    @Test
    void createRoomTest() {

        RoomRequestDto dto =
                new RoomRequestDto();

        dto.setRoomNumber("101");
        dto.setRoomType("DELUXE");
        dto.setPrice(2500.0);
        dto.setAvailable(true);
        dto.setHotelId(1L);

        when(hotelRepository.findById(1L))
                .thenReturn(Optional.of(hotel));

        String res =
                roomService.createRoom(dto);

        assertEquals(
                "Room created successfully", res);

        verify(roomRepository)
                .save(any(Room.class));
    }


    @Test
    void getRoomsByHotelTest() {

        when(roomRepository.findByHotelId(1L))
                .thenReturn(List.of(room));

        List<RoomResponseDto> list =
                roomService.getRoomsByHotel(1L);

        assertEquals(1, list.size());
        assertEquals("Taj Hotel",
                list.get(0).getHotelName());
    }


    @Test
    void updateRoomTest() {

        RoomRequestDto dto =
                new RoomRequestDto();

        dto.setRoomNumber("102");
        dto.setRoomType("AC");
        dto.setPrice(3000.0);
        dto.setAvailable(false);

        when(roomRepository.findById(101L))
                .thenReturn(Optional.of(room));

        String res =
                roomService.updateRoom(101L, dto);

        assertEquals(
                "Room updated successfully", res);

        verify(roomRepository).save(room);
    }

    @Test
    void deleteRoomTest() {

        String res =
                roomService.deleteRoom(101L);

        assertEquals(
                "Room deleted successfully", res);

        verify(roomRepository)
                .deleteById(101L);
    }


    @Test
    void availableRoomsTest() {

        when(roomRepository.findByAvailableTrue())
                .thenReturn(List.of(room));

        List<RoomResponseDto> list =
                roomService.getAvailableRooms();

        assertEquals(1, list.size());
        assertTrue(list.get(0).getAvailable());
    }
}