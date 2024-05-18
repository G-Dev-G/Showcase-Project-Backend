package com.project.backend.repository;

import com.project.backend.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    // FindAllBy + OrderBy
    List<Favorite> findAllByUser_UserIdOrderByFavoriteId(Long userId);
}
