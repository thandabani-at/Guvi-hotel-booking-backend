package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.*;
import com.example.hotel_booking.repository.UserRepository;
import com.example.hotel_booking.config.JwtUtil;
import com.example.hotel_booking.entity.Role;
import com.example.hotel_booking.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    public String register(RegisterRequestDto dto) {

        User user = User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);

        return "Customer Registered Successfully";
    }


    public AuthResponseDto login(LoginRequestDto dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        User user = userRepository
                .findByEmail(dto.getEmail())
                .orElseThrow();

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponseDto(
                token,
                user.getRole().name(),
                user.getId(),
                user.getEmail()
        );
    }


    @PostConstruct
    public void initAdmin(){

        if(userRepository.findByEmail("admin@gmail.com").isEmpty()){

            User admin = User.builder()
                    .fullName("Admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
        }
    }
}