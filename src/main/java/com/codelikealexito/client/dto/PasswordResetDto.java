package com.codelikealexito.client.dto;

import lombok.Data;

@Data
public class PasswordResetDto {
    private String newPassword;
    private String confirmNewPassword;
}
