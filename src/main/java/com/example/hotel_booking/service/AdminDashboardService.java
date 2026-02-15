package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.AdminDashboardDto;
import com.example.hotel_booking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final PaymentRepository paymentRepository;
    private final com.example.hotel_booking.repository.UserRepository userRepository;

    public AdminDashboardDto getDashboardStats() {

        Long totalUsers = userRepository.count();
        Long totalHotels = hotelRepository.count();
        Long totalBookings = bookingRepository.count();
        Long totalPayments = paymentRepository.count();


        Double totalRevenue = paymentRepository.findAll()
                .stream()
                .filter(p -> "SUCCESS".equals(p.getPaymentStatus()) && p.getAmount() != null)
                .mapToDouble(p -> p.getAmount())
                .sum();

        return AdminDashboardDto.builder()
                .totalUsers(totalUsers)
                .totalHotels(totalHotels)
                .totalBookings(totalBookings)
                .totalPayments(totalPayments)
                .totalRevenue(totalRevenue)
                .build();
    }
}