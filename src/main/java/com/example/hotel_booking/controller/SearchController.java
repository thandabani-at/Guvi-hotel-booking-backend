package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.SearchResponseDto;
import com.example.hotel_booking.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SearchController {

    private final SearchService searchService;


    @GetMapping
    public List<SearchResponseDto> search(
            @RequestParam String location,
            @RequestParam String checkIn,
            @RequestParam String checkOut
    ) {

        return searchService.searchAvailableRooms(
                location,
                LocalDate.parse(checkIn),
                LocalDate.parse(checkOut)
        );
    }
}