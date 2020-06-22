package com.paypal.busreservation.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusResponseDto {

  private Long id;

  private String busNumber;

  private String operatorName;

  private int numberOfSeats;

  private List<BusRouteResponseDto> busRouteResponseDto;
}
