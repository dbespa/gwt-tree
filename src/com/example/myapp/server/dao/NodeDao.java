package com.example.myapp.server.dao;

import java.util.List;

import com.example.myapp.server.entity.NodeEntity;

public interface NodeDao {

    public List<NodeEntity> getAllNodes();

    public NodeEntity getNodeById(Long id);

    public void save(NodeEntity node);

    public void deleteNode(Long id);
}
