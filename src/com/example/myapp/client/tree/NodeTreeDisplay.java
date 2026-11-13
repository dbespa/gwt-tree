package com.example.myapp.client.tree;

import com.example.myapp.event.handler.NodeSelectHandler;
import com.google.gwt.user.client.ui.IsWidget;

public interface NodeTreeDisplay extends IsWidget {

    void setTree(NodeTreeViewData tree);

    void setNodeSelectHandler(NodeSelectHandler onSelect);

    void unselectOtherNodes(Long selectedNodeId);
}
