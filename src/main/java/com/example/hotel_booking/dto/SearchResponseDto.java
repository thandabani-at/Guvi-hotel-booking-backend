package com.example.hotel_booking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResponseDto {

    private Long hotelId;
    private String hotelName;
    private String location;

    private Long roomId;
    private String roomType;
    private Double pricePerNight;
}