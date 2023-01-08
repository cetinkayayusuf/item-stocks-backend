package com.cetinkayayusuf.itemstocks.controllers.dtos.stock;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdateStockRequest {
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 120)
    private String description;

    @NotBlank
    @Size(min = 0)
    private int amount;
}
