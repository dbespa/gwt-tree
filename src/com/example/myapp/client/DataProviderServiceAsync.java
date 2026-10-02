package com.example.myapp.client;

import java.util.List;

import com.example.myapp.shared.TreeNodeBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataProviderServiceAsync {
    void getTreeNodes(AsyncCallback<List<TreeNodeBean>> callback);
}
