package com.example.hotel_booking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDashboardDto {

    private Long totalUsers;
    private Long totalHotels;
    private Long totalBookings;
    private Long totalPayments;
    private Double totalRevenue;
}