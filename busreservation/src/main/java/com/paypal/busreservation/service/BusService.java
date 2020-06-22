package com.paypal.busreservation.service;

import com.paypal.busreservation.dto.BusResponseDto;
import com.paypal.busreservation.dto.CreateBusDto;
import com.paypal.busreservation.dto.CreateBusTripDto;
import com.paypal.busreservation.dto.FilterCriteria;
import org.springframework.data.domain.PageImpl;

public interface BusService {

  BusResponseDto createBus(final CreateBusDto createBusDto);

  void createBusTrip(final CreateBusTripDto createBusTripDto);

  PageImpl<BusResponseDto> getBuses(final FilterCriteria filterCriteria);

}
