package com.sap.ordermanagergreen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TokenDTO {
    private String userId;
    private String companyId;
    private String roleId;
    private Date expirationDate;
}
