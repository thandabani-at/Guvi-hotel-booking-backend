package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.AdminDashboardDto;
import com.example.hotel_booking.entity.Payment;
import com.example.hotel_booking.repository.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminDashboardServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminDashboardService adminDashboardService;

    @Test
    void getDashboardStatsTest() {


        when(userRepository.count())
                .thenReturn(10L);

        when(hotelRepository.count())
                .thenReturn(5L);

        when(bookingRepository.count())
                .thenReturn(20L);

        when(paymentRepository.count())
                .thenReturn(15L);


        Payment p1 = Payment.builder()
                .amount(5000.0)
                .paymentStatus("SUCCESS")
                .build();

        Payment p2 = Payment.builder()
                .amount(3000.0)
                .paymentStatus("SUCCESS")
                .build();

        Payment p3 = Payment.builder()
                .amount(2000.0)
                .paymentStatus("FAILED")
                .build();

        when(paymentRepository.findAll())
                .thenReturn(List.of(p1, p2, p3));



        AdminDashboardDto dto =
                adminDashboardService
                        .getDashboardStats();



        assertNotNull(dto);

        assertEquals(10L,
                dto.getTotalUsers());

        assertEquals(5L,
                dto.getTotalHotels());

        assertEquals(20L,
                dto.getTotalBookings());

        assertEquals(15L,
                dto.getTotalPayments());

        // Only SUCCESS revenue counted
        assertEquals(8000.0,
                dto.getTotalRevenue());
    }
}