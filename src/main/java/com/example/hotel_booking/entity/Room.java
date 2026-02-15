package com.example.hotel_booking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;

    private String roomType;


    private Double PricePerNight;

    private Boolean available = true;


    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}