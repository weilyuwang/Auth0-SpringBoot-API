package com.weilyu.springsecuritywithauth0.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private UUID id;

    private String number;

    private Integer exp_month;

    private Integer exp_year;

    private String cvc;
}
