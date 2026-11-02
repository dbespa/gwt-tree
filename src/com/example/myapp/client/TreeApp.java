package com.example.myapp.client;

import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.handler.RefreshHandler;
import com.example.myapp.client.rpc.NodeRpcService;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.client.table.NodeTablePresenter;
import com.example.myapp.client.table.NodeTableView;
import com.example.myapp.client.tree.NodeTreePresenter;
import com.example.myapp.client.tree.NodeTreeView;
import com.example.myapp.client.uipanel.UiPanelPresenter;
import com.example.myapp.client.uipanel.UiPanelView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TreeApp implements EntryPoint {

    private static final String STYLE_MAIN = "TreeApp";
    private static final String STYLE_HEADING = STYLE_MAIN + "-heading";
    private static final String STYLE_TREE_CONTAINER = STYLE_MAIN + "-tree-container";
    private static final String STYLE_UI_PANEL_CONTAINER = STYLE_MAIN + "-ui-panel-container";
    private static final String STYLE_NODE_TABLE_CONTAINER = STYLE_MAIN + "-node-table-container";

    private static final String LABEL_ALL_NODES = "All nodes:";
    private static final String LABEL_TREE = "Tree:";

    private NodeRpcServiceAsync nodeRpcService;
    private NodeTreePresenter nodeTreePresenter;
    private NodeTablePresenter nodeTablePresenter;
    private UiPanelPresenter uiPanelPresenter;

    private NodeTableView nodeTableWidget;
    private NodeTreeView nodeTreeWidget;
    private UiPanelView uiPanelWidget;

    public void onModuleLoad() {
        build();
        bind();
        update();
    }

    private void build() {
        nodeRpcService = (NodeRpcServiceAsync) GWT.create(NodeRpcService.class);
        buildTree();
        buildUiPanel();
        buildTable();
    }

    private void buildTree() {
        nodeTreeWidget = new NodeTreeView();

        FlowPanel treeContainer = new FlowPanel();
        treeContainer.addStyleName(STYLE_TREE_CONTAINER);
        RootPanel.get().add(treeContainer);

        Label label = new Label(LABEL_TREE);
        label.addStyleName(STYLE_HEADING);
        treeContainer.add(label);

        nodeTreePresenter = new NodeTreePresenter(nodeRpcService, nodeTreeWidget);
        nodeTreePresenter.go(treeContainer);
    }

    private void buildUiPanel() {
        uiPanelWidget = new UiPanelView();

        FlowPanel uiPanel = new FlowPanel();
        uiPanel.addStyleName(STYLE_UI_PANEL_CONTAINER);
        RootPanel.get().add(uiPanel);

        uiPanelPresenter = new UiPanelPresenter(uiPanelWidget);
        uiPanelPresenter.go(uiPanel);
    }

    private void buildTable() {
        nodeTableWidget = new NodeTableView();

        FlowPanel tableContainer = new FlowPanel();
        tableContainer.addStyleName(STYLE_NODE_TABLE_CONTAINER);
        RootPanel.get().add(tableContainer);

        Label label = new Label(LABEL_ALL_NODES);
        label.addStyleName(STYLE_HEADING);
        tableContainer.add(label);

        nodeTablePresenter = new NodeTablePresenter(nodeRpcService, nodeTableWidget);
        nodeTablePresenter.go(tableContainer);
    }

    private void bind() {
        nodeTreePresenter.setNodeSelectHandler(new NodeSelectHandler() {
            @Override
            public void onNodeSelect(Long selectedNodeId) {
                uiPanelPresenter.setSelectedNodeId(selectedNodeId);
            }
        });

        uiPanelPresenter.setRefreshHandler(new RefreshHandler() {
            @Override
            public void onRefresh() {
                nodeTablePresenter.updateTable();
            }
        });
    }

    private void update() {
        nodeTreePresenter.updateTree();
        nodeTablePresenter.updateTable();
    }
}
