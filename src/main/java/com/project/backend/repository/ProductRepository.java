package com.project.backend.repository;

import com.project.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> // for test purpose
{
    // Default
    @Query(value = "SELECT p FROM Product p WHERE p.deletedAt = NULL ORDER BY p.productId")
    List<Product> findAllExistingOrderByProductId();

    // ============== Order By ============================
    @Query(value = "SELECT p FROM Product p WHERE p.deletedAt = NULL ORDER BY p.price")
    List<Product> findAllExistingOrderByPrice();

    @Query(value = "SELECT p FROM Product p WHERE p.deletedAt = NULL ORDER BY p.price DESC")
    List<Product> findAllExistingOrderByPriceDesc();

    // ============== Find By Category + Order By =========
    @Query(value = "SELECT p FROM Product p WHERE p.deletedAt = NULL AND p.category = :category ORDER BY p.productId")
    List<Product> findAllExistingByCategoryOrderByProductId(@Param("category") String category);

    @Query(value = "SELECT p FROM Product p WHERE p.deletedAt = NULL AND p.category = :category ORDER BY p.price")
    List<Product> findAllExistingByCategoryOrderByPrice(@Param("category") String category);

    @Query(value = "SELECT p FROM Product p WHERE p.deletedAt = NULL AND p.category = :category ORDER BY p.price DESC")
    List<Product> findAllExistingByCategoryOrderByPriceDesc(@Param("category") String category);
}
