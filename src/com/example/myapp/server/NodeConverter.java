package com.example.myapp.server;

import java.util.ArrayList;
import java.util.List;

import com.example.myapp.server.entity.NodeEntity;
import com.example.myapp.shared.bean.NodeBean;

public class NodeConverter {

    public NodeConverter() {
        //do nothing
    }

    public List<NodeBean> convertToNodeBeanList(List<NodeEntity> allNodes) {
        List<NodeBean> convertedNodes = new ArrayList<>();

        if (allNodes == null || allNodes.isEmpty()) {
            return convertedNodes;
        }

        for (NodeEntity nodeEntity : allNodes) {
            convertedNodes.add(convertToNodeBean(nodeEntity));
        }
        return convertedNodes;
    }

    public NodeBean convertToNodeBean(NodeEntity nodeEntity) {
        if (nodeEntity == null) {
            return null;
        }

        return new NodeBean(nodeEntity.getId(),
                nodeEntity.getParentId(),
                nodeEntity.getName(),
                nodeEntity.getIp(),
                nodeEntity.getPort());
    }

    public NodeEntity convertToNodeEntity(NodeBean nodeBean) {
        if (nodeBean == null) {
            return null;
        }

        return new NodeEntity(nodeBean.getId(),
                nodeBean.getParentId(),
                nodeBean.getName(),
                nodeBean.getIp(),
                nodeBean.getPort());
    }
}
