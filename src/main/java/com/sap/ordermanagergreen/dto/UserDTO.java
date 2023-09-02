package com.sap.ordermanagergreen.dto;

import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.model.Company;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDTO {
    @Id
    private String id;
    private String fullName;
    private String password;
    private String email;
    private String addressName;
    private String telephone;
    private AvailableRole roleName;
    private Company company;

}
