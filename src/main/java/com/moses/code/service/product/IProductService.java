package com.moses.code.service.product;

import com.moses.code.dto.ProductDto;
import com.moses.code.entity.Product;
import com.moses.code.exception.NotFoundException;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    Product addProduct(ProductDto product) throws NotFoundException;

    Page<Product> getPaginatedAndSortedProducts(int page, int size, String sortBy, String sortDirection);

    Product getProductById(Long  productId) throws NotFoundException;
    Product updateProduct(ProductDto product, Long productId) throws NotFoundException;
    void deleteProduct(Long productId) throws NotFoundException;

    List<Product> getProductsByCategory(String category);

    List<Product> searchProductsByName(String name);

    List<Product> getTopExpensiveProducts(int limit);



    List<Product> getAvailableProducts();

    List<Product> getOutOfStockProducts();

    void updateProductDownloadUrls(List<String> downloadUrls, Long productId);

    void loadProductsIntoTree();

    Product findProductByPrice(BigDecimal price);

    List<Product> getProductsSortedByPrice();

    List<String> getProductImages(Long productId);
}
