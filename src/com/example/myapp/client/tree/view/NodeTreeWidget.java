package com.example.myapp.client.tree.view;

import com.example.myapp.client.bean.NodeViewData;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.tree.presenter.NodeTreePresenter;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class NodeTreeWidget extends Composite implements NodeTreePresenter.Display {

    private static final String STYLE_TREE = "tree";

    private FlowPanel main;
    private NodeTreeRowWidget virtualRoot;

    private NodeSelectHandler onNodeSelect;

    public NodeTreeWidget() {
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

    public void setTree(NodeViewData tree) {
        main.clear();

        if (tree == null || tree.isEmpty()) {
            return;
        }

        virtualRoot = new NodeTreeRowWidget(tree);
        virtualRoot.setNodeSelectHandler(onNodeSelect);
        virtualRoot.setCaptionVisible(false);
        main.add(virtualRoot);
    }

    public void unselectOtherNodes(Long selectedNodeId) {
        virtualRoot.unselectOtherNodes(selectedNodeId);
    }

    public Widget asWidget() {
        return this;
    }
}
