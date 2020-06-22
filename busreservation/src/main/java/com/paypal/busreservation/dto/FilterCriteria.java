package com.paypal.busreservation.dto;

import com.paypal.busreservation.constants.ApiConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Filter criteria.
 */
@Data
@AllArgsConstructor
public class FilterCriteria {

  private final String fullTextSearch;
  private final String query;
  private final int page;
  private final int limit;
  private final String sort;
  private final String sortDir;
  private final String filter;

  public List<SearchCriteria> getCriterion() {
    if (!hasQuery()) {
      return Collections.emptyList();
    }

    final List<SearchCriteria> params = new ArrayList<>();
    final Pattern pattern = Pattern.compile(ApiConstants.FILTER_CRITERIA_REGEX);
    final Matcher matcher = pattern.matcher(query + ApiConstants.COMMA);

    while (matcher.find()) {
      params.add(
          new SearchCriteria(matcher.group(1), SearchOperation.getOperation(matcher.group(2)),
              matcher.group(3)));
    }
    return params;
  }

  public List<SearchCriteria> getFilters(){
    if (!hasFilter()) {
      return Collections.emptyList();
    }

    final List<SearchCriteria> params = new ArrayList<>();
    final Pattern pattern = Pattern.compile(ApiConstants.FILTER_CRITERIA_REGEX);
    final Matcher matcher = pattern.matcher(filter + ApiConstants.COMMA);

    while (matcher.find()) {
      params.add(
          new SearchCriteria(matcher.group(1), SearchOperation.getOperation(matcher.group(2)),
              matcher.group(3)));
    }
    return params;
  }

  public boolean hasQuery() {
    return StringUtils.isNotEmpty(query);
  }
  public boolean hasFilter() {
    return StringUtils.isNotEmpty(filter);
  }
}
