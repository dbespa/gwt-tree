package com.example.myapp.server.service;

import java.util.List;

import com.example.myapp.client.rpc.NodeRpcService;
import com.example.myapp.server.NodeConverter;
import com.example.myapp.server.NodeTreeBuilder;
import com.example.myapp.server.dao.NodeDao;
import com.example.myapp.server.dao.impl.NodeDaoDemo;
import com.example.myapp.server.entity.NodeEntity;
import com.example.myapp.shared.bean.NodeBean;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class NodeRpcServiceImpl extends RemoteServiceServlet implements NodeRpcService {

    private static final long serialVersionUID = -4966825884205570101L;

    private final NodeDao nodeDao;

    private final NodeConverter nodeConverter;
    private final NodeTreeBuilder nodeTreeBuilder;

    public NodeRpcServiceImpl() {
        nodeDao = new NodeDaoDemo();
        nodeConverter = new NodeConverter();
        nodeTreeBuilder = new NodeTreeBuilder();
    }

    @Override
    public List<NodeBean> getAllNodes() {
        List<NodeEntity> allNodes = nodeDao.getAllNodes();
        return nodeConverter.convertToNodeBeanList(allNodes);
    }

    @Override
    public NodeTreeBean getTree() {
        List<NodeEntity> allNodes = nodeDao.getAllNodes();
        return nodeTreeBuilder.getTree(allNodes);
    }
}
