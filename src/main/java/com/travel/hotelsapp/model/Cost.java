package com.travel.hotelsapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel_room_cost")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cost{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int cost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "cost")
    private List<Booking> bookings = new ArrayList<>();
}

