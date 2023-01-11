package com.cetinkayayusuf.itemstocks.controllers.dtos.stock;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SearchStockRequest {
    @NotBlank
    @Size(max = 50)
    private String name;
}
