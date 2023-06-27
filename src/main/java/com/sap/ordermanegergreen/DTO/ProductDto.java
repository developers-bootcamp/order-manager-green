package com.sap.ordermanegergreen.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @Id private String Id;
    private String Name;
}
