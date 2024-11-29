package com.example.practice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAndEditRestaurantMenu {
    private final String name;
    private final Integer price;
}
