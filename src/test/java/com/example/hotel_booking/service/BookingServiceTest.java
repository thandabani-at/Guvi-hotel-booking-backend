package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.BookingRequestDto;
import com.example.hotel_booking.dto.BookingResponseDto;
import com.example.hotel_booking.entity.*;
import com.example.hotel_booking.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingService bookingService;

    private Room room;
    private User user;
    private Booking booking;

    @BeforeEach
    void setUp() {


        Authentication auth =
                mock(Authentication.class);

        when(auth.getName())
                .thenReturn("test@mail.com");

        SecurityContext context =
                mock(SecurityContext.class);

        when(context.getAuthentication())
                .thenReturn(auth);

        SecurityContextHolder.setContext(context);


        user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");


        Hotel hotel = new Hotel();
        hotel.setName("Taj Hotel");


        room = new Room();
        room.setId(101L);
        room.setRoomType("DELUXE");
        room.setPricePerNight(2500.0);
        room.setHotel(hotel);


        booking = Booking.builder()
                .id(1L)
                .room(room)
                .user(user)
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(2))
                .totalPrice(5000.0)
                .status(BookingStatus.CONFIRMED)
                .build();
    }


    @Test
    void createBookingTest() {

        BookingRequestDto dto =
                new BookingRequestDto();

        dto.setRoomId(101L);
        dto.setCheckInDate(LocalDate.now());
        dto.setCheckOutDate(LocalDate.now().plusDays(2));

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.of(user));

        when(roomRepository.findById(101L))
                .thenReturn(Optional.of(room));

        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(booking);



        Booking response =
                bookingService.createBooking(dto);



        assertNotNull(response);
        assertEquals(5000.0,
                response.getTotalPrice());
        assertEquals(BookingStatus.CONFIRMED,
                response.getStatus());

        verify(bookingRepository, times(1))
                .save(any(Booking.class));
    }

    @Test
    void createBooking_InvalidDate_Test() {

        BookingRequestDto dto =
                new BookingRequestDto();

        dto.setRoomId(101L);
        dto.setCheckInDate(LocalDate.now());
        dto.setCheckOutDate(LocalDate.now().minusDays(1));

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.of(user));

        when(roomRepository.findById(101L))
                .thenReturn(Optional.of(room));

        RuntimeException ex =
                assertThrows(RuntimeException.class,
                        () -> bookingService.createBooking(dto));

        assertEquals(
                "Check-out date must be after check-in date",
                ex.getMessage()
        );
    }
}