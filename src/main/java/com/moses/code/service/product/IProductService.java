package com.moses.code.service.product;

import com.moses.code.entity.Product;
import com.moses.code.exception.CategoryNotFoundException;
import com.moses.code.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product addProduct(Product product) throws CategoryNotFoundException;

    Page<Product> getPaginatedAndSortedProducts(int page, int size, String sortBy, String sortDirection);

    Product getProductById(Long  productId) throws ProductNotFoundException;
    Product getProductByCategoryName(Long categoryName);
    Product updateProduct(Product product, Long productId) throws ProductNotFoundException;
    void deleteProduct(Long productId) throws ProductNotFoundException;

    List<Product> getProductsByCategory(String category);

    List<Product> searchProductsByName(String name);

    List<Product> getTopExpensiveProducts(int limit);


    long countProductsByCategoryName(String categoryName);

    List<Product> getAvailableProducts();

    List<Product> getOutOfStockProducts();

    void updateProductDownloadUrls(List<String> downloadUrls, Long productId);

    void loadProductsIntoTree();

    Product findProductByPrice(BigDecimal price);

    List<Product> getProductsSortedByPrice();
}
