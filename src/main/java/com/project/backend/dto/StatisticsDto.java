package com.project.backend.dto;

/**
 * Projection - Fetch custom columns
 */
public interface StatisticsDto {
    Long getProductId();
    String getShortName();
    String getCategory();
    Integer getSum(); // ordered quantity of the product
}
