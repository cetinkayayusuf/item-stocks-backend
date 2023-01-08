package com.cetinkayayusuf.itemstocks.controllers.dtos.item;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdateItemRequest {
    @Size(max = 50)
    private String code;

    @Size(max = 50)
    private String name;

    @Size(max = 120)
    private String description;
}
