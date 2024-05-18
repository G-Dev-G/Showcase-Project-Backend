package com.project.backend.controller;

import com.project.backend.dto.FavoriteDto;
import com.project.backend.model.Favorite;
import com.project.backend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/getAllByUserId/{userId}")
    public List<FavoriteDto> getAllFavoritesByUserId(@PathVariable(value="userId") Long userId) {
        List<FavoriteDto> favoriteDtoList = new ArrayList<>();

        for (Favorite favorite : favoriteService.getAllFavoritesByUserId(userId)) {
            favoriteDtoList.add(favoriteService.favoriteEntityToDto(favorite));
        }
        return favoriteDtoList;
    }

    @PostMapping("/add")
    public FavoriteDto addFavorite(@RequestBody FavoriteDto favoriteDto) {
        Favorite favorite = favoriteService.favoriteDtoToEntity(favoriteDto);
        Favorite favoriteAdded = favoriteService.addFavorite(favorite);
        return favoriteService.favoriteEntityToDto(favoriteAdded); // always return DTO
    }

    @DeleteMapping("/delete/{id}")
    public FavoriteDto deleteFavorite(@PathVariable(value="id") Long id) {
        Favorite deletedFavorite = favoriteService.deleteFavorite(id);
        return favoriteService.favoriteEntityToDto(deletedFavorite);
    }
}
