package com.sap.ordermanegergreen.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String userId;
    private String companyId;
    private String roleId;
    private Date expirationDate;
}
