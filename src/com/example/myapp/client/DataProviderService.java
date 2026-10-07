package com.example.myapp.client;

import java.util.List;

import com.example.myapp.shared.tree.TreeNode;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("dataProvider")
public interface DataProviderService extends RemoteService {
    public List<TreeNode> getTreeNodes();
}
