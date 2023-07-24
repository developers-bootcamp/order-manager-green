package com.sap.ordermanagergreen.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class Address {
    
    private String telephone;
    @NotNull
    private String addressName;
    //@NotBlank
    private String email;
    
}
