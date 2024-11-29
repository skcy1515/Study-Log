package com.example.practice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateAndEditRestaurant {
    private final String name;
    private final String address;
    private final List<CreateAndEditRestaurantMenu> menus;
}
