package com.sap.ordermanagergreen.dto;

import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.model.Company;
import com.sap.ordermanagergreen.model.Role;
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
    private String password;
    private String email;
    private String address;
    private String telephone;
    private AvailableRole roleName;
    private Company company;

}