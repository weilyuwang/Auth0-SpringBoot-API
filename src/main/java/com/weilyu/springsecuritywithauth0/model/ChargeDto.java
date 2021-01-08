package com.weilyu.springsecuritywithauth0.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeDto {

    private String currency;

    private Long amount;
}
