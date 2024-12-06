package com.moses.code.binarytree;

import com.moses.code.entity.Product;

import java.math.BigDecimal;
import java.util.List;


public class BinaryTree {
    private BinaryTreeNode root;

    // Insert a product into the binary tree
    public void insert(Product product) {
        root = insertRecursive(root, product);
    }

    private BinaryTreeNode insertRecursive(BinaryTreeNode node, Product product) {
        if (node == null) {
            return new BinaryTreeNode(product);
        }
        // Use compareTo() for BigDecimal comparison
        if (product.getPrice().compareTo(node.getProduct().getPrice()) < 0) {
            node.setLeft(insertRecursive(node.getLeft(), product));
        } else if (product.getPrice().compareTo(node.getProduct().getPrice()) > 0) {
            node.setRight(insertRecursive(node.getRight(), product));
        }
        return node;
    }

    // Search for a product by price
    public Product search(BigDecimal price) {
        return searchRecursive(root, price);
    }

    private Product searchRecursive(BinaryTreeNode node, BigDecimal price) {
        if (node == null || node.getProduct().getPrice() == price) {
            return node != null ? node.getProduct() : null;
        }
        if (price.compareTo(node.getProduct().getPrice()) <0) {
            return searchRecursive(node.getLeft(), price);
        }
        return searchRecursive(node.getRight(), price);
    }

    // In-order traversal for sorted product list
    public void inOrderTraversal(List<Product> productList) {
        inOrderTraversalRecursive(root, productList);
    }

    private void inOrderTraversalRecursive(BinaryTreeNode node, List<Product> productList) {
        if (node != null) {
            inOrderTraversalRecursive(node.getLeft(), productList);
            productList.add(node.getProduct());
            inOrderTraversalRecursive(node.getRight(), productList);
        }
    }
}
