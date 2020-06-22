package com.paypal.busreservation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusDto {

  @NotEmpty
  private String busNumber;

  @NotEmpty
  private String operatorName;

  private List<CreateBusRouteDto> createBusRouteDtoList;

  @Min(value = 0, message = "Seats should be more than 0")
  private int numberOfSeats;

  @JsonIgnore
  private boolean isDeleted;
}
