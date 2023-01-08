package com.cetinkayayusuf.itemstocks.controllers.dtos.stock;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddStockRequest {
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 120)
    private String description;

    private Long itemId;

    @Size()
    private int amount;
}
