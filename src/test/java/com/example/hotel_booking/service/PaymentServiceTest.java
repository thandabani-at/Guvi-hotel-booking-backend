package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.PaymentRequestDto;
import com.example.hotel_booking.dto.PaymentResponseDto;
import com.example.hotel_booking.entity.Booking;
import com.example.hotel_booking.entity.BookingStatus;
import com.example.hotel_booking.entity.Payment;
import com.example.hotel_booking.repository.BookingRepository;
import com.example.hotel_booking.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Booking booking;

    @BeforeEach
    void setUp() {

        booking = new Booking();
        booking.setId(1L);
        booking.setStatus(BookingStatus.PENDING);
    }


    @Test
    void makePaymentTest() {


        PaymentRequestDto dto =
                new PaymentRequestDto();

        dto.setBookingId(1L);
        dto.setAmount(5000.0);
        dto.setPaymentMethod("RAZORPAY");


        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking));

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocation -> {

                    Payment p =
                            invocation.getArgument(0);

                    p.setId(10L);
                    p.setPaymentDate(
                            LocalDateTime.now());

                    return p;
                });

        PaymentResponseDto res =
                paymentService.makePayment(dto);


        assertNotNull(res);
        assertEquals(1L,
                res.getBookingId());
        assertEquals(5000.0,
                res.getAmount());
        assertEquals("SUCCESS",
                res.getPaymentStatus());


        verify(paymentRepository)
                .save(any(Payment.class));

        verify(bookingRepository)
                .save(any(Booking.class));
    }
}