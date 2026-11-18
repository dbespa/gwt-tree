package com.example.myapp.client.tree;

import java.util.Set;

import com.example.myapp.event.handler.NodeSelectHandler;
import com.example.myapp.event.handler.ToggleChildrenHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class NodeTreeView extends Composite implements NodeTreeDisplay {

    private static final String STYLE_MAIN = "NodeTreeView";
    private static final String STYLE_TREE = STYLE_MAIN + "-tree";

    private FlowPanel main;
    private NodeTreeRowWidget virtualRoot;

    private NodeSelectHandler onNodeSelect;
    private ToggleChildrenHandler onToggleChildren;

    public NodeTreeView() {
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        main.addStyleName(STYLE_TREE);
    }

    public void setNodeSelectHandler(NodeSelectHandler onSelect) {
        this.onNodeSelect = onSelect;
    }

    public void setToggleChildrenHandler(ToggleChildrenHandler onToggleChildren) {
        this.onToggleChildren = onToggleChildren;
    }

    public void setTree(NodeTreeViewData tree) {
        main.clear();

        if (tree == null || tree.isEmpty()) {
            return;
        }

        virtualRoot = new NodeTreeRowWidget(tree);
        virtualRoot.setNodeSelectHandler(onNodeSelect);
        virtualRoot.setToggleChildrenHandler(onToggleChildren);
        virtualRoot.removeCaption();
        main.add(virtualRoot);
    }

    public void unselectOtherNodes(Long selectedNodeId) {
        if (virtualRoot == null) {
            return;
        }
        virtualRoot.unselectOtherNodes(selectedNodeId);
    }

    public void collapseNodes(Set<Long> collapsedNodes) {
        if (virtualRoot == null) {
            return;
        }
        virtualRoot.setCollapsedNodes(collapsedNodes);
    }
}
