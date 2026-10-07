package com.example.myapp.client;

import java.util.List;

import com.example.myapp.shared.tree.TreeNode;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TreeServiceAsync {
    void getAllDescendants(AsyncCallback<List<TreeNode>> callback);
    void getNodeDescendantsList(AsyncCallback<List<TreeNode>> callback);
}
