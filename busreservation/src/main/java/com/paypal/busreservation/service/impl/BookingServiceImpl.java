package com.paypal.busreservation.service.impl;

import com.paypal.busreservation.dto.BookingDto;
import com.paypal.busreservation.dto.BusTripResponseDto;
import com.paypal.busreservation.dto.FilterCriteria;
import com.paypal.busreservation.dto.SearchCriteria;
import com.paypal.busreservation.entity.Booking;
import com.paypal.busreservation.entity.BookingDetails;
import com.paypal.busreservation.entity.BusTrip;
import com.paypal.busreservation.exception.ServiceException;
import com.paypal.busreservation.mapper.BookingMapper;
import com.paypal.busreservation.mapper.BusTripMapper;
import com.paypal.busreservation.repository.BookingDetailsRepository;
import com.paypal.busreservation.repository.BookingRepository;
import com.paypal.busreservation.repository.BusTripRepository;
import com.paypal.busreservation.repository.TripSpecificationBuilder;
import com.paypal.busreservation.service.BookingService;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


@Service
public class BookingServiceImpl implements BookingService {

  @Autowired
  BookingRepository bookingRepository;

  @Autowired
  BookingDetailsRepository bookingDetailsRepository;

  @Autowired
  BusTripRepository busTripRepository;

  @Override
  @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
  public Booking createBooking(final BookingDto bookingDto) {
    if (ObjectUtils.isEmpty(bookingDto)) {
      throw new ServiceException("Please fill proper details", HttpStatus.BAD_REQUEST);
    }
    final List<BookingDetails> bookingDetails = this.bookingDetailsRepository
        .findByTripIdAndSeatNumberIn(bookingDto.getTripId(), bookingDto.getSeatNoList());

    if (!CollectionUtils.isEmpty(bookingDetails)) {
      throw new ServiceException("Seats already booked", HttpStatus.CONFLICT);
    }

    final Booking booking = BookingMapper.INSTANCE.toEntity(bookingDto);
    booking.setBookingDetails(this.createBookingDetailObject(bookingDto, booking));


    final Optional<BusTrip> optionalBusTrip = busTripRepository.findById(booking.getTripId());
    if(!optionalBusTrip.isPresent()){
      throw new ServiceException("No trip found", HttpStatus.CONFLICT);
    }
    final BusTrip busTrip = optionalBusTrip.get();
    booking.setTotalPrice(busTrip.getTicketPrice().multiply(new BigDecimal(bookingDto.getSeatNoList().size())).round(
        MathContext.DECIMAL32));
    this.bookingRepository.save(booking);
    this.busTripRepository.save(busTrip);
    return booking;
  }

  @Override
  public PageImpl<BusTripResponseDto> searchTrips(FilterCriteria filterCriteria) {
    List<SearchCriteria> params = filterCriteria.getCriterion();
    if (CollectionUtils.isEmpty(params)) {
      params = new ArrayList<>();
    }
    final TripSpecificationBuilder builder =
        new TripSpecificationBuilder(filterCriteria.getFullTextSearch(), params);

    final Specification<BusTrip> specification = builder.build();
    final int page = filterCriteria.getPage();
    final int limit = filterCriteria.getLimit();
    final Pageable pageable = PageRequest.of(page, limit,
        Sort.by(Sort.Direction.fromString(filterCriteria.getSortDir()), filterCriteria.getSort()));

    final Page<BusTrip> pagedBuses = this.busTripRepository.findAll(specification, pageable);
    final List<BusTripResponseDto> busTripResponseDtos = BusTripMapper.INSTANCE
        .toResponseDtoList(pagedBuses.getContent());
    return new PageImpl<>(busTripResponseDtos,pageable,page);
  }

  @Override
  public BusTripResponseDto getSeats(Integer id) {
    final Optional<BusTrip> optionalBusTrip = this.busTripRepository.findById(Long.valueOf(id));

    if(!optionalBusTrip.isPresent()){
      throw new ServiceException("Trip not found", HttpStatus.BAD_REQUEST);
    }

    final BusTrip busTrip = optionalBusTrip.get();
    final List<BookingDetails> optionalBooking = this.bookingDetailsRepository.findByTripId(id);
     Set<Integer> booked = new HashSet<>();
    if(!CollectionUtils.isEmpty(optionalBooking)){
       booked = optionalBooking.stream().map(BookingDetails::getSeatNumber)
          .collect(Collectors.toSet());
    }
    final BusTripResponseDto busTripResponseDto = BusTripMapper.INSTANCE.toResponseDto(busTrip);
    final Map<Integer,String> seatMap= new HashMap<>();
    for(int i=1;i<=busTrip.getSeatsAvailable();i++){
      if(booked.contains(i)){
        seatMap.put(i,"BOOKED");
      }
      else {
        seatMap.put(i,"AVAILABLE");
      }
    }
    busTripResponseDto.setSeats(seatMap);
    return busTripResponseDto;
  }

  private List<BookingDetails> createBookingDetailObject(BookingDto bookingDto, Booking booking) {
    List<BookingDetails> bookingDetailsList = new ArrayList<>();

    bookingDto.getSeatNoList().forEach(e -> {
      BookingDetails bookingDetails = new BookingDetails();
      bookingDetails.setSeatNumber(e);
      bookingDetails.setTripId(bookingDto.getTripId());
      bookingDetails.setBooking(booking);
      bookingDetailsList.add(bookingDetails);
    });
    return bookingDetailsList;
  }
}
