package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.PaymentRequestDto;
import com.example.hotel_booking.dto.PaymentResponseDto;
import com.example.hotel_booking.entity.Booking;
import com.example.hotel_booking.entity.BookingStatus;
import com.example.hotel_booking.entity.Payment;
import com.example.hotel_booking.repository.BookingRepository;
import com.example.hotel_booking.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentResponseDto makePayment(PaymentRequestDto dto) {


        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));


        boolean paymentSuccess = simulatePayment(dto.getAmount());


        Payment payment = Payment.builder()
                .amount(dto.getAmount())
                .paymentMethod(dto.getPaymentMethod())
                .paymentStatus(paymentSuccess ? "SUCCESS" : "FAILED")
                .transactionId("TXN" + System.currentTimeMillis())
                .paymentDate(LocalDateTime.now())
                .booking(booking)
                .build();

        paymentRepository.save(payment);

        if (paymentSuccess) {
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        }


        return PaymentResponseDto.builder()
                .paymentId(payment.getId())
                .bookingId(booking.getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .transactionId(payment.getTransactionId())
                .paymentDate(payment.getPaymentDate())
                .build();
    }


    private boolean simulatePayment(Double amount) {
        return true;
    }
}