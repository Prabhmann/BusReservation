package com.paypal.busreservation.mapper;

import com.paypal.busreservation.dto.BusResponseDto;
import com.paypal.busreservation.dto.BusRouteResponseDto;
import com.paypal.busreservation.dto.CreateBusRouteDto;
import com.paypal.busreservation.entity.BusRoute;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusRouteMapper {


  BusRouteMapper INSTANCE = Mappers.getMapper(BusRouteMapper.class);

   BusRouteResponseDto toResponseDto(BusRoute busRoute);

  List<BusRouteResponseDto> toResponseDtoList(List<BusRoute> busRoute);

  BusRoute toEntity(CreateBusRouteDto createBusRouteDto);

  List<BusRoute> toEntityList(List<CreateBusRouteDto> createBusRouteDto);

}
