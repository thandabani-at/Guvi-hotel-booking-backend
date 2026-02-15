package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.BookingRequestDto;
import com.example.hotel_booking.dto.BookingResponseDto;
import com.example.hotel_booking.entity.Booking;
import com.example.hotel_booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create")
    public Booking createBooking(
            @RequestBody BookingRequestDto dto) {
        return bookingService.createBooking(dto);
    }


    @GetMapping("/my")
    public List<BookingResponseDto> myBookings() {
        return bookingService.getMyBookings();
    }

    @GetMapping("/all")
    public List<BookingResponseDto> allBookings() {
        return bookingService.getAllBookings();
    }
    @PutMapping("/cancel/{id}")
    public String cancel(
            @PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }
}