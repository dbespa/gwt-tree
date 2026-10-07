package com.example.myapp.shared.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public List<TreeNode> getAllDescendants() {
        List<TreeNode> allNodesList = new ArrayList<>();
        allNodesList.add(root);

        if (root != null) {
            allNodesList.addAll(root.getDescendantsList());
        }

        return allNodesList;
    }
}
