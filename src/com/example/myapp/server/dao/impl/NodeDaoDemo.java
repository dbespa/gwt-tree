package com.example.myapp.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.myapp.server.dao.NodeDao;
import com.example.myapp.server.entity.NodeEntity;

public class NodeDaoDemo implements NodeDao {
    @Override
    public List<NodeEntity> getAllNodes() {
        List<NodeEntity> demoNodes = new ArrayList<>(Arrays.asList(
                new NodeEntity(0, null, "rootNode", "127.0.0.1", 1111),
                new NodeEntity(1, 0, "node-1", "127.0.1.1", 11),
                new NodeEntity(2, 0, "node-2", "127.0.1.2", 12),
                new NodeEntity(3, 2, "node-3", "127.0.1.21", 121)));

        return demoNodes;
    }

}
