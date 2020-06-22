package com.paypal.busreservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusTripDto {

  private Long routeId;

  @JsonFormat(pattern = "dd-mm-yyyy")
  private Date travelDate;

  private BigDecimal ticketPrice;


}
