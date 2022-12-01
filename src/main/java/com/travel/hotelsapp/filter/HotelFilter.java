package com.travel.hotelsapp.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelFilter {
    private List<String> nutritions;
    private List<Integer> stars;
    private List<String> countries;
    private int minCost;
    private int maxCost;
}
