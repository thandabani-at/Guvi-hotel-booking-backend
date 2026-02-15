package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.PaymentRequestDto;
import com.example.hotel_booking.dto.PaymentResponseDto;
import com.example.hotel_booking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping
    public PaymentResponseDto pay(@RequestBody PaymentRequestDto dto) {
        return paymentService.makePayment(dto);
    }
}