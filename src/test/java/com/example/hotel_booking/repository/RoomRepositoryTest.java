package com.example.hotel_booking.repository;

import com.example.hotel_booking.entity.Hotel;
import com.example.hotel_booking.entity.Room;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void findAvailableRoomsTest() {


        Hotel hotel = Hotel.builder()
                .name("Taj Hotel")
                .location("Chennai")
                .description("Luxury")
                .pricePerNight(5000.0)
                .rating(4.5)
                .build();

        hotelRepository.save(hotel);


        Room r1 = new Room();
        r1.setRoomNumber("101");
        r1.setRoomType("DELUXE");
        r1.setPricePerNight(2500.0);
        r1.setAvailable(true);
        r1.setHotel(hotel);

        Room r2 = new Room();
        r2.setRoomNumber("102");
        r2.setRoomType("AC");
        r2.setPricePerNight(2000.0);
        r2.setAvailable(false);
        r2.setHotel(hotel);

        roomRepository.saveAll(List.of(r1, r2));

        /* ---------- TEST ---------- */
        List<Room> available =
                roomRepository.findByAvailableTrue();

        assertEquals(1, available.size());
        assertEquals("101",
                available.get(0).getRoomNumber());
    }
}