package com.moses.code.controller;

import com.moses.code.dto.ProductDto;
import com.moses.code.entity.Product;
import com.moses.code.exception.NotFoundException;
import com.moses.code.mappers.ProductMapper;
import com.moses.code.service.product.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/add")
    @Operation(summary = "Add a new product", description = "Creates a new product and associates it with a category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productRequest) throws NotFoundException {
        Product product = productService.addProduct(productRequest);
        return new ResponseEntity<>(ProductMapper.convertFromProductToProductDto(product), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get paginated and sorted products", description = "Retrieves products with optional pagination and sorting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    public Page<ProductDto> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Page<Product> productPage = productService.getPaginatedAndSortedProducts(page, size, sortBy, sortDirection);
        return productPage.map(ProductMapper::convertFromProductToProductDto);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> getProductByProductId(@PathVariable Long productId) throws NotFoundException {
        Product product = productService.getProductById(productId);
        ProductDto productDto = ProductMapper.convertFromProductToProductDto(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "Update a product", description = "Updates an existing product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productRequest, @PathVariable Long productId) throws NotFoundException {
        Product product = productService.updateProduct(productRequest, productId);
        return new ResponseEntity<>(ProductMapper.convertFromProductToProductDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) throws NotFoundException {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/category/{categoryName}")
    @Operation(summary = "Get products by category", description = "Retrieves all products in a specific category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String categoryName) {
        List<Product> products = productService.getProductsByCategory(categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name", description = "Searches for products by their name (case-insensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No products found with the given name")
    })
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/expensive")
    @Operation(summary = "Get top expensive products", description = "Retrieves the top N most expensive products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No products found")
    })
    public ResponseEntity<List<Product>> getTopExpensiveProducts(@RequestParam(defaultValue = "5") int limit) {
        List<Product> products = productService.getTopExpensiveProducts(limit);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/available")
    @Operation(summary = "Get available products", description = "Retrieves all products that are in stock.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available products retrieved successfully")
    })
    public ResponseEntity<List<Product>> getAvailableProducts() {
        List<Product> products = productService.getAvailableProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/out-of-stock")
    @Operation(summary = "Get out-of-stock products", description = "Retrieves all products that are out of stock.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Out-of-stock products retrieved successfully")
    })
    public ResponseEntity<List<Product>> getOutOfStockProducts() {
        List<Product> products = productService.getOutOfStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/load-binary-search")
    @Operation(summary = "Load products into binary tree", description = "Loads all products into a binary search tree for efficient querying.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products loaded into binary tree successfully")
    })
    public String loadProductsIntoBinaryTree() {
        productService.loadProductsIntoTree();
        return "Products loaded into binary tree!";
    }

    @GetMapping("/binary-search")
    @Operation(summary = "Find product by price", description = "Finds a product in the binary search tree by price.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No product found with the given price")
    })
    public Product findProductByPrice(@RequestParam BigDecimal price) {
        return productService.findProductByPrice(price);
    }

    @GetMapping("/sort-binary-search")
    @Operation(summary = "Get sorted products by price", description = "Retrieves all products sorted by price from the binary search tree.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved and sorted successfully")
    })
    public List<Product> getSortedProducts() {
        return productService.getProductsSortedByPrice();
    }

    @GetMapping("/{productId}/images")
    public ResponseEntity<List<String>> getProductImages(@PathVariable Long productId) {
        List<String> imageUrls = productService.getProductImages(productId);
        return new ResponseEntity<>(imageUrls, HttpStatus.OK);
    }

}
