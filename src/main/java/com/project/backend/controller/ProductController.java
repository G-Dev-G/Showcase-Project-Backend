package com.project.backend.controller;

import com.project.backend.dto.ProductDto;
import com.project.backend.model.Product;
import com.project.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/product")
public class ProductController {
    private final ProductService productService;

    @Autowired // inject the productService
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public List<ProductDto> getAllExistingProducts() {
        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : productService.getAllExistingProducts()) {
            productDtoList.add(productService.productEntityToDto(product));
        }
        return productDtoList;
    }

    /**
     * Get products WHERE + ORDER BY
     * @param category
     * @param priceSortBy
     * @return
     */
    @GetMapping("/getAllByCriteria")
    public List<ProductDto> getAllExistingProductsByCriteria(@RequestParam("category") String category,
                                                             @RequestParam("priceSortBy") String priceSortBy)
    {
        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : productService.getExistingProductsByCriteria(category, priceSortBy))
            productDtoList.add(productService.productEntityToDto(product));

        return productDtoList;
    }

    @GetMapping("/getById/{id}")
    public ProductDto getProductById(@PathVariable(value="id") Long id) {
        Product gotProduct = productService.getProductById(id);
        return productService.productEntityToDto(gotProduct);
    }

    @PostMapping("/add")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        Product product = productService.productDtoToEntity(productDto);
        Product productAdded = productService.addProduct(product);
        return productService.productEntityToDto(productAdded);
    }

    @PutMapping("/update")
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        Product product = productService.productDtoToEntity(productDto);
        Product updatedProduct = productService.updateProduct(product);
        return productService.productEntityToDto(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ProductDto deleteProduct(@PathVariable(value="id") Long id) {
        Product deletedProduct = productService.deleteProduct(id);
        return productService.productEntityToDto(deletedProduct);
    }
}
