package com.project.backend.service;

import com.project.backend.dto.FavoriteDto;
import com.project.backend.model.Favorite;
import com.project.backend.model.Product;
import com.project.backend.model.User;
import com.project.backend.repository.FavoriteRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository, ProductService productService, UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.productService = productService;
        this.userRepository = userRepository;
    }

    // Retrieve favorite items for the user id given
    public List<Favorite> getAllFavoritesByUserId(Long userId) {
        // get favorite items order by ID ASC
        return favoriteRepository.findAllByUser_UserIdOrderByFavoriteId(userId);
    }

    // Add favorite item
    public Favorite addFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    // Delete favorite item by primary key
    public Favorite deleteFavorite(Long id) {
        Favorite favoriteToDelete = favoriteRepository.findById(id).orElse(null);
        // validation
        if (favoriteToDelete != null) {
            favoriteRepository.delete(favoriteToDelete);
        }
        return favoriteToDelete;
    }

    /**
     * Entity to Dto conversion
     * @param favorite
     * @return favoriteDto
     */
    public FavoriteDto favoriteEntityToDto(Favorite favorite) {
        if (favorite != null) {
            FavoriteDto favoriteDto = new FavoriteDto(
                    favorite.getFavoriteId(),
                    productService.productEntityToDto(favorite.getProduct()),
                    favorite.getUser().getUserId()
            );
            return favoriteDto;
        }
        return null;
    }

    /**
     * Dto to Entity conversion
     * @param favoriteDto
     * @return favorite
     */
    public Favorite favoriteDtoToEntity(FavoriteDto favoriteDto) {
        if (favoriteDto != null) {
            // get product and user by id
            Product product = productService.getProductById(favoriteDto.getProductDto().getProductId());
            User user = userRepository.findById(favoriteDto.getUserId()).orElse(null);
            // validate if they exist
            if (product != null && user != null) {
                Favorite favorite = new Favorite(
                        favoriteDto.getFavoriteId(),
                        product,
                        user
                );
                return favorite;
            }
        }
        return null;
    }
}
