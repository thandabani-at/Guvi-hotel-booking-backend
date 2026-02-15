package com.example.hotel_booking.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequestDto {

    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}