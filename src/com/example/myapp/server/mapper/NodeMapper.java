package com.example.myapp.server.mapper;

import java.util.List;

import com.example.myapp.server.entity.NodeEntity;

public interface NodeMapper {

    List<NodeEntity> getAllNodes();

    NodeEntity getNodeById(Long id);

    void addNewNode(NodeEntity node);

    NodeEntity editNode(NodeEntity node);

    NodeEntity deleteNode(Long id);
}
