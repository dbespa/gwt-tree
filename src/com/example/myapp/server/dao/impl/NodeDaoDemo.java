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
    private static final String NODE_4_NAME = "node-4";
    private static final String NODE_4_IP = "127.0.1.11";

    private final List<NodeEntity> demoNodes;

    public NodeDaoDemo() {
        demoNodes = new ArrayList<>(Arrays.asList(
                new NodeEntity(0L, null, ROOT_NODE_NAME, ROOT_NODE_IP, 1111),
                new NodeEntity(1L, 0L, NODE_1_NAME, NODE_1_IP, 11),
                new NodeEntity(2L, 0L, NODE_2_NAME, NODE_2_IP, 12),
                new NodeEntity(3L, 2L, NODE_3_NAME, NODE_3_IP, 121),
                new NodeEntity(4L, 1L, NODE_4_NAME, NODE_4_IP, 111)));
    }

    @Override
    public List<NodeEntity> getAllNodes() {
        return new ArrayList<NodeEntity>(demoNodes);
    }
}
