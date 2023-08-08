package com.sap.ordermanagergreen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto {
    @Id
    private String id;
    private String fullName;
    private String email;
    private String addressName;
    private String telephone;
    private String roleId;
}
