package com.paypal.busreservation.service.impl;

import com.paypal.busreservation.dto.BusResponseDto;
import com.paypal.busreservation.dto.CreateBusDto;
import com.paypal.busreservation.dto.CreateBusTripDto;
import com.paypal.busreservation.dto.FilterCriteria;
import com.paypal.busreservation.dto.SearchCriteria;
import com.paypal.busreservation.entity.Bus;
import com.paypal.busreservation.entity.BusRoute;
import com.paypal.busreservation.entity.BusTrip;
import com.paypal.busreservation.exception.ServiceException;
import com.paypal.busreservation.mapper.BusMapper;
import com.paypal.busreservation.mapper.BusRouteMapper;
import com.paypal.busreservation.mapper.BusTripMapper;
import com.paypal.busreservation.repository.BusRepository;
import com.paypal.busreservation.repository.BusRouteRepository;
import com.paypal.busreservation.repository.BusSpecificationBuilder;
import com.paypal.busreservation.repository.BusTripRepository;
import com.paypal.busreservation.service.BusService;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
@Log4j2
public class BusServiceImpl implements BusService {

  @Autowired
  private BusRepository busRepository;

  @Autowired
  private BusTripRepository busTripRepository;

  @Autowired
  private BusRouteRepository busRouteRepository;


  @Override
  @Transactional(rollbackOn = Exception.class)
  public BusResponseDto createBus(final CreateBusDto createBusDto) {

    if (ObjectUtils.isEmpty(createBusDto)) {
      throw new ServiceException("Please provide bus details", HttpStatus.BAD_REQUEST);
    }

    final Optional<Bus> optionalBus = this.busRepository
        .findByBusNumber(createBusDto.getBusNumber());

    optionalBus.ifPresent(e -> {
      throw new ServiceException(
          String.format("Bus already exists with number {%s}", e.getBusNumber()),
          HttpStatus.CONFLICT);
    });

    final Bus bus = BusMapper.INSTANCE.toEntity(createBusDto);
    bus.setBusRouteList(
        BusRouteMapper.INSTANCE.toEntityList(createBusDto.getCreateBusRouteDtoList()));
    DateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
    this.extractTimes(bus, localDateFormat);
    final Bus savedBus = this.busRepository.save(bus);
    final BusResponseDto busResponseDto = BusMapper.INSTANCE.toResponseDto(savedBus);
    busResponseDto.setBusRouteResponseDto(BusRouteMapper.INSTANCE.toResponseDtoList(savedBus.getBusRouteList()));
    return busResponseDto;
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void createBusTrip(CreateBusTripDto createBusTripDto) {

    if (ObjectUtils.isEmpty(createBusTripDto)) {
      throw new ServiceException("Please provide bus trip details", HttpStatus.BAD_REQUEST);
    }

    final Optional<BusRoute> optionalBusRoute = this.busRouteRepository
        .findById(createBusTripDto.getRouteId());
    if (!optionalBusRoute.isPresent()) {
      throw new ServiceException("Bus route not found", HttpStatus.BAD_REQUEST);
    }

    final BusTrip busTrip = BusTripMapper.INSTANCE.toEntity(createBusTripDto);
    busTrip.setSeatsAvailable(optionalBusRoute.get().getBus().getNumberOfSeats());
    final Optional<BusTrip> optionalBusTrip = this.busTripRepository
        .findByRouteIdAndArrivalTimeAndDepartureTimeAndTravelDate(busTrip.getRouteId(),
            busTrip.getArrivalTime(), busTrip.getDepartureTime(), busTrip.getTravelDate());

    optionalBusTrip.ifPresent(e -> {
      throw new ServiceException("Bus trip already exists", HttpStatus.BAD_REQUEST);
    });

    final Optional<BusRoute> optionalRoute = this.busRouteRepository.findById(createBusTripDto.getRouteId());
    if(!optionalRoute.isPresent()){
      throw new ServiceException("Bus route not exists", HttpStatus.BAD_REQUEST);
    }
    busTrip.setDepartureTime(optionalBusRoute.get().getDepartureTime());
    busTrip.setArrivalTime(optionalBusRoute.get().getArrivalTime());
    busTrip.setSource(optionalBusRoute.get().getSource());
    busTrip.setDestination(optionalBusRoute.get().getDestination());
    
    this.busTripRepository.save(busTrip);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public PageImpl<BusResponseDto> getBuses(FilterCriteria filterCriteria) {
    List<SearchCriteria> params = filterCriteria.getCriterion();
    if (CollectionUtils.isEmpty(params)) {
      params = new ArrayList<>();
    }
    final BusSpecificationBuilder builder =
        new BusSpecificationBuilder(filterCriteria.getFullTextSearch(), params);

    final Specification<Bus> specification = builder.build();
    final int page = filterCriteria.getPage();
    final int limit = filterCriteria.getLimit();
    final Pageable pageable = PageRequest.of(page, limit,
        Sort.by(Sort.Direction.fromString(filterCriteria.getSortDir()), filterCriteria.getSort()));

    final Page<Bus> pagedBuses = this.busRepository.findAll(specification, pageable);
    final Map<Long, List<BusRoute>> busRouteMap = pagedBuses.getContent().stream()
        .collect(Collectors.toMap(Bus::getId, Bus::getBusRouteList, (p1, p2) -> p1));
    final List<BusResponseDto> busResponseDtos = BusMapper.INSTANCE
        .toResponseDtoList(pagedBuses.getContent());
    busResponseDtos.forEach(e -> e.setBusRouteResponseDto(
        BusRouteMapper.INSTANCE.toResponseDtoList(busRouteMap.get(e.getId()))));
    return new PageImpl<>(busResponseDtos, pagedBuses.getPageable(),
        pagedBuses.getTotalElements());
  }

  private void extractTimes(final Bus bus,
      final DateFormat localDateFormat) {
    bus.getBusRouteList().forEach(e -> {
      try {
        if (e.getDepartureTime().after(e.getArrivalTime())) {
          throw new ServiceException("Arrival time cannot be before than departure time",
              HttpStatus.BAD_REQUEST);
        }
        final Time arrivalTime = new Time(
            localDateFormat.parse(localDateFormat.format(e.getArrivalTime())).getTime());
        final Time departureTime = new Time(
            localDateFormat.parse(localDateFormat.format(e.getDepartureTime())).getTime());
        e.setArrivalTime(arrivalTime);
        e.setDepartureTime(departureTime);
        e.setBus(bus);
      } catch (ParseException parseException) {
        parseException.printStackTrace();
      }
    });
  }
}
