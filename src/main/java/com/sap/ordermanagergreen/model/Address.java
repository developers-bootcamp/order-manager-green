package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@SuperBuilder
public class Address {

    private String telephone;
    private String addressName;
    @Email(message = "Please provide a valid email address")
    private String email;
    
}
