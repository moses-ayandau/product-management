package com.moses.code.binarytree;

import com.moses.code.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class BinaryTreeNode {
    private Product product;
    private BinaryTreeNode left;
    private BinaryTreeNode right;


    public BinaryTreeNode(Product product) {
    }
}
