package com.paypal.busreservation.service;

import com.paypal.busreservation.dto.BookingDto;
import com.paypal.busreservation.dto.BusTripResponseDto;
import com.paypal.busreservation.dto.FilterCriteria;
import com.paypal.busreservation.entity.Booking;
import org.springframework.data.domain.PageImpl;

public interface BookingService {

  Booking createBooking(final BookingDto bookingDto);

  PageImpl<BusTripResponseDto> searchTrips(final FilterCriteria filterCriteria);

  BusTripResponseDto getSeats(Integer id);
}
