package com.example.myapp.server.service.impl;

import java.util.List;

import com.example.myapp.client.rpc.NodeRpcService;
import com.example.myapp.server.dao.NodeDao;
import com.example.myapp.server.dao.impl.NodeDaoDemo;
import com.example.myapp.server.entity.NodeEntity;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class NodeRpcServiceImpl extends RemoteServiceServlet implements NodeRpcService {
    private final NodeDao nodeDao = new NodeDaoDemo();
    private final NodeConverter nodeConverter = new NodeConverter();

    @Override
    public List<NodeBean> getAllNodes() {
        List<NodeEntity> allNodes = nodeDao.getAllNodes();
        return nodeConverter.convertToNodeBeanList(allNodes);
    }
}
