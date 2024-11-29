package com.example.practice.api;

import com.example.practice.request.CreateAndEditRestaurant;
import com.example.practice.request.CreateAndEditRestaurantMenu;
import com.example.practice.response.RestaurantResponse;
import com.example.practice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantApi {

    private final RestaurantService restaurantService;

    // 레스토랑, 메뉴 생성
    @PostMapping("/restaurant")
    public void createRestaurant(
            @RequestBody CreateAndEditRestaurant request
    ) {
        restaurantService.createRestaurant(request);
    }

    // 레스토랑에 메뉴 추가
    @PostMapping("/restaurant/{restaurantId}/menu")
    public void createMenu(
            @PathVariable Long restaurantId,
            @RequestBody CreateAndEditRestaurantMenu createAndEditRestaurantMenu
    ){
        restaurantService.createMenu(restaurantId, createAndEditRestaurantMenu);
    }

    // 레스토랑 수정
    @PutMapping("/restaurant/{restaurantId}")
    public void editRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody CreateAndEditRestaurant request
    ){
        restaurantService.editRestaurant(restaurantId, request);
    }

    // 레스토랑의 메뉴 수정
    @PutMapping("/menu/{menuId}")
    public void editMenu(
            @PathVariable Long menuId,
            @RequestBody CreateAndEditRestaurantMenu request
    ){
        restaurantService.editMenu(menuId, request);
    }

    // 레스토랑과 그에 관련된 메뉴 삭제
    @DeleteMapping("/restaurant/{restaurantId}")
    public void deleteRestaurant(
            @PathVariable Long restaurantId
    ){
        restaurantService.deleteRestaurant(restaurantId);
    }

    // 메뉴 삭제
    @DeleteMapping("/menu/{menuId}")
    public void deleteMenu(
            @PathVariable Long menuId
    ){
        restaurantService.deleteMenu(menuId);
    }

    // 레스토랑 조회
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantWithMenus(@PathVariable Long restaurantId) {
        RestaurantResponse restaurantWithMenus = restaurantService.getRestaurantWithMenus(restaurantId);
        return ResponseEntity.ok(restaurantWithMenus);
    }

    // 전체 레스토랑 조회
    @GetMapping("/restaurant")
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<RestaurantResponse> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }
}
