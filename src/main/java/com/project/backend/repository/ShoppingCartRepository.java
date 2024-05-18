package com.project.backend.repository;

import com.project.backend.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    // FindAllBy + OrderBy
    List<ShoppingCart> findAllByUser_UserIdOrderByShoppingCartIdDesc(Long userId);

    // Find potential shopping cart item based on userId and productId
    Optional<ShoppingCart> findTopByUser_UserIdAndProduct_ProductId(Long userId, Long productId);
}
