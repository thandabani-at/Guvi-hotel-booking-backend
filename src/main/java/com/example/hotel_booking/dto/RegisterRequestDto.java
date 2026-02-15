package com.example.hotel_booking.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {


    private String fullName;
    private String email;
    private String password;
    private String role;
}
