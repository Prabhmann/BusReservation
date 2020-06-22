package com.paypal.busreservation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paypal.busreservation.constants.EntityConstants;
import com.paypal.busreservation.constants.EntityConstants.Columns;
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
@Table(name = EntityConstants.BOOKING_DETAILS_TABLE)
public class BookingDetails {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = Columns.SEAT_NO)
  private Integer seatNumber;

  @ToString.Exclude
  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = EntityConstants.BOOKING_ID, nullable = false)
  private Booking booking;

  @Column(name = Columns.TRIP_ID)
  private Integer tripId;

}
