package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.RoomRequestDto;
import com.example.hotel_booking.dto.RoomResponseDto;
import com.example.hotel_booking.entity.Hotel;
import com.example.hotel_booking.entity.Room;
import com.example.hotel_booking.repository.HotelRepository;
import com.example.hotel_booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;


    public String createRoom(RoomRequestDto dto){

        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Room room = new Room();
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setPricePerNight(dto.getPrice());
        room.setAvailable(dto.getAvailable());
        room.setHotel(hotel);

        roomRepository.save(room);

        return "Room created successfully";
    }


    public List<RoomResponseDto> getRoomsByHotel(Long hotelId){

        return roomRepository.findByHotelId(hotelId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public String updateRoom(Long id, RoomRequestDto dto){

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setPricePerNight(dto.getPrice());
        room.setAvailable(dto.getAvailable());

        roomRepository.save(room);

        return "Room updated successfully";
    }


    public String deleteRoom(Long id){

        roomRepository.deleteById(id);

        return "Room deleted successfully";
    }

    public List<RoomResponseDto> getAvailableRooms(){

        return roomRepository.findByAvailableTrue()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private RoomResponseDto mapToDto(Room room){

        return RoomResponseDto.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .price(room.getPricePerNight())
                .available(room.getAvailable())
                .hotelName(room.getHotel().getName())
                .build();
    }
}