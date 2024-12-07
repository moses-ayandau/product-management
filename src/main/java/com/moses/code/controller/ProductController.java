package com.moses.code.controller;

import com.moses.code.dto.ProductDto;
import com.moses.code.entity.Product;
import com.moses.code.exception.CategoryNotFoundException;
import com.moses.code.exception.ProductNotFoundException;
import com.moses.code.mappers.ProductMapper;
import com.moses.code.service.product.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    @Operation(summary = "Add a new product", description = "Creates a new product and associates it with a category.")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productRequest) throws CategoryNotFoundException {
        Product product = productService.addProduct(ProductMapper.convertFromProductDtoToProduct(productRequest));
        return new ResponseEntity<>(ProductMapper.convertFromProductToProductDto(product), HttpStatus.CREATED);
    }

    @Operation(summary = "Get paginated and sorted products", description = "Retrieves products with optional pagination and sorting.")
    @GetMapping
    public Page<Product> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return productService.getPaginatedAndSortedProducts(page, size, sortBy, sortDirection);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID.")
    public ResponseEntity<ProductDto> getProductByProductId(@PathVariable Long productId) throws ProductNotFoundException {
        Product product = productService.getProductById(productId);
        ProductDto productDto = ProductMapper.convertFromProductToProductDto(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "Update a product", description = "Updates an existing product by its ID.")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productRequest, @PathVariable Long productId) throws ProductNotFoundException {
        Product product = productService.updateProduct(ProductMapper.convertFromProductDtoToProduct(productRequest), productId);
        return new ResponseEntity<>(ProductMapper.convertFromProductToProductDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID.")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.NO_CONTENT);
    }

    // New methods based on the updated service

    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category", description = "Retrieves all products in a specific category.")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name", description = "Searches for products by their name (case-insensitive).")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/expensive")
    @Operation(summary = "Get top expensive products", description = "Retrieves the top N most expensive products.")
    public ResponseEntity<List<Product>> getTopExpensiveProducts(@RequestParam(defaultValue = "5") int limit) {
        List<Product> products = productService.getTopExpensiveProducts(limit);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/count/{category}")
    @Operation(summary = "Count products by category", description = "Counts the number of products in a specific category.")
    public ResponseEntity<Long> countProductsByCategory(@PathVariable String category) {
        long count = productService.countProductsByCategory(category);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/available")
    @Operation(summary = "Get available products", description = "Retrieves all products that are in stock.")
    public ResponseEntity<List<Product>> getAvailableProducts() {
        List<Product> products = productService.getAvailableProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/out-of-stock")
    @Operation(summary = "Get out-of-stock products", description = "Retrieves all products that are out of stock.")
    public ResponseEntity<List<Product>> getOutOfStockProducts() {
        List<Product> products = productService.getOutOfStockProducts();
        return ResponseEntity.ok(products);
    }
}
