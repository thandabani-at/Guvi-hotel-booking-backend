package com.example.hotel_booking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomResponseDto {

    private Long id;
    private String roomNumber;
    private String roomType;
    private Double price;
    private Boolean available;
    private String hotelName;
}