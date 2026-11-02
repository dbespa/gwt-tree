package com.example.myapp.client.tree;

import com.example.myapp.client.bean.NodeViewData;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.google.gwt.user.client.ui.IsWidget;

public interface NodeTreeDisplay extends IsWidget {

    void setTree(NodeViewData tree);

    void setNodeSelectHandler(NodeSelectHandler onSelect);

    void unselectOtherNodes(Long selectedNodeId);
}
