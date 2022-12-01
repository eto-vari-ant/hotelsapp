package com.travel.hotelsapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "hotels")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String city;

    private String country;

    private int stars;

    @Column(name = "min_cost", nullable = false)
    private int minCost;

    @Column(name = "max_cost", nullable = false)
    private int maxCost;

    private String nutrition;

    @Column(name = "rooms_number", nullable = false)
    private int roomNumber;

    @Column(name = "sea_distance", nullable = false)
    private int seaDistance;

    private String description;

    private String longitude;

    private String latitude;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "hotel_service",
            joinColumns = { @JoinColumn(name = "hotel_id") },
            inverseJoinColumns = { @JoinColumn(name = "service_id") }
    )
    private List<Service> services = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<Cost> costs = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<Booking> bookings = new ArrayList<>();


}

