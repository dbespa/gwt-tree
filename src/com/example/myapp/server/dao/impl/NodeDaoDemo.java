package com.example.myapp.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.myapp.server.dao.NodeDao;
import com.example.myapp.server.entity.NodeEntity;

public class NodeDaoDemo implements NodeDao {

    private static final String ROOT_NODE_NAME = "rootNode";
    private static final String ROOT_NODE_IP = "127.0.0.1";
    private static final String NODE_1_NAME = "node-1";
    private static final String NODE_1_IP = "127.0.1.1";
    private static final String NODE_2_NAME = "node-2";
    private static final String NODE_2_IP = "127.0.1.2";
    private static final String NODE_3_NAME = "node-3";
    private static final String NODE_3_IP = "127.0.1.21";

    private final List<NodeEntity> demoNodes;

    public NodeDaoDemo() {
        demoNodes = new ArrayList<>(Arrays.asList(
                new NodeEntity(0, null, ROOT_NODE_NAME, ROOT_NODE_IP, 1111),
                new NodeEntity(1, 0, NODE_1_NAME, NODE_1_IP, 11),
                new NodeEntity(2, 0, NODE_2_NAME, NODE_2_IP, 12),
                new NodeEntity(3, 2, NODE_3_NAME, NODE_3_IP, 121)));
    }

    @Override
    public List<NodeEntity> getAllNodes() {
        return new ArrayList<NodeEntity>(demoNodes);
    }
}
