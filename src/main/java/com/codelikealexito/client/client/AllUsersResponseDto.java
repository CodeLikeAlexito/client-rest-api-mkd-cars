package com.codelikealexito.client.client;

import com.codelikealexito.client.entities.Client;
import lombok.Data;

import java.util.List;

@Data
public class AllUsersResponseDto {
    private List<Client> allClientsList;
}
