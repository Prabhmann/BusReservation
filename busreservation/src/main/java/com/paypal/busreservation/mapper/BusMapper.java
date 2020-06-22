package com.paypal.busreservation.mapper;

import com.paypal.busreservation.dto.BusResponseDto;
import com.paypal.busreservation.dto.CreateBusDto;
import com.paypal.busreservation.entity.Bus;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusMapper {

  BusMapper INSTANCE = Mappers.getMapper(BusMapper.class);

  Bus toEntity(CreateBusDto createBusDto);

  BusResponseDto toResponseDto(final Bus bus);

  List<BusResponseDto> toResponseDtoList(final List<Bus> busList);



}
