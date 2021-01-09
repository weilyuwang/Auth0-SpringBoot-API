package com.weilyu.springsecuritywithauth0.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String name;
    private String description;
    private String email;
    private String token;
}
