package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.*;
import com.example.hotel_booking.entity.Role;
import com.example.hotel_booking.entity.User;
import com.example.hotel_booking.repository.UserRepository;
import com.example.hotel_booking.config.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(1L)
                .fullName("Test User")
                .email("test@mail.com")
                .password("encodedPass")
                .role(Role.CUSTOMER)
                .build();
    }



    @Test
    void registerTest() {

        RegisterRequestDto dto =
                new RegisterRequestDto();

        dto.setFullName("Test User");
        dto.setEmail("test@mail.com");
        dto.setPassword("1234");

        when(passwordEncoder.encode("1234"))
                .thenReturn("encodedPass");

        String response =
                authService.register(dto);

        assertEquals(
                "Customer Registered Successfully",
                response
        );

        verify(userRepository, times(1))
                .save(any(User.class));
    }


    @Test
    void loginTest() {

        LoginRequestDto dto =
                new LoginRequestDto();

        dto.setEmail("test@mail.com");
        dto.setPassword("1234");

        when(userRepository
                .findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));

        when(jwtUtil.generateToken(
                "test@mail.com"))
                .thenReturn("mock-token");

        AuthResponseDto response =
                authService.login(dto);

        assertNotNull(response);
        assertEquals("mock-token",
                response.getToken());
        assertEquals("CUSTOMER",
                response.getRole());
        assertEquals(1L,
                response.getUserId());
    }



    @Test
    void initAdminTest() {

        when(userRepository
                .findByEmail("admin@gmail.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("admin123"))
                .thenReturn("encodedAdminPass");

        authService.initAdmin();

        verify(userRepository)
                .save(any(User.class));
    }
}