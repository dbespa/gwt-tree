package com.example.myapp.client.tree;

public class Tree<T> {
    private TreeNode<T> root;

    public Tree() {
        root = null;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void addRootNode(TreeNode<T> node) {
        root = node;
    }

    public void addNode(TreeNode<T> parentNode, TreeNode<T> node) {
        parentNode.addChildrenNode(node);
        node.setParent(parentNode);
    }
}
