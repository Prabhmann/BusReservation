package com.paypal.busreservation.entity;

import static com.paypal.busreservation.constants.EntityConstants.Columns.ARRIVAL_TIME;
import static com.paypal.busreservation.constants.EntityConstants.Columns.DEPARTURE_TIME;
import static com.paypal.busreservation.constants.EntityConstants.Columns.DESTINATION;
import static com.paypal.busreservation.constants.EntityConstants.Columns.SOURCE;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paypal.busreservation.constants.EntityConstants;
import java.math.BigDecimal;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = EntityConstants.BUS_ROUTE_TABLE)
public class BusRoute {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ToString.Exclude
  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = EntityConstants.BUS_ID, nullable = false)
  private Bus bus;

  @Column(name = SOURCE)
  private String source;

  @Column(name = DESTINATION)
  private String destination;

  @Column(name = ARRIVAL_TIME)
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
  private Time arrivalTime;

  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
  @Column(name = DEPARTURE_TIME)
  private Time departureTime;
  
}
