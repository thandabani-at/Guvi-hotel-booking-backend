package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.AdminDashboardDto;
import com.example.hotel_booking.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;


    @GetMapping
    public AdminDashboardDto getDashboard() {
        return adminDashboardService.getDashboardStats();
    }
}