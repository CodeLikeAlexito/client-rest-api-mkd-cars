package com.codelikealexito.client.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private long id;
    private String username;
    private String token;
    private Date expirationTime;
}
