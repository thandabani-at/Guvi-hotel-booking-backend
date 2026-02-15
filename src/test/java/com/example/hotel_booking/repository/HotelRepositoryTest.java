package com.example.hotel_booking.repository;

import com.example.hotel_booking.entity.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.ANY)
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void saveHotelTest() {

        Hotel hotel = Hotel.builder()
                .name("Taj Hotel")
                .location("Chennai")
                .description("5 Star Hotel")
                .pricePerNight(5000.0)
                .rating(4.5)
                .build();

        Hotel saved =
                hotelRepository.save(hotel);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Taj Hotel",
                saved.getName());
    }

    @Test
    void searchByLocationTest() {

        Hotel h1 = Hotel.builder()
                .name("Taj")
                .location("Chennai")
                .description("Luxury")
                .pricePerNight(4000.0)
                .rating(4.2)
                .build();

        hotelRepository.save(h1);

        List<Hotel> list =
                hotelRepository
                        .findByLocationContainingIgnoreCase(
                                "chen");

        assertFalse(list.isEmpty());
        assertEquals("Chennai",
                list.get(0).getLocation());
    }
}