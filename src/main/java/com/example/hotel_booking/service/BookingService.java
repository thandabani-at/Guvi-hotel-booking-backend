package com.example.hotel_booking.service;

import com.example.hotel_booking.dto.BookingRequestDto;
import com.example.hotel_booking.dto.BookingResponseDto;
import com.example.hotel_booking.entity.*;
import com.example.hotel_booking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public Booking createBooking(BookingRequestDto dto) {


        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() ->
                        new RuntimeException("Room not found"));

        if (dto.getCheckOutDate().isBefore(dto.getCheckInDate())) {
            throw new RuntimeException(
                    "Check-out date must be after check-in date");
        }

        long days = ChronoUnit.DAYS.between(
                dto.getCheckInDate(),
                dto.getCheckOutDate()
        );


        if (days == 0) days = 1;


        double totalPrice = days * room.getPricePerNight();


        Booking booking = Booking.builder()
                .user(user)
                .room(room)
                .checkInDate(dto.getCheckInDate())
                .checkOutDate(dto.getCheckOutDate())
                .totalPrice(totalPrice)
                .status(BookingStatus.CONFIRMED)
                .build();

        bookingRepository.save(booking);

        return booking;
    }


    public List<BookingResponseDto> getMyBookings() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return bookingRepository.findByUser(user)
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    public List<BookingResponseDto> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public String cancelBooking(Long id) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        if (!booking.getUser()
                .getEmail()
                .equals(email)) {

            throw new RuntimeException(
                    "You can cancel only your booking");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);

        return "Booking Cancelled Successfully";
    }


    private BookingResponseDto mapToDto(Booking booking) {

        return BookingResponseDto.builder()
                .bookingId(booking.getId())
                .hotelName(
                        booking.getRoom()
                                .getHotel()
                                .getName())
                .roomType(
                        booking.getRoom()
                                .getRoomType())
                .checkInDate(
                        booking.getCheckInDate())
                .checkOutDate(
                        booking.getCheckOutDate())
                .totalPrice(
                        booking.getTotalPrice())
                .status(
                        booking.getStatus())
                .build();
    }
}