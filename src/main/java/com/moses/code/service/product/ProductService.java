package com.moses.code.service.product;

import com.moses.code.binarytree.BinaryTree;
import com.moses.code.dto.ProductDto;
import com.moses.code.entity.Category;
import com.moses.code.entity.Product;
import com.moses.code.exception.NotFoundException;
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
    public Product addProduct(ProductDto productRequest) throws NotFoundException {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());

        Category category = (Category) categoryRepository.findCategoryByName(productRequest.getCategoryName())
                .orElseThrow(() -> new NotFoundException("Category not found with name: " + productRequest.getCategoryName()));

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
    public Product getProductById(Long productId) throws NotFoundException {
        return productRepository.findById(productId).orElseThrow(()->new NotFoundException("Product Not found with the specified Id"));
    }

    @Override
    public Product getProductByCategoryName(Long categoryName) {
        return null;
    }

    @Override
    public Product updateProduct(ProductDto product, Long productId) throws NotFoundException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with this Id"));

        if (product.getQuantity() > 0) {
            existingProduct.setQuantity(product.getQuantity());
        }

        if (product.getPrice() != null && product.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            existingProduct.setPrice(product.getPrice());
        }

        if (product.getName() != null && !product.getName().isEmpty()) {
            existingProduct.setName(product.getName());
        }

        if (product.getDescription() != null && !product.getDescription().isEmpty()) {
            existingProduct.setDescription(product.getDescription());
        }

        if (product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
            existingProduct.setImageUrls(product.getImageUrls());
        }
        return productRepository.save(existingProduct);
    }



    @Override
    public void deleteProduct(Long productId) throws NotFoundException {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }



    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        Category category = (Category) categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new NotFoundException("Category not found with name: " + categoryName));

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

    @Override
    public List<String> getProductImages(Long productId){
        List<String> imageUrls =productRepository.findProductImages(productId);
        if(imageUrls.isEmpty()){
            throw new NotFoundException("This product does not have image or product does not exist");
        }
        return imageUrls ;
    }

}
