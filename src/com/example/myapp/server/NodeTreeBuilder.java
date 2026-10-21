package com.example.myapp.server;

import java.util.ArrayList;
import java.util.List;

import com.example.myapp.server.entity.NodeEntity;
import com.example.myapp.shared.NodeDto;
import com.google.gwt.dev.util.collect.HashMap;

public class NodeTreeBuilder {

    public NodeTreeBuilder() {
        //do nothing
    }

    private NodeDto convertToNodeDto(NodeEntity nodeEntity) {
        if (nodeEntity == null) {
            return null;
        }

        return new NodeDto(nodeEntity.getId(), nodeEntity.getName());
    }

    private HashMap<Long, NodeDto> buildMap(List<NodeEntity> nodeEntityList) {
        HashMap<Long, NodeDto> tree = new HashMap<>();

        if (nodeEntityList == null || nodeEntityList.isEmpty()) {
            return tree;
        }

        for (NodeEntity node : nodeEntityList) {
            if (node != null) {
                NodeDto convertedNode = convertToNodeDto(node);
                tree.put(node.getId(), convertedNode);
            }
        }

        return tree;
    }

    public List<NodeDto> getTreeRoots(List<NodeEntity> nodeEntityList) {
        HashMap<Long, NodeDto> tree = buildMap(nodeEntityList);
        List<NodeDto> treeRoots = new ArrayList<>();

        if (nodeEntityList == null || nodeEntityList.isEmpty()) {
            return treeRoots;
        }

        for (NodeEntity node : nodeEntityList) {
            Long id = node.getId();
            Long parentId = node.getParentId();

            if (parentId == null) {
                treeRoots.add(tree.get(id));
            } else {
                NodeDto child = tree.get(id);
                NodeDto parent = tree.get(parentId);
                parent.addChild(child);
            }
        }

        return treeRoots;
    }
}
