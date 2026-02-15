package com.example.hotel_booking.repository;

import com.example.hotel_booking.entity.Room;
import com.example.hotel_booking.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository
        extends JpaRepository<Room, Long> {

    List<Room> findByHotel(Hotel hotel);

    List<Room> findByHotelId(Long hotelId);


    List<Room> findByAvailableTrue();
}