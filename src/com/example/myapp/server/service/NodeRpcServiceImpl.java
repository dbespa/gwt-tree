package com.example.myapp.server.service;

import java.util.List;

import com.example.myapp.client.rpc.NodeRpcService;
import com.example.myapp.server.MyBatisUtils;
import com.example.myapp.server.NodeConverter;
import com.example.myapp.server.NodeTreeBuilder;
import com.example.myapp.server.dao.NodeDao;
import com.example.myapp.server.dao.impl.NodeDaoDatabase;
import com.example.myapp.server.entity.NodeEntity;
import com.example.myapp.shared.bean.NodeBean;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class NodeRpcServiceImpl extends RemoteServiceServlet implements NodeRpcService {

    private static final long serialVersionUID = -4966825884205570101L;

    private final NodeDao nodeDao;

    private final NodeTreeBuilder nodeTreeBuilder;

    public NodeRpcServiceImpl() {
        nodeDao = new NodeDaoDatabase(MyBatisUtils.getSqlSessionFactory());
        nodeTreeBuilder = new NodeTreeBuilder();
    }

    @Override
    public List<NodeBean> getAllNodes() {
        List<NodeEntity> allNodes = nodeDao.getAllNodes();
        return NodeConverter.getInstance().convertToNodeBeanList(allNodes);
    }

    @Override
    public NodeTreeBean getTree() {
        List<NodeEntity> allNodes = nodeDao.getAllNodes();
        return nodeTreeBuilder.getTree(allNodes);
    }

    @Override
    public NodeBean getNodeById(Long id) {
        NodeEntity node = nodeDao.getNodeById(id);
        return NodeConverter.getInstance().convertToNodeBean(node);
    }

    @Override
    public void deleteNode(Long id) {
        nodeDao.deleteNode(id);
    }

    @Override
    public NodeBean save(NodeBean node) {
        NodeEntity nodeEntity = NodeConverter.getInstance().convertToNodeEntity(node);
        NodeEntity savedNode = nodeDao.save(nodeEntity);
        return NodeConverter.getInstance().convertToNodeBean(savedNode);
    }
}
