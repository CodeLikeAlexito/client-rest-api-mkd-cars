package com.codelikealexito.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ValidateTokenDto {
    private ScientistDto scientistDto;
    private Boolean isValid;
}
