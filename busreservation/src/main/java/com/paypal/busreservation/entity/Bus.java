package com.paypal.busreservation.entity;

import static com.paypal.busreservation.constants.EntityConstants.Columns.BUS_NUMBER;
import static com.paypal.busreservation.constants.EntityConstants.Columns.IS_DELETED;
import static com.paypal.busreservation.constants.EntityConstants.Columns.NO_OF_SEATS;
import static com.paypal.busreservation.constants.EntityConstants.Columns.OPERATOR_NAME;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.paypal.busreservation.constants.EntityConstants;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = EntityConstants.BUS_TABLE)
public class Bus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = BUS_NUMBER)
  private String busNumber;

  @Column(name = OPERATOR_NAME)
  private String operatorName;

  @OneToMany(mappedBy = EntityConstants.BUS_ATTRIBUTE,
      cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<BusRoute> busRouteList;

  @Column(name = NO_OF_SEATS)
  private int numberOfSeats;

  @Column(name = IS_DELETED)
  private boolean isDeleted;


}
