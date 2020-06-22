package com.paypal.busreservation.repository;

import com.paypal.busreservation.entity.Booking;
import com.paypal.busreservation.entity.BookingDetails;
import io.swagger.models.auth.In;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {

  List<BookingDetails> findByTripIdAndSeatNumberIn(Integer tripId, List<Integer> seatNoList);

  List<BookingDetails> findByTripId(Integer id);
}
