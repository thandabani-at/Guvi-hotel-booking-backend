package com.example.hotel_booking.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {

    private String token;
    private String role;
    private Long userId;
    private String email;
}