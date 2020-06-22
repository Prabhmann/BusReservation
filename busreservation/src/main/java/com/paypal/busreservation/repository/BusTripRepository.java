package com.paypal.busreservation.repository;

import com.paypal.busreservation.entity.BusTrip;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BusTripRepository extends JpaRepository<BusTrip, Long>,
    PagingAndSortingRepository<BusTrip,Long>, JpaSpecificationExecutor<BusTrip> {

  Optional<BusTrip> findByRouteIdAndArrivalTimeAndDepartureTimeAndTravelDate(Long routeId,
      Time arrivalTime, Time departureTime, Date travelDate);

  List<BusTrip> findByIdIn(List<Long> id);
}
