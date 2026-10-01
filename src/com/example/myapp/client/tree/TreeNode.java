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

    public int getId() {
        return id;
    }

    public Integer getParentId() {
        return parent.getId();
    }

}
