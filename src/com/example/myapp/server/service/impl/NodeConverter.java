package com.example.myapp.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.myapp.server.entity.NodeEntity;
import com.example.myapp.shared.bean.NodeBean;

public class NodeConverter {
    public List<NodeBean> convertToNodeBeanList(List<NodeEntity> allNodes) {
        List<NodeBean> convertedNodes = new ArrayList<>();

        for (NodeEntity nodeEntity : allNodes) {
            convertedNodes.add(convertToNodeBean(nodeEntity));
        }

        return convertedNodes;
    }

    private NodeBean convertToNodeBean(NodeEntity nodeEntity) {
        return new NodeBean(
                nodeEntity.getId(),
                nodeEntity.getParentId(),
                nodeEntity.getName(),
                nodeEntity.getIp(),
                nodeEntity.getPort());
    }
}
