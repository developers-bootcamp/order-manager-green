package com.sap.ordermanegergreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class AuditDate {
  private LocalDate createDate;
  private LocalDate updateDate;
  public AuditDate(String createDate, String updateDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    this.createDate = LocalDate.parse(createDate,formatter);
    this.updateDate = LocalDate.parse(updateDate,formatter);
  }
}
