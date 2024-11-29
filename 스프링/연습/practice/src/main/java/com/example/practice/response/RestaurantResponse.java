package com.example.practice.response;

import com.example.practice.model.RestaurantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RestaurantResponse {
    private Long id;
    private String name;
    private String address;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<MenuResponse> menus;

    public RestaurantResponse(RestaurantEntity restaurantEntity) {
        this.id = restaurantEntity.getId();
        this.name = restaurantEntity.getName();
        this.address = restaurantEntity.getAddress();
        this.createdAt = restaurantEntity.getCreatedAt();
        this.updatedAt = restaurantEntity.getUpdatedAt();
        this.menus = restaurantEntity.getMenus().stream()
                .map(MenuResponse::new)  // MenuEntity를 MenuResponse로 변환
                .collect(Collectors.toList());
    }
}
