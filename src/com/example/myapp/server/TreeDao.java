package com.example.myapp.server;

import java.util.ArrayList;
import java.util.List;

import com.example.myapp.shared.TreeNodeBean;
import com.example.myapp.shared.tree.Tree;
import com.example.myapp.shared.tree.TreeNode;

public class TreeDao {

    private List<TreeNode> allNodes = new ArrayList<>();
    private Tree tree = new Tree();

    public TreeDao() {
    }

    private TreeNode createNode(TreeNodeBean nodeBean) {
        int id = nodeBean.getId();
        String name = nodeBean.getName();
        String ip = nodeBean.getIp();
        int port = nodeBean.getPort();

        TreeNode node = new TreeNode(id, name, ip, port);
        return node;
    }

    private TreeNode getParent(int id) {
        for (int i = 0; i < allNodes.size(); i++) {
            TreeNode currentNode = allNodes.get(i);
            if (currentNode.getId() == id) {
                return currentNode;
            }
        }
        return null;
    }

    private void buildTree(List<TreeNodeBean> serverData) {
        TreeNodeBean rootNodeBean = serverData.get(0);
        TreeNode rootNode = createNode(rootNodeBean);
        tree.addRootNode(rootNode);
        allNodes.add(rootNode);

        for (int i = 1; i < serverData.size(); i++) {
            TreeNodeBean nodeBean = serverData.get(i);
            TreeNode currentNode = createNode(nodeBean);

            TreeNode parent = getParent(nodeBean.getParentId());
            parent.linkWithChildNode(currentNode);

            allNodes.add(currentNode);
        }
    }

    public TreeDao(List<TreeNodeBean> serverData) {
        buildTree(serverData);
    }

    public List<TreeNode> getAllNodes() {
        return allNodes;
    }

    public Tree getTree() {
        return tree;
    }
}
