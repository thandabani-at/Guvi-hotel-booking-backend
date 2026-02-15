package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.HotelRequestDto;
import com.example.hotel_booking.dto.HotelResponseDto;
import com.example.hotel_booking.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class HotelController {

    private final HotelService hotelService;


    @PostMapping("/create")
    public String createHotel(
            @RequestBody HotelRequestDto dto) {
        return hotelService.createHotel(dto);
    }

    @GetMapping
    public List<HotelResponseDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelResponseDto getById(
            @PathVariable Long id) {
        return hotelService.getHotelById(id);
    }
    @PutMapping("/update/{id}")
    public String updateHotel(
            @PathVariable Long id,
            @RequestBody HotelRequestDto dto) {
        return hotelService.updateHotel(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteHotel(
            @PathVariable Long id) {
        return hotelService.deleteHotel(id);
    }

    @GetMapping("/search")
    public List<HotelResponseDto> search(
            @RequestParam String location) {
        return hotelService.searchByLocation(location);
    }
}