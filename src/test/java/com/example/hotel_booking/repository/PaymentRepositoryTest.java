package com.example.hotel_booking.repository;

import com.example.hotel_booking.entity.Booking;
import com.example.hotel_booking.entity.Payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void savePaymentTest() {

        Booking booking = new Booking();
        booking.setId(1L);

        Payment payment = Payment.builder()
                .amount(5000.0)
                .paymentMethod("RAZORPAY")
                .paymentStatus("SUCCESS")
                .transactionId("TXN123")
                .paymentDate(LocalDateTime.now())
                .booking(booking)
                .build();

        Payment saved =
                paymentRepository.save(payment);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("SUCCESS",
                saved.getPaymentStatus());
    }
}