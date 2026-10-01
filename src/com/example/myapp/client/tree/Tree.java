package com.example.myapp.client.tree;

public class Tree {
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

    public void addNode(TreeNode parentNode, TreeNode node) {
        parentNode.addChildrenNode(node);
        node.setParent(parentNode);
    }
}
