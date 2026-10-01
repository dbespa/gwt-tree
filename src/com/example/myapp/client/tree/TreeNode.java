package com.example.myapp.client.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private int id;
    private Data data;
    private List<TreeNode> children;
    private TreeNode parent;

    public TreeNode(int id, Data data, TreeNode parent) {
        this.id = id;
        this.data = data;
        this.parent = parent;
        children = new ArrayList<>();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<TreeNode> getChildren() {
        return this.children;
    }

    public int getId() {
        return id;
    }

    void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public Integer getParentId() {
        if (this.parent == null)
            return null;
        return parent.getId();
    }

    void addChildrenNode(TreeNode node) {
        children.add(node);
    }
}
