package com.codelikealexito.client.enums;

public enum Roles {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    MODERATOR("ROLE_MODERATOR"),
    GUEST("ROLE_GUEST");

    private final String value;

    Roles(String value){
        this.value = value;
    }
}
