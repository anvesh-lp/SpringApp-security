package com.anvesh.springsecurity.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndPasswordAuthenticationRequest {
    private String username;
    private String password;
}
