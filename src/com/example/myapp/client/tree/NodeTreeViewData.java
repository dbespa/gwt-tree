package com.example.myapp.client.tree;

import java.util.ArrayList;
import java.util.List;

public class NodeTreeViewData {
    private Long id;
    private String name;
    private List<NodeTreeViewData> children;

    public NodeTreeViewData(Long id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NodeTreeViewData> getChildren() {
        return new ArrayList<>(children);
    }

    public void setChildren(List<NodeTreeViewData> children) {
        this.children = new ArrayList<>(children);
    }

    public void addChild(NodeTreeViewData child) {
        children.add(child);
    }

    public Boolean isEmpty() {
        return children.isEmpty();
    }

    @Override
    public String toString() {
        return "NodeViewData [id=" + id + ", name=" + name + ", children=" + children + "]";
    }
}
