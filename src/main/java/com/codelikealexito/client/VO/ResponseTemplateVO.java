package com.codelikealexito.client.VO;

import com.codelikealexito.client.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateVO {
    private Client client;
    private Car car;
}
