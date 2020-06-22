package com.paypal.busreservation.mapper;

import com.paypal.busreservation.dto.BookingDto;
import com.paypal.busreservation.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

  BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);
  
  Booking toEntity(BookingDto bookingDto);

}
