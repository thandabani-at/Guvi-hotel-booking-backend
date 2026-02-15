package com.example.hotel_booking.controller;


import com.example.hotel_booking.dto.AuthResponseDto;
import com.example.hotel_booking.dto.LoginRequestDto;
import com.example.hotel_booking.dto.RegisterRequestDto;
import com.example.hotel_booking.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(
            @RequestBody LoginRequestDto dto) {

        return authService.login(dto);
    }
}