package com.codelikealexito.client.enums;

public enum Roles {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String value;

    Roles(String value){
        this.value = value;
    }
}
