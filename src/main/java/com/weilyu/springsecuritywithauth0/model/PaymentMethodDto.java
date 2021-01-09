package com.weilyu.springsecuritywithauth0.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto {
     private Long exp_month;
     private Long exp_year;
     private String last4;
     private String brand;
}
