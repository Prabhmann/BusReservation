package com.paypal.busreservation.mapper;

import com.paypal.busreservation.dto.BusTripResponseDto;
import com.paypal.busreservation.dto.CreateBusTripDto;
import com.paypal.busreservation.entity.BusTrip;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusTripMapper {

  BusTripMapper INSTANCE = Mappers.getMapper(BusTripMapper.class);


  BusTrip toEntity(CreateBusTripDto createBusTripDto);

  BusTripResponseDto toResponseDto(BusTrip busTrip);

  List<BusTripResponseDto> toResponseDtoList(List<BusTrip> busTrip);

}
