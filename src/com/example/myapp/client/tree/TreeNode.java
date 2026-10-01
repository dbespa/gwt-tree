package com.example.myapp.client.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {
    private int id;
    private T data;
    private List<TreeNode<T>> children;
    private TreeNode<T> parent;

    public TreeNode(int id, T data, TreeNode<T> parent) {
        this.id = id;
        this.data = data;
        this.parent = parent;
        children = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<TreeNode<T>> getChildren() {
        return this.children;
    }

    public int getId() {
        return id;
    }

    void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public Integer getParentId() {
        if (this.parent == null)
            return null;
        return parent.getId();
    }

    void addChildrenNode(TreeNode<T> node) {
        children.add(node);
    }
}
