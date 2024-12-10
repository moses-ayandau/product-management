package com.moses.code.binarytree;

import com.moses.code.entity.Product;
import com.moses.code.exception.BadRequestException;

import java.math.BigDecimal;
import java.util.List;

public class BinaryTree {
    private BinaryTreeNode root;

    public void insert(Product product) {
        if (product == null || product.getPrice() == null) {
            throw new BadRequestException("Product or Product price cannot be null");
        }
        root = insertRecursive(root, product);
    }

    private BinaryTreeNode insertRecursive(BinaryTreeNode node, Product product) {
        System.out.println("Inserting product: " + product);
        if (node == null) {
            System.out.println("Creating new node for product: " + product);
            return new BinaryTreeNode(product);
        }
        if (product.getPrice().compareTo(node.getProduct().getPrice()) < 0) {
            System.out.println("Inserting into left subtree: " + product);
            node.setLeft(insertRecursive(node.getLeft(), product));
        } else if (product.getPrice().compareTo(node.getProduct().getPrice()) > 0) {
            System.out.println("Inserting into right subtree: " + product);
            node.setRight(insertRecursive(node.getRight(), product));
        }
        return node;
    }

    public Product search(BigDecimal price) {
        return searchRecursive(root, price);
    }

    private Product searchRecursive(BinaryTreeNode node, BigDecimal price) {
        if (node == null || node.getProduct().getPrice().compareTo(price) == 0) {
            return node != null ? node.getProduct() : null;
        }
        if (price.compareTo(node.getProduct().getPrice()) < 0) {
            return searchRecursive(node.getLeft(), price);
        }
        return searchRecursive(node.getRight(), price);
    }

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
