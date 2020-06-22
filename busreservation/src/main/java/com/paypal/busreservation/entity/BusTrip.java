package com.paypal.busreservation.entity;

import static com.paypal.busreservation.constants.EntityConstants.Columns.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paypal.busreservation.constants.EntityConstants;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = EntityConstants.BUS_TRIP_TABLE)
public class BusTrip {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = BUS_ID)
  private Long routeId;

  @Column(name = SEATS_AVAILABLE)
  private Integer seatsAvailable;

  @Column(name = ARRIVAL_TIME)
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
  private Time arrivalTime;

  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
  @Column(name = DEPARTURE_TIME)
  private Time departureTime;

  @Column(name = SOURCE)
  private String source;

  @Column(name = DESTINATION)
  private String destination;

  @JsonFormat(pattern = "dd-mm-yyyy")
  @Column(name = TRAVEL_DATE)
  private Date travelDate;

  @Column(name = TICKET_PRICE)
  private BigDecimal ticketPrice;

}
