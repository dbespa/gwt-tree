package com.example.myapp.server;

import java.util.ArrayList;
import java.util.List;

import com.example.myapp.client.TreeService;
import com.example.myapp.shared.tree.Tree;
import com.example.myapp.shared.tree.TreeNode;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TreeServiceImpl extends RemoteServiceServlet implements TreeService {

    @Override
    public List<TreeNode> getAllDescendants(Tree tree) {
        TreeNode root = tree.getRoot();
        List<TreeNode> allNodesList = new ArrayList<>();
        allNodesList.add(root);

        if (root != null) {
            allNodesList.addAll(getNodeDescendantsList(root));
        }

        return allNodesList;
    }

    @Override
    public List<TreeNode> getNodeDescendantsList(TreeNode node) {
        List<TreeNode> childrenList = node.getCurrentChildrenList();
        List<TreeNode> descendantsList = new ArrayList<>();

        if (childrenList.size() == 0) {
            return new ArrayList<>();
        }

        descendantsList.addAll(childrenList);

        for (int i = 0; i < childrenList.size(); i++) {
            TreeNode currentNode = childrenList.get(i);
            List<TreeNode> currentNodeDescendants = getNodeDescendantsList(currentNode);
            descendantsList.addAll(currentNodeDescendants);
        }

        return descendantsList;
    }

}
