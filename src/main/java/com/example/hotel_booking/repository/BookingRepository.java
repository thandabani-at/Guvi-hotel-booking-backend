package com.example.hotel_booking.repository;

import com.example.hotel_booking.entity.Booking;
import com.example.hotel_booking.entity.Room;
import com.example.hotel_booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {


    List<Booking> findByUser(User user);


    List<Booking> findByRoom(Room room);


    List<Booking>
    findByRoomAndCheckOutDateAfterAndCheckInDateBefore(
            Room room,
            LocalDate checkIn,
            LocalDate checkOut
    );
}