package com.example.myapp.server.dao;

import java.util.List;

import com.example.myapp.server.entity.NodeEntity;

public interface NodeDao {

    public List<NodeEntity> getAllNodes();

    public NodeEntity getNodeById(Long id);

    public NodeEntity save(NodeEntity node);

    public NodeEntity deleteNode(Long id);
}
