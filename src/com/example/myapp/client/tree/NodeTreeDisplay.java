package com.example.myapp.client.tree;

import java.util.Set;

import com.example.myapp.event.handler.NodeSelectHandler;
import com.example.myapp.event.handler.ToggleChildrenHandler;
import com.google.gwt.user.client.ui.IsWidget;

public interface NodeTreeDisplay extends IsWidget {

    void setTree(NodeTreeViewData tree);

    void setNodeSelectHandler(NodeSelectHandler onSelect);

    void setToggleChildrenHandler(ToggleChildrenHandler onToggleChildren);

    void unselectOtherNodes(Long selectedNodeId);

    void collapseNodes(Set<Long> collapsedNodes);
}
