package com.cetinkayayusuf.itemstocks.controllers.dtos.auth;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private String refreshToken;
    private String username;
    @Setter(AccessLevel.NONE)
    private List<String> roles;

}
