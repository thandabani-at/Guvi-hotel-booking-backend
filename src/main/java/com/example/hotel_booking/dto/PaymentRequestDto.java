package com.example.hotel_booking.dto;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private Long bookingId;
    private Double amount;
    private String paymentMethod; // RAZORPAY
}