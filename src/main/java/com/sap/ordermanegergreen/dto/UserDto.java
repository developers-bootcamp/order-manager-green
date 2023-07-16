package com.sap.ordermanegergreen.dto;

import com.sap.ordermanegergreen.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Id
    private String id;
    private String fullName;
    private String email;
    private String addressName;
    private String telephone;
    private String roleId;
}
