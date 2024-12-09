package com.moses.code.service.product;

import com.moses.code.binarytree.BinaryTree;
import com.moses.code.entity.Category;
import com.moses.code.entity.Product;
import com.moses.code.exception.CategoryNotFoundException;
import com.moses.code.exception.ProductNotFoundException;
import com.moses.code.repository.CategoryRepository;
import com.moses.code.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private BinaryTree binaryTree;

    public ProductService() {
        this.binaryTree = new BinaryTree();
    }

    @Override
    public Product addProduct(Product productRequest) throws CategoryNotFoundException {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        Category category = categoryRepository.findById(productRequest.getCategory().getId()).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Page<Product> getPaginatedAndSortedProducts(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("Product Not found with the specified Id"));
    }

    @Override
    public Product getProductByCategoryName(Long categoryName) {
        return null;
    }

    @Override
    public Product updateProduct(Product product, Long productId) throws ProductNotFoundException {
       Product existingProduct = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product not found with this Id"));
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }



    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findProductsByCategory(category);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.searchProductsByName(name);
    }

    @Override
    public List<Product> getTopExpensiveProducts(int limit) {
        return productRepository.findTopExpensiveProducts(PageRequest.of(0, limit));
    }
    @Override
    public long countProductsByCategoryName(String categoryName) {
        return productRepository.countProductsByCategoryName(categoryName);
    }

    @Override
    public List<Product> getAvailableProducts() {
        return productRepository.findAvailableProducts();
    }

    @Override
    public List<Product> getOutOfStockProducts() {
        return productRepository.findOutOfStockProducts();
    }

    @Override
    public void updateProductDownloadUrls(List<String> downloadUrls, Long productId){
        Product product = getProductById(productId);
        product.setImageUrls(downloadUrls);
        productRepository.save(product);

    }
    @Override
    public void loadProductsIntoTree() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            if (product != null && product.getPrice() != null) {
                binaryTree.insert(product);
            } else {
                System.err.println("Skipping invalid product: " + product);
            }
        }
    }

    @Override
    public Product findProductByPrice(BigDecimal price) {
        return binaryTree.search(price);
    }

    @Override
    public List<Product> getProductsSortedByPrice() {
        List<Product> sortedProducts = new ArrayList<>();
        binaryTree.inOrderTraversal(sortedProducts);
        return sortedProducts;
    }

}
