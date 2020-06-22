package com.paypal.busreservation.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.paypal.busreservation.constants.EntityConstants;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;

import static com.paypal.busreservation.constants.EntityConstants.Columns.*;

@Data
@Entity
@Table(name = EntityConstants.BOOKING_ATTRIBUTE)
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = BOOKING_NUMBER)
  private String bookingNumber;

  @Column(name = TRIP_ID)
  private Long tripId;

  @Column(name = CUSTOMER_NAME)
  private String customerName;

  @OneToMany(mappedBy = EntityConstants.BOOKING_ATTRIBUTE,
      cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<BookingDetails> bookingDetails;

  @Column(name = TOTAL_PRICE)
  private BigDecimal totalPrice;

  @PrePersist
  private void generate() {
    this.bookingNumber =
        "BOOKING" + String.valueOf(UUID.randomUUID()).substring(0, 5) + customerName;
  }


}
