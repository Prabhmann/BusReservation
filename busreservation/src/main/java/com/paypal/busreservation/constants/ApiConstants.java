package com.paypal.busreservation.constants;

import com.paypal.busreservation.dto.SearchOperation;

public class ApiConstants {

  public static final String SEPARATOR = "/";
  public static final String BUS_API = SEPARATOR + "bus" ;
  public static final String BOOKING_API = SEPARATOR + "booking" ;

  public static final String API_BUS = "Create Bus";
  public static final String API_BUS_NOTES = "API to create bus";
  public static final String API_BUS_BOOKING = "Create bus booking";
  public static final String API_BUS_BOOKING_NOTES = "API to create bus booking";
  public static final String API_BUS_GET_SEATS = "get bus seats";
  public static final String API_BUS_GET_SEATS_NOTES = "API to get bus seats";
  public static final String API_BUS_GET_TRIPS = "get bus trips";
  public static final String API_BUS_GET_TRIPS_NOTES = "API to get bus trips";
  public static final String API_BUS_TRIP = "Create Bus trip";
  public static final String API_BUS_TRIP_NOTES = "API to create bus trip";
  public static final String SAVE_SUCCESS = "Created successfully";
  public static final String SAVE_FAILURE = "Unable to create bus";
  public static final String GET_SUCCESS = "fetched successfully";
  public static final String GET_FAILURE = "unable to fetch buses";
  public static final String CREATE_BUS = SEPARATOR + "create-bus";
  public static final String CREATE_TRIP = SEPARATOR + "create-trip";
  public static final String CREATE_BOOKING = SEPARATOR + "create-booking";
  public static final String GET_BUS = SEPARATOR + "get-bus";
  public static final String GET_TRIP = SEPARATOR + "get-trip";
  public static final String GET_SEATS = SEPARATOR + "get-seats/{id}";
  public static final String API_RP_SEARCH = "search";
  public static final String API_RP_QUERY = "query";
  public static final String API_RP_FILTER = "filter";
  public static final String API_RP_PAGE = "page";
  public static final String API_RP_SORT = "sort";
  public static final String API_RP_SORT_DIR = "sortDir";
  public static final String SORT_DIR_ALLOWABLE_VALUES = "asc";
  public static final String DEFAULT_SORT_DIRECTION = "DESC";
  public static final String DEFAULT_SORT_COLUMN = "operatorName";
  public static final String DEFAULT_SORT_COLUMN_SOURCE = "source";
  public static final String API_RP_LIMIT = "limit";
  public static final String DEFAULT_PAGE_LIMIT = "10";
  public static final String DEFAULT_PAGE = "0";

  public static final String COMMA = ",";
  public static final String PIPE = "|";
  public static final String WORDS_HYPHEN_SPACE_REGEX = "([\\w-. ]+)";
  public static final String WORDS_HYPHEN_SPACE_DOT_REGEX = "([\\w-:. ]+)";
  public static final String FILTER_CRITERIA_REGEX = String.format(
      "%s(%s)%s,",
      WORDS_HYPHEN_SPACE_REGEX, SearchOperation.getOperationSet(), WORDS_HYPHEN_SPACE_REGEX);
  public static final String DELETED = "isDeleted";
  public static final String BUS_NUMBER = "busNumber";
  public static final String DESTINATION = "destination";
  public static final String SOURCE = "source";
  public static final String OPERATOR_NAME = "operatorName";
  public static final String TRAVEL_DATE = "travelDate";
}
