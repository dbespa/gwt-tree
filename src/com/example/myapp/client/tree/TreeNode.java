package com.example.myapp.client.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private int id;
    private List<TreeNode> children;
    private TreeNode parent;

    private String name;
    private String ip;
    private int port;

    public TreeNode(int id, String name, String ip, int port) {
        this.id = id;
        this.parent = null;
        children = new ArrayList<>();

        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void linkWithChildNode(TreeNode childNode) {
        if (this != null && children != null) {
            children.add(childNode);
            childNode.setParent(this);
        }
    }

    public List<TreeNode> getCurrentChildrenList() {
        List<TreeNode> currentChildren = new ArrayList<>(this.children);
        return currentChildren;
    }

    public List<TreeNode> getDescendantsList() {
        List<TreeNode> childrenList = this.getCurrentChildrenList();
        List<TreeNode> descendantsList = new ArrayList<>();

        if (childrenList.size() == 0) {
            return new ArrayList<>();
        }

        descendantsList.addAll(childrenList);

        for (int i = 0; i < childrenList.size(); i++) {
            TreeNode currentNode = childrenList.get(i);
            List<TreeNode> currentNodeDescendants = currentNode.getDescendantsList();
            descendantsList.addAll(currentNodeDescendants);
        }

        return descendantsList;
    }

    public int getId() {
        return id;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public Integer getParentId() {
        if (this.parent == null) {
            return null;
        }
        return parent.getId();
    }
}
