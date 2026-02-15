package com.example.hotel_booking.controller;

import com.example.hotel_booking.dto.RoomRequestDto;
import com.example.hotel_booking.dto.RoomResponseDto;
import com.example.hotel_booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    public String createRoom(@RequestBody RoomRequestDto dto){
        return roomService.createRoom(dto);
    }


    @GetMapping("/hotel/{hotelId}")
    public List<RoomResponseDto> getRoomsByHotel(
            @PathVariable Long hotelId){
        return roomService.getRoomsByHotel(hotelId);
    }


    @PutMapping("/update/{id}")
    public String updateRoom(
            @PathVariable Long id,
            @RequestBody RoomRequestDto dto){
        return roomService.updateRoom(id, dto);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id){
        return roomService.deleteRoom(id);
    }


    @GetMapping("/available")
    public List<RoomResponseDto> availableRooms(){
        return roomService.getAvailableRooms();
    }
}
