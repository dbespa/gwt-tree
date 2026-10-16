package com.example.myapp.client.service;

import java.util.List;

import com.example.myapp.client.rpc.NodeRpcService;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;;

public class NodeTableRefreshService {
    private NodeRpcServiceAsync nodeRpcService = (NodeRpcServiceAsync) GWT.create(NodeRpcService.class);

    public void getAllNodes(final AsyncCallback<List<NodeBean>> callback) {
        nodeRpcService.getAllNodes(new AsyncCallback<List<NodeBean>>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(List<NodeBean> result) {
                callback.onSuccess(result);
            }
        });
    }
}
