package com.codelikealexito.client.client;

import com.codelikealexito.client.entities.Role;
import lombok.Getter;

import java.util.Collection;

@Getter
public class ClientUpdateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String city;
    private String address;
    private String phone;
    private String username;
    private Collection<Role> roles;
}
