package com.project.backend.repository;

import com.project.backend.dto.StatisticsDto;
import com.project.backend.model.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Date;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "SELECT p.productId as productId, p.shortName as shortName, p.category as category, SUM(i.quantity) as sum " +
            "FROM OrderItem i INNER JOIN i.product p INNER JOIN i.order o WHERE o.orderDate > :earliestDate GROUP BY p.productId ORDER BY SUM(i.quantity) DESC")
    List<StatisticsDto> findAllStatisticsGroupByProductOrderByQtyDescAfterDate(@Param("earliestDate") Date earliestDate, Pageable pageable);

    @Query(value = "SELECT p.productId as productId, p.shortName as shortName, p.category as category, SUM(i.quantity) as sum " +
            "FROM OrderItem i INNER JOIN i.product p INNER JOIN i.order o WHERE p.category = :category AND o.orderDate > :earliestDate GROUP BY p.productId ORDER BY SUM(i.quantity) DESC")
    List<StatisticsDto> findAllStatisticsGroupByProductByCategoryOrderByQtyDescAfterDate(@Param("category") String category, @Param("earliestDate") Date earliestDate, Pageable pageable);
}
