package com.cetinkayayusuf.itemstocks.controllers.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRefreshRequest {
    private String username;
    private String refreshToken;
}
