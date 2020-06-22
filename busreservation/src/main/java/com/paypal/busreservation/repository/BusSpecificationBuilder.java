package com.paypal.busreservation.repository;

import com.paypal.busreservation.constants.ApiConstants;
import com.paypal.busreservation.constants.EntityConstants.Columns;
import com.paypal.busreservation.dto.SearchCriteria;
import com.paypal.busreservation.dto.SearchOperation;
import com.paypal.busreservation.entity.Bus;
import io.swagger.annotations.Api;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * Contact specification builder
 */
public class BusSpecificationBuilder {

  private final String fullTextSearch;
  private final List<SearchCriteria> params;

  public BusSpecificationBuilder(final String fullTextSearch,
      final List<SearchCriteria> params) {
    this.fullTextSearch = fullTextSearch;
    this.params = params;
  }

  public Specification<Bus> build() {
    Specification<Bus> spec = Specification.where(
        getAttributeSpecification(ApiConstants.DELETED, Boolean.FALSE));

    for (SearchCriteria criteria : params) {
      spec = spec.and(new BusSpecification(criteria));
    }
    spec = spec.and(buildFullTextSearchSpecification());
    return spec;
  }

  public Specification<Bus> buildFullTextSearchSpecification() {
    if (StringUtils.isNoneEmpty(fullTextSearch)) {
      return Specification
          .where(
              new BusSpecification(
                  new SearchCriteria(ApiConstants.BUS_NUMBER, SearchOperation.LIKE,
                      fullTextSearch))
                  .or(new BusSpecification(
                      new SearchCriteria(ApiConstants.OPERATOR_NAME, SearchOperation.LIKE,
                          fullTextSearch))));
    }
    return null;
  }

  public Specification<Bus> getAttributeSpecification(final String attribute,
      final Object value) {
    return (root, query, cb) -> cb
        .equal(root.get(attribute).as(String.class), String.valueOf(value));
  }
}