package com.example.myapp.client;

import java.util.ArrayList;
import java.util.List;

public class NodeViewData {
    private Long id;
    private String name;
    private List<NodeViewData> children;

    public NodeViewData(Long id, String name) {
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

    public List<NodeViewData> getChildren() {
        return new ArrayList<>(children);
    }

    public void setChildren(List<NodeViewData> children) {
        this.children = new ArrayList<>(children);
    }

    public void addChild(NodeViewData child) {
        children.add(child);
    }

    public Boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public String toString() {
        return "NodeViewData [id=" + id + ", name=" + name + ", children=" + children + "]";
    }
}
