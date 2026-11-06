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

    private Long idCounter;

    public NodeDaoDemo() {
        idCounter = 0L;
        demoNodes = new ArrayList<>(Arrays.asList(
                new NodeEntity(nextId(), null, ROOT_NODE_NAME, ROOT_NODE_IP, 1111),
                new NodeEntity(nextId(), 0L, NODE_1_NAME, NODE_1_IP, 11),
                new NodeEntity(nextId(), 0L, NODE_2_NAME, NODE_2_IP, 12),
                new NodeEntity(nextId(), 2L, NODE_3_NAME, NODE_3_IP, 121),
                new NodeEntity(nextId(), 1L, NODE_4_NAME, NODE_4_IP, 111)));
    }

    public Long nextId() {
        return idCounter++;
    }

    @Override
    public List<NodeEntity> getAllNodes() {
        return new ArrayList<NodeEntity>(demoNodes);
    }

    @Override
    public NodeEntity getNodeById(Long id) {
        if (id == null) {
            return null;
        }

        for (NodeEntity node : demoNodes) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void deleteNode(Long id) {
        // TODO
    }

    @Override
    public void save(NodeEntity node) {
        if (node == null) {
            return;
        }

        Long nodeId = node.getId();
        if (node.getId() == null) {
            node.setId(nextId());
            demoNodes.add(node);
            return;
        }

        for (NodeEntity nodeItem : demoNodes) {
            if (nodeItem.getId().equals(nodeId)) {
                nodeItem.setName(node.getName());
                nodeItem.setIp(node.getIp());
                nodeItem.setPort(node.getPort());
                return;
            }
        }
    }
}
