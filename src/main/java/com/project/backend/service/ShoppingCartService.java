package com.project.backend.service;

import com.project.backend.dto.ShoppingCartDto;
import com.project.backend.model.Product;
import com.project.backend.model.ShoppingCart;
import com.project.backend.model.User;
import com.project.backend.repository.ShoppingCartRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.userRepository = userRepository;
    }

    // Retrieve shopping cart items for the user id given
    public List<ShoppingCart> getAllShoppingCartsByUserId(Long userId) {
        // get shopping cart items order by ID DESC
        return shoppingCartRepository.findAllByUser_UserIdOrderByShoppingCartIdDesc(userId);
    }

    // Add shopping cart item or update quantity
    public ShoppingCart addShoppingCartOrUpdateQuantity(ShoppingCart shoppingCart) {
        ShoppingCart gotShoppingCart = shoppingCartRepository.findTopByUser_UserIdAndProduct_ProductId(
                shoppingCart.getUser().getUserId(),
                shoppingCart.getProduct().getProductId()
        ).orElse(null);
        // if the shopping cart item already exists, add quantity to it
        if (gotShoppingCart != null) {
            Integer newQuantity = gotShoppingCart.getQuantity() + shoppingCart.getQuantity();
            // maximum quantity is 10
            if (newQuantity <= 10)
                gotShoppingCart.setQuantity(newQuantity);
            return shoppingCartRepository.save(gotShoppingCart);
        }
        // Create shopping cart item if not existed
        return shoppingCartRepository.save(shoppingCart);
    }

    // Update shopping cart item
    public ShoppingCart updateShoppingCartQuantityAndChecked(ShoppingCart shoppingCart) {
        // retrieve shopping cart in db
        ShoppingCart shoppingCartToUpdate = shoppingCartRepository.findById(shoppingCart.getShoppingCartId()).orElse(null);
        // validation
        if (shoppingCartToUpdate != null) {
            // maximum quantity is 10
            if (shoppingCart.getQuantity() > 10)
                shoppingCartToUpdate.setQuantity(10);
            else
                shoppingCartToUpdate.setQuantity(shoppingCart.getQuantity());
            shoppingCartToUpdate.setChecked(shoppingCart.getChecked());
            return shoppingCartRepository.save(shoppingCartToUpdate);
        }
        return null;
    }

    // Delete shopping cart item
    public ShoppingCart deleteShoppingCart(Long id) {
        ShoppingCart shoppingCartToDelete = shoppingCartRepository.findById(id).orElse(null);
        // validation
        if (shoppingCartToDelete != null) {
            shoppingCartRepository.delete(shoppingCartToDelete);
        }
        return shoppingCartToDelete;
    }

    // Delete all shopping cart item
    public void clearShoppingCartByUserId(Long userId) {
        List<ShoppingCart> shoppingCartsToDelete = shoppingCartRepository.findAllByUser_UserIdOrderByShoppingCartIdDesc(userId);
        shoppingCartRepository.deleteInBatch(shoppingCartsToDelete);
    }

    // Check all shopping cart items by user id
    public List<ShoppingCart> checkAllShoppingCartsByUserId(Long userId) {
        // get all by user id
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUser_UserIdOrderByShoppingCartIdDesc(userId);
        // update checked of shopping cart items to true
        for (ShoppingCart shoppingCart : shoppingCarts) {
            shoppingCart.setChecked(true);
        }
        return shoppingCartRepository.saveAll(shoppingCarts);
    }

    // Un-check all shopping cart items by user id
    public List<ShoppingCart> unCheckAllShoppingCartsByUserId(Long userId) {
        // get all by user id
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUser_UserIdOrderByShoppingCartIdDesc(userId);
        // update checked of shopping cart items to false
        for (ShoppingCart shoppingCart : shoppingCarts) {
            shoppingCart.setChecked(false);
        }
        return shoppingCartRepository.saveAll(shoppingCarts);
    }


    /**
     * Entity to Dto conversion
     * @param shoppingCart
     * @return shoppingCartDto
     */
    public ShoppingCartDto shoppingCartEntityToDto(ShoppingCart shoppingCart) {
        if (shoppingCart != null) {
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(
                    shoppingCart.getShoppingCartId(),
                    productService.productEntityToDto(shoppingCart.getProduct()),
                    shoppingCart.getUser().getUserId(),
                    shoppingCart.getQuantity(),
                    shoppingCart.getChecked()
            );
            return shoppingCartDto;
        }
        return null;
    }

    /**
     * Dto to Entity conversion
     * @param shoppingCartDto
     * @return shoppingCart
     */
    public ShoppingCart shoppingCartDtoToEntity(ShoppingCartDto shoppingCartDto) {
        if (shoppingCartDto != null) {
            // get product and user by respective id
            Product product = productService.getProductById(shoppingCartDto.getProductDto().getProductId());
            User user = userRepository.findById(shoppingCartDto.getUserId()).orElse(null);
            // validate if both fields' objects exist
            if (product != null && user != null) {
                ShoppingCart shoppingCart = new ShoppingCart(
                        shoppingCartDto.getShoppingCartId(),
                        product,
                        user,
                        shoppingCartDto.getQuantity(),
                        shoppingCartDto.getChecked()
                );
                return shoppingCart;
            }
        }
        return null;
    }
}
