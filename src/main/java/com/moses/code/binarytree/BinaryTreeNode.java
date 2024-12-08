package com.moses.code.binarytree;

import com.moses.code.entity.Product;
import com.moses.code.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BinaryTreeNode {
    private Product product;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public BinaryTreeNode(Product product) {
        if (product == null) {
            throw new BadRequestException("Product cannot be null");
        }
        this.product = product;
        this.left = null;
        this.right = null;
    }
}
