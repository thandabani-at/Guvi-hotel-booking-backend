package com.example.hotel_booking.dto;

import lombok.Data;

@Data
public class RoomRequestDto {

    private String roomNumber;
    private String roomType;
    private Double price;
    private Boolean available;
    private Long hotelId;
}