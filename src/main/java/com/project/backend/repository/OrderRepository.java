package com.project.backend.repository;

import com.project.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // OrderBy Desc
    List<Order> findAllByOrderByOrderIdDesc();

    // FindBy + OrderBy
    @Query(value = "SELECT o FROM Order o INNER JOIN o.user u WHERE o.deletedAt = NULL AND u.userId = :userId ORDER BY o.orderId DESC")
    List<Order> findAllExistingByUser_UserIdOrderByOrderIdDesc(@Param("userId") Long userId);
}
