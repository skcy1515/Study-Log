package com.example.practice.service;

import com.example.practice.model.MenuEntity;
import com.example.practice.model.RestaurantEntity;
import com.example.practice.repository.MenuRepository;
import com.example.practice.repository.RestaurantRepository;
import com.example.practice.request.CreateAndEditRestaurant;
import com.example.practice.request.CreateAndEditRestaurantMenu;
import com.example.practice.response.RestaurantResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    // 레스토랑과 메뉴 생성
    @Transactional
    public void createRestaurant(
            CreateAndEditRestaurant createAndEditRestaurant
    ) {
        RestaurantEntity restaurantEntity = RestaurantEntity.builder().
                name(createAndEditRestaurant.getName()).
                address(createAndEditRestaurant.getAddress()).
                createdAt(ZonedDateTime.now()).
                updatedAt(ZonedDateTime.now()).
                build();

        restaurantRepository.save(restaurantEntity);

        createAndEditRestaurant.getMenus().forEach((menu) -> {
            MenuEntity menuEntity = MenuEntity.builder().
                    name(menu.getName()).
                    price(menu.getPrice()).
                    createdAt(ZonedDateTime.now()).
                    updatedAt(ZonedDateTime.now()).
                    restaurant(restaurantEntity). // 관계 설정
                    build();

            menuRepository.save(menuEntity);
        });
    }

    // 메뉴 추가
    public void createMenu(Long restaurantId, CreateAndEditRestaurantMenu createAndEditRestaurantMenu){
        // 레스토랑 조회
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 레스토랑을 찾을 수 없습니다."));

        // 새로운 메뉴 생성 및 레스토랑 설정
        MenuEntity menuEntity = MenuEntity.builder()
                .name(createAndEditRestaurantMenu.getName())
                .price(createAndEditRestaurantMenu.getPrice())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .restaurant(restaurantEntity)  // 관계 설정
                .build();

        // 메뉴 저장
        menuRepository.save(menuEntity);
    }

    // 메뉴 변경
    public void editMenu(Long menuId, CreateAndEditRestaurantMenu request) {
        MenuEntity menuEntity = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("해당 레스토랑에 메뉴가 존재하지 않습니다."));

        menuEntity.changeNameAndPrice(request.getName(), request.getPrice());
        menuRepository.save(menuEntity);
    }

    // 레스토랑 변경
    public void editRestaurant(Long restaurantId, CreateAndEditRestaurant request) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId).
                orElseThrow(() -> new RuntimeException("없는 레스토랑입니다."));

        restaurantEntity.changeNameAndAddress(request.getName(), request.getAddress());
        restaurantRepository.save(restaurantEntity);
    }

    // 레스토랑 삭제 (해당하는 메뉴들도 함께 삭제)
    public void deleteRestaurant(Long restaurantId) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId)
                .orElseThrow();
        restaurantRepository.delete(restaurantEntity);
    }

    // 메뉴 삭제
    public void deleteMenu (Long menuId){
        MenuEntity menuEntity = menuRepository.findById(menuId)
                .orElseThrow();
        menuRepository.delete(menuEntity);
    }

    // 레스토랑과 그에 해당하는 메뉴들 조회
    public RestaurantResponse getRestaurantWithMenus(Long restaurantId) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 레스토랑을 찾을 수 없습니다."));

        // 레스토랑과 메뉴들 데이터를 Dto로 변환하여 반환
        return new RestaurantResponse(restaurantEntity);
    }

    // 모든 레스토랑 조회
    public List<RestaurantResponse> getAllRestaurants() {
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll(); // 모든 레스토랑 조회

        return restaurantEntities.stream()
                .map(RestaurantResponse::new)  // 각 레스토랑을 Dto로 변환
                .collect(Collectors.toList());
    }
}
