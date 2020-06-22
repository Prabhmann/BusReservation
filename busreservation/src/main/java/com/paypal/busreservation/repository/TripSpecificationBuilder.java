package com.paypal.busreservation.repository;

import com.paypal.busreservation.constants.ApiConstants;
import com.paypal.busreservation.dto.SearchCriteria;
import com.paypal.busreservation.dto.SearchOperation;
import com.paypal.busreservation.entity.BusTrip;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class TripSpecificationBuilder {

  private final String fullTextSearch;
  private final List<SearchCriteria> params;

  public TripSpecificationBuilder(final String fullTextSearch,
      final List<SearchCriteria> params) {
    this.fullTextSearch = fullTextSearch;
    this.params = params;
  }

  public Specification<BusTrip> build() {
    Specification<BusTrip> spec = Specification.where(
        null);

    for (SearchCriteria criteria : params) {
      spec = spec.and(new TripSpecification(criteria));
    }
    spec = spec.and(buildFullTextSearchSpecification());
    return spec;
  }

  public Specification<BusTrip> buildFullTextSearchSpecification() {
    if (StringUtils.isNoneEmpty(fullTextSearch)) {
      return Specification
          .where(
              new TripSpecification(
                  new SearchCriteria(ApiConstants.SOURCE, SearchOperation.LIKE,
                      fullTextSearch))
                  .or(new TripSpecification(
                      new SearchCriteria(ApiConstants.DESTINATION, SearchOperation.LIKE,
                          fullTextSearch)).or(new TripSpecification(
                      new SearchCriteria(ApiConstants.TRAVEL_DATE, SearchOperation.LIKE,
                          fullTextSearch)))));
    }
    return null;
  }

  public Specification<BusTrip> getAttributeSpecification(final String attribute,
      final Object value) {
    return (root, query, cb) -> cb
        .equal(root.get(attribute).as(String.class), String.valueOf(value));
  }
}
