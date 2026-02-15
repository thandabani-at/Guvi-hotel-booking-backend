package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.AdminDashboardDto;
import com.example.hotel_booking.service.AdminDashboardService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminDashboardController.class)
class AdminDashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminDashboardService adminDashboardService;

    @Test
    void dashboardApiTest() throws Exception {

        AdminDashboardDto dto =
                AdminDashboardDto.builder()
                        .totalUsers(10L)
                        .totalHotels(5L)
                        .totalBookings(20L)
                        .totalPayments(15L)
                        .totalRevenue(8000.0)
                        .build();

        when(adminDashboardService
                .getDashboardStats())
                .thenReturn(dto);

        mockMvc.perform(
                        get("/api/admin/dashboard")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUsers")
                        .value(10))
                .andExpect(jsonPath("$.totalHotels")
                        .value(5))
                .andExpect(jsonPath("$.totalRevenue")
                        .value(8000.0));
    }
}