package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.HotelRequestDto;
import com.example.hotel_booking.dto.HotelResponseDto;
import com.example.hotel_booking.entity.Hotel;
import com.example.hotel_booking.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;


    public String createHotel(HotelRequestDto dto) {

        Hotel hotel = Hotel.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .pricePerNight(dto.getPricePerNight())
                .rating(dto.getRating())
                .build();

        hotelRepository.save(hotel);

        return "Hotel Created Successfully";
    }


    public List<HotelResponseDto> getAllHotels() {

        return hotelRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    public HotelResponseDto getHotelById(Long id) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hotel Not Found"));

        return mapToDto(hotel);
    }

    public String updateHotel(Long id,
                              HotelRequestDto dto) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hotel Not Found"));

        hotel.setName(dto.getName());
        hotel.setLocation(dto.getLocation());
        hotel.setDescription(dto.getDescription());
        hotel.setPricePerNight(dto.getPricePerNight());
        hotel.setRating(dto.getRating());

        hotelRepository.save(hotel);

        return "Hotel Updated Successfully";
    }
    public String deleteHotel(Long id) {

        hotelRepository.deleteById(id);

        return "Hotel Deleted Successfully";
    }


    public List<HotelResponseDto> searchByLocation(
            String location) {

        return hotelRepository
                .findByLocationContainingIgnoreCase(location)
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    private HotelResponseDto mapToDto(Hotel hotel) {

        return HotelResponseDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .location(hotel.getLocation())
                .description(hotel.getDescription())
                .pricePerNight(hotel.getPricePerNight())
                .rating(hotel.getRating())
                .build();
    }
}