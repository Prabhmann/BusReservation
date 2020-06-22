package com.paypal.busreservation.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

  private Integer tripId;

  private String customerName;

  private List<Integer> seatNoList;


}
