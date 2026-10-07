package com.example.myapp.client;

import java.util.List;

import com.example.myapp.shared.tree.Tree;
import com.example.myapp.shared.tree.TreeNode;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("tree")
public interface TreeService extends RemoteService {
    public List<TreeNode> getAllDescendants(Tree tree);
    public List<TreeNode> getNodeDescendantsList(TreeNode node);
}
