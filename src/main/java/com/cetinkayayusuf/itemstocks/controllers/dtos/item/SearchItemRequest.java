package com.cetinkayayusuf.itemstocks.controllers.dtos.item;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SearchItemRequest {
    @NotBlank
    @Size(max = 50)
    private String name;
}
