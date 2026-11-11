package com.example.myapp.client.tree;

import com.example.myapp.client.handler.NodeSelectHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class NodeTreeView extends Composite implements NodeTreeDisplay {

    private static final String STYLE_MAIN = "NodeTreeView";
    private static final String STYLE_TREE = STYLE_MAIN + "-tree";

    private FlowPanel main;
    private NodeTreeRowWidget virtualRoot;

    private NodeSelectHandler onNodeSelect;

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

    public void setTree(NodeTreeViewData tree) {
        main.clear();

        if (tree == null || tree.isEmpty()) {
            return;
        }

        virtualRoot = new NodeTreeRowWidget(tree);
        virtualRoot.setNodeSelectHandler(onNodeSelect);
        virtualRoot.removeCaption();
        main.add(virtualRoot);
    }

    public void unselectOtherNodes(Long selectedNodeId) {
        if (virtualRoot == null) {
            return;
        }
        virtualRoot.unselectOtherNodes(selectedNodeId);
    }
}
