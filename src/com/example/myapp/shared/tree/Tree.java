package com.example.myapp.shared.tree;

import java.io.Serializable;

public class Tree implements Serializable {
    private TreeNode root;

    public Tree() {
        root = null;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void addRootNode(TreeNode node) {
        root = node;
    }
}
