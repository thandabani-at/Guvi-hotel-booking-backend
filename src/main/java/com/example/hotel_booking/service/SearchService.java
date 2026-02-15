package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.SearchResponseDto;
import com.example.hotel_booking.entity.*;
import com.example.hotel_booking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;


    public List<SearchResponseDto> searchAvailableRooms(
            String location,
            LocalDate checkIn,
            LocalDate checkOut
    ) {

        List<SearchResponseDto> result =
                new ArrayList<>();


        List<Hotel> hotels =
                hotelRepository
                        .findByLocationContainingIgnoreCase(location);

        for (Hotel hotel : hotels) {


            List<Room> rooms =
                    roomRepository.findByHotel(hotel);

            for (Room room : rooms) {


                List<Booking> bookings =
                        bookingRepository
                                .findByRoomAndCheckOutDateAfterAndCheckInDateBefore(
                                        room,
                                        checkIn,
                                        checkOut
                                );

                if (bookings.isEmpty()) {

                    result.add(
                            SearchResponseDto.builder()
                                    .hotelId(hotel.getId())
                                    .hotelName(hotel.getName())
                                    .location(hotel.getLocation())
                                    .roomId(room.getId())
                                    .roomType(room.getRoomType())
                                    .pricePerNight(room.getPricePerNight())
                                    .build()
                    );
                }
            }
        }

        return result;
    }
}