package com.codelikealexito.client.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private long id;
    private String title;
    private String price;
    private String shortDescription;
    private String description;
    private String additionalInformation;
    private String carLocation;
    private String author;
}
