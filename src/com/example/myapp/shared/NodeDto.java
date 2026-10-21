package com.example.myapp.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NodeDto implements Serializable {

    private static final long serialVersionUID = 6166253696483744286L;

    private static final String NODE_DTO_TO_STRING = "NodeDto: ";
    private static final String CHILDREN_TO_STRING = "children";

    private Long id;
    private String name;
    private List<NodeDto> children;

    public NodeDto() {
        // do nothing
    }

    public NodeDto(Long id, String name) {
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

    public List<NodeDto> getChildren() {
        return new ArrayList<>(children);
    }

    public void setChildren(List<NodeDto> children) {
        this.children = children;
    }

    public void addChild(NodeDto child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return NODE_DTO_TO_STRING + StringUtils.LEFT_BRACKET +
                StringUtils.ID_TO_STRING + StringUtils.EQUALS + id + StringUtils.SEPARATOR +
                StringUtils.NAME_TO_STRING + StringUtils.EQUALS + name + StringUtils.SEPARATOR +
                CHILDREN_TO_STRING + StringUtils.EQUALS + children + StringUtils.RIGTH_BRACKET;
    }
}
