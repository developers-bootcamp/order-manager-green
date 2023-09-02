package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Address {

    private String telephone;
    private String addressName;
    @Email(message = "Please provide a valid email address")
    private String email;

}
