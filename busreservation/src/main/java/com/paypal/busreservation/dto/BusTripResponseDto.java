package com.paypal.busreservation.dto;

import static com.paypal.busreservation.constants.EntityConstants.Columns.ARRIVAL_TIME;
import static com.paypal.busreservation.constants.EntityConstants.Columns.BUS_ID;
import static com.paypal.busreservation.constants.EntityConstants.Columns.DEPARTURE_TIME;
import static com.paypal.busreservation.constants.EntityConstants.Columns.DESTINATION;
import static com.paypal.busreservation.constants.EntityConstants.Columns.SEATS_AVAILABLE;
import static com.paypal.busreservation.constants.EntityConstants.Columns.SOURCE;
import static com.paypal.busreservation.constants.EntityConstants.Columns.TICKET_PRICE;
import static com.paypal.busreservation.constants.EntityConstants.Columns.TRAVEL_DATE;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusTripResponseDto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long routeId;

  private Integer seatsAvailable;
  
  private Map<Integer,String> seats = new HashMap<>();

  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
  private Time arrivalTime;

  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
  private Time departureTime;

  private String source;

  private String destination;

  private String travelDate;
  
  private BigDecimal ticketPrice;

}
