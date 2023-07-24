package com.sap.ordermanagergreen.model;

import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@SuperBuilder
public class Address {

    private String telephone;
    @NotNull
    private String addressName;
    //@NotBlank
    private String address;
    @Email(message = "Please provide a valid email address")
    private String email;
    
}
