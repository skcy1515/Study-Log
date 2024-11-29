package com.example.practice.repository;

import com.example.practice.model.RestaurantEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    @EntityGraph(attributePaths = "menus")  // 메뉴들을 함께 로딩
    Optional<RestaurantEntity> findById(Long id);

    @EntityGraph(attributePaths = "menus")  // 메뉴들도 함께 로딩
    List<RestaurantEntity> findAll();  // 모든 레스토랑을 조회
}
