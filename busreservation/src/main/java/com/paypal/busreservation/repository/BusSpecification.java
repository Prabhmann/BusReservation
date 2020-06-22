package com.paypal.busreservation.repository;

import com.paypal.busreservation.dto.SearchCriteria;
import com.paypal.busreservation.entity.Bus;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BusSpecification implements Specification<Bus> {

  private static final String WILDCARD = "%";
  private SearchCriteria criteria;

  public BusSpecification(final SearchCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate(Root<Bus> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    switch (criteria.getOperation()) {
      case EQUALITY:
        return criteriaBuilder
            .equal(criteriaBuilder.lower(root.get(criteria.getKey()).as(String.class)),
                criteria.getValue().toString().toLowerCase());
      case NEGATION:
        return criteriaBuilder
            .notEqual(criteriaBuilder.lower(root.get(criteria.getKey()).as(String.class)),
                criteria.getValue().toString().toLowerCase());
      case GREATER_THAN:
        return criteriaBuilder
            .greaterThan(root.get(criteria.getKey()).as(String.class),
                criteria.getValue().toString());
      case GREATER_THAN_OR_EQUAL_TO:
        return criteriaBuilder
            .greaterThanOrEqualTo(root.get(criteria.getKey()).as(String.class),
                criteria.getValue().toString());
      case LESS_THAN:
        return criteriaBuilder
            .lessThan(root.get(criteria.getKey()).as(String.class), criteria.getValue().toString());
      case LESS_THAN_OR_EQUAL_TO:
        return criteriaBuilder
            .lessThanOrEqualTo(root.get(criteria.getKey()).as(String.class),
                criteria.getValue().toString());
      case LIKE:
        return criteriaBuilder
            .like(criteriaBuilder.lower(root.get(criteria.getKey()).as(String.class)),
                containsLowerCase(criteria.getValue().toString()));
      default:
        return null;
    }
  }

  public static String containsLowerCase(final String searchField) {
    return WILDCARD + searchField.toLowerCase() + WILDCARD;
  }

}
