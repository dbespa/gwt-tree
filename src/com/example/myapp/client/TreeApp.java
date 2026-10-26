package com.example.myapp.client;

import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.handler.RefreshHandler;
import com.example.myapp.client.rpc.NodeRpcService;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.client.widget.NodeTableWidget;
import com.example.myapp.client.widget.NodeTreeWidget;
import com.example.myapp.client.widget.UiPanelWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TreeApp implements EntryPoint {

    private static final String STYLE_HEADING = "heading";
    private static final String STYLE_TREE_CONTAINER = "tree-container";
    private static final String STYLE_UI_PANEL = "ui-panel";
    private static final String STYLE_NODE_TABLE_CONTAINER = "node-table-container";

    private static final String LABEL_ALL_NODES = "All nodes:";
    private static final String LABEL_TREE = "Tree:";

    private NodeRpcServiceAsync nodeRpcService;

    private NodeTableWidget nodeTableWidget;
    private NodeTreeWidget nodeTreeWidget;
    private UiPanelWidget uiPanelWidget;

    public void onModuleLoad() {
        build();
        bind();
        update();
    }

    private void build() {
        nodeRpcService = (NodeRpcServiceAsync) GWT.create(NodeRpcService.class);

        FlowPanel treeContainer = new FlowPanel();
        treeContainer.addStyleName(STYLE_TREE_CONTAINER);
        RootPanel.get().add(treeContainer);

        uiPanelWidget = new UiPanelWidget();
        uiPanelWidget.addStyleName(STYLE_UI_PANEL);
        RootPanel.get().add(uiPanelWidget);

        FlowPanel nodeTableContainer = new FlowPanel();
        nodeTableContainer.addStyleName(STYLE_NODE_TABLE_CONTAINER);
        RootPanel.get().add(nodeTableContainer);

        Label label = new Label(LABEL_TREE);
        label.addStyleName(STYLE_HEADING);
        treeContainer.add(label);

        nodeTreeWidget = new NodeTreeWidget(nodeRpcService);
        treeContainer.add(nodeTreeWidget);

        label = new Label(LABEL_ALL_NODES);
        label.addStyleName(STYLE_HEADING);
        nodeTableContainer.add(label);

        nodeTableWidget = new NodeTableWidget(nodeRpcService);
        nodeTableContainer.add(nodeTableWidget);
    }

    private void bind() {
        nodeTreeWidget.setHandler(new NodeSelectHandler() {
            @Override
            public void onNodeSelect(Long selectedNodeId) {
                uiPanelWidget.setSelectedNodeId(selectedNodeId);
            }
        });

        uiPanelWidget.setHandler(new RefreshHandler() {
            @Override
            public void onRefresh() {
                update();
            }
        });
    }

    private void update() {
        nodeTableWidget.refreshTable();
        nodeTreeWidget.refreshTree();
    }
}
