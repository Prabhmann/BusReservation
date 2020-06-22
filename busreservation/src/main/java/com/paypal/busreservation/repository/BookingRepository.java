package com.paypal.busreservation.repository;

import com.paypal.busreservation.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
  
}
