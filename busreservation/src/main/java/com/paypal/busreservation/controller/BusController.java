package com.paypal.busreservation.controller;


import com.paypal.busreservation.constants.ApiConstants;
import com.paypal.busreservation.dto.BusResponseDto;
import com.paypal.busreservation.dto.CreateBusDto;
import com.paypal.busreservation.dto.CreateBusTripDto;
import com.paypal.busreservation.dto.FilterCriteria;
import com.paypal.busreservation.service.BusService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = ApiConstants.BUS_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class BusController {

  @Autowired
  private BusService busService;

  @ApiOperation(value = ApiConstants.API_BUS,
      notes = ApiConstants.API_BUS_NOTES)
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = ApiConstants.SAVE_SUCCESS),
          @ApiResponse(code = 500, message = ApiConstants.SAVE_FAILURE)})
  @PostMapping(ApiConstants.CREATE_BUS)
  public ResponseEntity<BusResponseDto> createBus(@RequestBody @Valid CreateBusDto createBusDto) {
    return new ResponseEntity<>(this.busService.createBus(createBusDto),HttpStatus.CREATED);
  }

  @ApiOperation(value = ApiConstants.API_BUS_TRIP,
      notes = ApiConstants.API_BUS_TRIP_NOTES)
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = ApiConstants.SAVE_SUCCESS),
          @ApiResponse(code = 500, message = ApiConstants.SAVE_FAILURE)})
  @PostMapping(ApiConstants.CREATE_TRIP)
  public ResponseEntity<Void> createBusTrip
      (@RequestBody @Valid CreateBusTripDto createBusTripDto) {
    this.busService.createBusTrip(createBusTripDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }


  @ApiOperation(value = ApiConstants.API_BUS,
      notes = ApiConstants.API_BUS_NOTES)
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = ApiConstants.GET_SUCCESS),
          @ApiResponse(code = 500, message = ApiConstants.GET_FAILURE)})
  @GetMapping(ApiConstants.GET_BUS)
  public ResponseEntity<PageImpl<BusResponseDto>> getBuses(
      @RequestParam(name = ApiConstants.API_RP_SEARCH, required = false) String fullTextSearch,
      @RequestParam(name = ApiConstants.API_RP_QUERY, required = false) String query,
      @RequestParam(name = ApiConstants.API_RP_SORT, required = false, defaultValue = ApiConstants.DEFAULT_SORT_COLUMN) String sort,
      @RequestParam(name = ApiConstants.API_RP_SORT_DIR, required = false, defaultValue = ApiConstants.DEFAULT_SORT_DIRECTION)
      @ApiParam(allowableValues = ApiConstants.SORT_DIR_ALLOWABLE_VALUES) String sortDir,
      @RequestParam(name = ApiConstants.API_RP_PAGE, required = false, defaultValue = ApiConstants.DEFAULT_PAGE) int page,
      @RequestParam(name = ApiConstants.API_RP_LIMIT, required = false, defaultValue = ApiConstants.DEFAULT_PAGE_LIMIT) int limit) {
    final FilterCriteria filterCriteria = new FilterCriteria(fullTextSearch, query, page, limit,
        sort, sortDir, "");
    log.debug(String.format("Incoming request to search buses with {{}}", filterCriteria));
    return new ResponseEntity<>(this.busService.getBuses(filterCriteria), HttpStatus.OK);
  }

}
