package com.sap.ordermanagergreen.dto;

import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    @Id
    private String id;
    private String fullName;
    private String email;
    private String address;
    private String telephone;
    private String roleId;

}