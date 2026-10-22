package com.example.myapp.shared.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NodeTreeBean implements Serializable {

    private static final long serialVersionUID = 6166253696483744286L;

    private Long id;
    private String name;
    private List<NodeTreeBean> children;

    public NodeTreeBean() {
        // do nothing
    }

    public NodeTreeBean(Long id, String name) {
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

    public List<NodeTreeBean> getChildren() {
        return new ArrayList<>(children);
    }

    public void setChildren(List<NodeTreeBean> children) {
        this.children = new ArrayList<>(children);
    }

    public void addChild(NodeTreeBean child) {
        children.add(child);
    }

    public Boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public String toString() {
        return "NodeTreeBean [id=" + id + ", name=" + name + ", children=" + children + "]";
    }
}
