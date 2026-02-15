package com.example.hotel_booking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelResponseDto {

    private Long id;
    private String name;
    private String location;
    private String description;
    private Double pricePerNight;
    private Double rating;
}