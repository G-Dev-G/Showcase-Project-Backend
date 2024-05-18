package com.project.backend.service;

import com.project.backend.dto.ProductDto;
import com.project.backend.model.Product;
import com.project.backend.model.ProductImage;
import com.project.backend.repository.ProductImageRepository;
import com.project.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    // retrieve all existing products
    public List<Product> getAllExistingProducts() {
        return productRepository.findAllExistingOrderByProductId();
    }

    // retrieve existing products conditionally
    public List<Product> getExistingProductsByCriteria(String category, String priceSortBy) {
        // CASE 1: category is not provided
        if (category.length() == 0)
        {
            switch (priceSortBy)
            {
                // value sent from frontend
                case "priceAsc":
                    return productRepository.findAllExistingOrderByPrice();
                case "priceDesc":
                    return productRepository.findAllExistingOrderByPriceDesc();
                default:
                    return productRepository.findAllExistingOrderByProductId();
            }
        }
        // CASE 2: category is selected
        else
        {
            switch (priceSortBy)
            {
                // value sent from frontend
                case "priceAsc":
                    return productRepository.findAllExistingByCategoryOrderByPrice(category);
                case "priceDesc":
                    return productRepository.findAllExistingByCategoryOrderByPriceDesc(category);
                default:
                    return productRepository.findAllExistingByCategoryOrderByProductId(category);
            }
        }
    }

    // Retrieve product By Id
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Add product with product images included
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Update product including product images
    public Product updateProduct(Product product) {
        // retrieve product in db
        Product productToUpdate = getProductById(product.getProductId());
        // validation
        if (productToUpdate != null) {
            // delete/reset associated images
            // 1) get product images
            List<ProductImage> productImages = productToUpdate.getProductImages();
            // 2) delete
            productImageRepository.deleteInBatch(productImages);

            // update
            productToUpdate.setShortName(product.getShortName());
            productToUpdate.setFullName(product.getFullName());
            productToUpdate.setCategory(product.getCategory());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setProductImages(product.getProductImages());
            return productRepository.save(productToUpdate);
        }
        return null;
    }

    // Delete product
    public Product deleteProduct(Long id) {
        Product productToDelete = getProductById(id);

        // validation
        if (productToDelete != null) {
            productToDelete.setDeletedAt(new Date()); // update Deleted At timestamp to indicate deletion
            return productRepository.save(productToDelete);
        }
        return null;
    }

    /**
     * Entity to Dto conversion
     * @param product
     * @return productDto
     */
    public ProductDto productEntityToDto(Product product) {
        if (product != null) {
            ProductDto productDto = new ProductDto(
                    product.getProductId(),
                    product.getShortName(),
                    product.getFullName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategory(),
                    product.getProductImages().stream().map(productImage -> productImage.getImage()).collect(Collectors.toList()) // extract images as list
            );
            return productDto;
        }
        return null;
    }

    /**
     * Dto to Entity conversion
     * @param productDto
     * @return product
     */
    public Product productDtoToEntity(ProductDto productDto) {
        if (productDto != null) {
            Product product = new Product(
                    productDto.getProductId(),
                    productDto.getShortName(),
                    productDto.getFullName(),
                    productDto.getDescription(),
                    productDto.getPrice(),
                    productDto.getCategory()
            );
            // extract product images
            List<ProductImage> productImageList = new ArrayList<>();
            for (int i = 0; i < productDto.getImages().size(); i++) {
                // set foreign key into (product)
                productImageList.add(new ProductImage(product, productDto.getImages().get(i)));
            }
            // set associated images
            product.setProductImages(productImageList);
            return product;
        }
        return null;
    }
}
