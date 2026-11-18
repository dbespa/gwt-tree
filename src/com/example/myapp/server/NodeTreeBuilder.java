package com.example.myapp.server;

import java.util.HashMap;
import java.util.List;

import com.example.myapp.server.entity.NodeEntity;
import com.example.myapp.shared.bean.NodeTreeBean;

public class NodeTreeBuilder {

    private static final String UNIVERSAL_ROOT_NODE = "universalRoot";
    private HashMap<Long, NodeTreeBean> tree;

    public NodeTreeBuilder() {
        tree = new HashMap<>();
    }

    private NodeTreeBean convertToNodeTreeBean(NodeEntity nodeEntity) {
        if (nodeEntity == null) {
            return null;
        }
        return new NodeTreeBean(nodeEntity.getId(), nodeEntity.getName());
    }

    public NodeTreeBean getTree(List<NodeEntity> nodeEntityList) {
        if (nodeEntityList == null || nodeEntityList.isEmpty()) {
            return null;
        }

        collectNodes(nodeEntityList);
        NodeTreeBean root = fillChildren(nodeEntityList);
        return root;
    }

    private void collectNodes(List<NodeEntity> nodeEntityList) {
        for (NodeEntity node : nodeEntityList) {
            if (node != null) {
                NodeTreeBean convertedNode = convertToNodeTreeBean(node);
                tree.put(node.getId(), convertedNode);
            }
        }
    }

    private NodeTreeBean fillChildren(List<NodeEntity> nodeEntityList) {
        NodeTreeBean root = new NodeTreeBean(-1L, UNIVERSAL_ROOT_NODE);

        for (NodeEntity node : nodeEntityList) {
            if (node != null) {
                Long id = node.getId();
                Long parentId = node.getParentId();

                if (parentId == null) {
                    root.addChild(tree.get(id));
                } else {
                    NodeTreeBean child = tree.get(id);
                    NodeTreeBean parent = tree.get(parentId);
                    parent.addChild(child);
                }
            }
        }

        return root;
    }
}
