package com.travel.hotelsapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type")
    private Cost cost;

    @Column(name = "final_cost")
    private int finalCost;

    @Column(name = "day_amount")
    @Min(value = 5, message = "Вы не можете забронировать отель менее чем на 5 дней")
    @Max(value = 30, message = "Вы не можете забронировать отель более чем на 30 дней")
    private int dayAmount;

    @Column(name = "start_date")
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date startDate;
}
