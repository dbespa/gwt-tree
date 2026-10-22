package com.example.myapp.server;

import java.util.List;

import com.example.myapp.server.entity.NodeEntity;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.dev.util.collect.HashMap;

public class NodeTreeBuilder {

    private static final String UNIVERSAL_ROOT_NODE = "root";

    public NodeTreeBuilder() {
        //do nothing
    }

    private NodeTreeBean convertToNodeDto(NodeEntity nodeEntity) {
        if (nodeEntity == null) {
            return null;
        }
        return new NodeTreeBean(nodeEntity.getId(), nodeEntity.getName());
    }

    public NodeTreeBean getTree(List<NodeEntity> nodeEntityList) {
        HashMap<Long, NodeTreeBean> tree = new HashMap<>();
        NodeTreeBean root = new NodeTreeBean(-1L, UNIVERSAL_ROOT_NODE);

        if (nodeEntityList == null || nodeEntityList.isEmpty()) {
            return null;
        }

        for (NodeEntity node : nodeEntityList) {
            if (node != null) {
                NodeTreeBean convertedNode = convertToNodeDto(node);
                tree.put(node.getId(), convertedNode);

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
