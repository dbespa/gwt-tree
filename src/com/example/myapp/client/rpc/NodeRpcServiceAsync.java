package com.example.myapp.client.rpc;

import java.util.List;

import com.example.myapp.shared.NodeDto;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface NodeRpcServiceAsync {

    void getAllNodes(AsyncCallback<List<NodeBean>> callback);

    void getTreeRoots(AsyncCallback<List<NodeDto>> callback);
}
