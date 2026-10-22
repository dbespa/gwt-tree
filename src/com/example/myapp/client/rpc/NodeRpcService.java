package com.example.myapp.client.rpc;

import java.util.List;

import com.example.myapp.shared.bean.NodeBean;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("nodeRpc")
public interface NodeRpcService extends RemoteService {

    public List<NodeBean> getAllNodes();

    public NodeTreeBean getTree();
}
