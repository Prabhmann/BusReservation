package com.paypal.busreservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusRouteDto {

  @NotEmpty
  private String source;

  @NotEmpty
  private String destination;

  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
  @Temporal(TemporalType.TIME)
  private Date arrivalTime;

  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
  @Temporal(TemporalType.TIME)
  private Date departureTime;

}
