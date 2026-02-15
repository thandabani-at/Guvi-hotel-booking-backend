package com.example.hotel_booking.dto;


import lombok.Data;

@Data
public class HotelRequestDto {

    private String name;
    private String location;
    private String description;
    private Double pricePerNight;
    private Double rating;
}