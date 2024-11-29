package com.example.practice.response;

import com.example.practice.model.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MenuResponse {
    private Long menuId;
    private String name;
    private Integer price;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public MenuResponse(MenuEntity menuEntity) {
        this.menuId = menuEntity.getMenuId();
        this.name = menuEntity.getName();
        this.price = menuEntity.getPrice();
        this.createdAt = menuEntity.getCreatedAt();
        this.updatedAt = menuEntity.getUpdatedAt();
    }
}
