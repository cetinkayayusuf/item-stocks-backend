package com.cetinkayayusuf.itemstocks.controllers.dtos.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private Integer id;

    private String code;

    private String name;

    private String description;
}
