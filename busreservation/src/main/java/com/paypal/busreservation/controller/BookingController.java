package com.paypal.busreservation.controller;


import com.paypal.busreservation.constants.ApiConstants;
import com.paypal.busreservation.dto.BookingDto;
import com.paypal.busreservation.dto.BusTripResponseDto;
import com.paypal.busreservation.dto.FilterCriteria;
import com.paypal.busreservation.entity.Booking;
import com.paypal.busreservation.entity.BusTrip;
import com.paypal.busreservation.service.BookingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = ApiConstants.BOOKING_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingController {

  @Autowired
  private BookingService bookingService;

  @ApiOperation(value = ApiConstants.API_BUS_BOOKING,
      notes = ApiConstants.API_BUS_BOOKING_NOTES)
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = ApiConstants.SAVE_SUCCESS),
          @ApiResponse(code = 500, message = ApiConstants.SAVE_FAILURE)})
  @PostMapping(ApiConstants.CREATE_BOOKING)
  public ResponseEntity<Booking> createBooking(@RequestBody @Valid BookingDto bookingDto) {
    return new ResponseEntity<>(this.bookingService.createBooking(bookingDto), HttpStatus.OK);
  }

  @ApiOperation(value = ApiConstants.API_BUS_BOOKING,
      notes = ApiConstants.API_BUS_BOOKING_NOTES)
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = ApiConstants.GET_SUCCESS),
          @ApiResponse(code = 500, message = ApiConstants.GET_FAILURE)})
  @GetMapping(ApiConstants.GET_SEATS)
  public ResponseEntity<BusTripResponseDto> getAvailableSeats(@PathVariable final Integer id) {
    return new ResponseEntity<>(this.bookingService.getSeats(id), HttpStatus.OK);
  }


  @ApiOperation(value = ApiConstants.API_BUS_GET_TRIPS,
      notes = ApiConstants.API_BUS_GET_TRIPS_NOTES)
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = ApiConstants.GET_SUCCESS),
          @ApiResponse(code = 500, message = ApiConstants.GET_FAILURE)})
  @GetMapping(ApiConstants.GET_TRIP)
  public ResponseEntity<PageImpl<BusTripResponseDto>> getTrips(@RequestParam(name = ApiConstants.API_RP_SEARCH, required = false) String fullTextSearch,
      @RequestParam(name = ApiConstants.API_RP_QUERY, required = false) String query,
      @RequestParam(name = ApiConstants.API_RP_SORT, required = false, defaultValue = ApiConstants.DEFAULT_SORT_COLUMN_SOURCE) String sort,
      @RequestParam(name = ApiConstants.API_RP_SORT_DIR, required = false, defaultValue = ApiConstants.DEFAULT_SORT_DIRECTION)
      @ApiParam(allowableValues = ApiConstants.SORT_DIR_ALLOWABLE_VALUES) String sortDir,
      @RequestParam(name = ApiConstants.API_RP_PAGE, required = false, defaultValue = ApiConstants.DEFAULT_PAGE) int page,
      @RequestParam(name = ApiConstants.API_RP_LIMIT, required = false, defaultValue = ApiConstants.DEFAULT_PAGE_LIMIT) int limit){
    final FilterCriteria filterCriteria = new FilterCriteria(fullTextSearch, query, page, limit,
        sort, sortDir, "");
    log.debug("Incoming request to search buses with {}",
        filterCriteria);
    return new ResponseEntity<>(this.bookingService.searchTrips(filterCriteria),HttpStatus.OK);
  }


}
