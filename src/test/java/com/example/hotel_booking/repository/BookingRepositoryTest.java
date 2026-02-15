package com.example.hotel_booking.repository;

import com.example.hotel_booking.entity.Booking;
import com.example.hotel_booking.entity.BookingStatus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.ANY)
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void saveBookingTest() {

        Booking b = Booking.builder()
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(1))
                .totalPrice(2000.0)
                .status(BookingStatus.CONFIRMED)
                .build();

        Booking saved = bookingRepository.save(b);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(
                BookingStatus.CONFIRMED,
                saved.getStatus());
    }
}