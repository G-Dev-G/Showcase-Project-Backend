package com.project.backend.controller;

import com.project.backend.dto.ShoppingCartDto;
import com.project.backend.model.ShoppingCart;
import com.project.backend.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/shoppingCart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/getAllByUserId/{userId}")
    public List<ShoppingCartDto> getAllShoppingCartsByUserId(@PathVariable(value="userId") Long userId) {
        List<ShoppingCartDto> shoppingCartDtoList = new ArrayList<>();

        for (ShoppingCart shoppingCart : shoppingCartService.getAllShoppingCartsByUserId(userId)) {
            shoppingCartDtoList.add(shoppingCartService.shoppingCartEntityToDto(shoppingCart));
        }
        return shoppingCartDtoList;
    }

    @PutMapping("/addOrUpdateQuantity")
    public ShoppingCartDto addShoppingCartOrUpdateQuantity(@RequestBody ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = shoppingCartService.shoppingCartDtoToEntity(shoppingCartDto);
        ShoppingCart shoppingCartSet = shoppingCartService.addShoppingCartOrUpdateQuantity(shoppingCart);
        return shoppingCartService.shoppingCartEntityToDto(shoppingCartSet); // always return DTO
    }

    @PutMapping("/update")
    public ShoppingCartDto updateShoppingCart(@RequestBody ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = shoppingCartService.shoppingCartDtoToEntity(shoppingCartDto);
        ShoppingCart shoppingCartUpdated = shoppingCartService.updateShoppingCartQuantityAndChecked(shoppingCart);
        return shoppingCartService.shoppingCartEntityToDto(shoppingCartUpdated);
    }

    @DeleteMapping("/delete/{id}")
    public ShoppingCartDto deleteShoppingCart(@PathVariable(value="id") Long id) {
        ShoppingCart deletedShoppingCart = shoppingCartService.deleteShoppingCart(id);
        return shoppingCartService.shoppingCartEntityToDto(deletedShoppingCart);
    }

    @DeleteMapping("/deleteAllByUserId")
    public Map<String, String> clearShoppingCartByUserId(@RequestParam("userId") Long userId) {
        // delete all
        shoppingCartService.clearShoppingCartByUserId(userId);
        return Collections.singletonMap("msg", "success");
    }

    @PutMapping("/checkAllByUserId")
    public List<ShoppingCartDto> checkAllShoppingCartsByUserId(@RequestParam("userId") Long userId) {
        List<ShoppingCartDto> shoppingCartDtoList = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCartService.checkAllShoppingCartsByUserId(userId)) {
            shoppingCartDtoList.add(shoppingCartService.shoppingCartEntityToDto(shoppingCart));
        }
        return shoppingCartDtoList;
    }

    @PutMapping("/unCheckAllByUserId")
    public List<ShoppingCartDto> unCheckAllShoppingCartsByUserId(@RequestParam("userId") Long userId) {
        List<ShoppingCartDto> shoppingCartDtoList = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCartService.unCheckAllShoppingCartsByUserId(userId)) {
            shoppingCartDtoList.add(shoppingCartService.shoppingCartEntityToDto(shoppingCart));
        }
        return shoppingCartDtoList;
    }
}
