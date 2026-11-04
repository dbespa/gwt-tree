package com.example.myapp.client;

import com.example.myapp.client.editform.NodeEditFormPresenter;
import com.example.myapp.client.editform.NodeEditFormView;
import com.example.myapp.client.handler.AddRootHandler;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.handler.RefreshHandler;
import com.example.myapp.client.rpc.NodeRpcService;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.client.selectednodeform.SelectedNodeFormPresenter;
import com.example.myapp.client.selectednodeform.SelectedNodeFormView;
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
    private static final String STYLE_SELECTED_NODE_FORM_CONTAINER = STYLE_MAIN + "-selected-node-form-container";
    private static final String STYLE_UI_PANEL_CONTAINER = STYLE_MAIN + "-ui-panel-container";
    private static final String STYLE_NODE_TABLE_CONTAINER = STYLE_MAIN + "-node-table-container";

    private static final String LABEL_ALL_NODES = "All nodes:";
    private static final String LABEL_TREE = "Tree:";
    private static final String LABEL_SELECTED = "Selected:";

    private NodeRpcServiceAsync nodeRpcService;
    private NodeTreePresenter nodeTreePresenter;
    private SelectedNodeFormPresenter selectedNodeFormPresenter;
    private NodeTablePresenter nodeTablePresenter;
    private UiPanelPresenter uiPanelPresenter;
    private NodeEditFormPresenter nodeEditFormPresenter;

    private NodeTableView nodeTableView;
    private NodeTreeView nodeTreeView;
    private SelectedNodeFormView selectedNodeFormView;
    private UiPanelView uiPanelView;
    private NodeEditFormView nodeEditFormView;

    public void onModuleLoad() {
        build();
        bind();
        update();
    }

    private void build() {
        nodeRpcService = (NodeRpcServiceAsync) GWT.create(NodeRpcService.class);
        buildTree();
        buildSelectedNodeForm();
        buildUiPanel();
        buildTable();
        buildEditForm();
    }

    private void buildTree() {
        nodeTreeView = new NodeTreeView();

        FlowPanel treeContainer = new FlowPanel();
        treeContainer.addStyleName(STYLE_TREE_CONTAINER);
        RootPanel.get().add(treeContainer);

        Label label = new Label(LABEL_TREE);
        label.addStyleName(STYLE_HEADING);
        treeContainer.add(label);

        nodeTreePresenter = new NodeTreePresenter(nodeRpcService, nodeTreeView);
        nodeTreePresenter.go(treeContainer);
    }

    private void buildSelectedNodeForm() {
        selectedNodeFormView = new SelectedNodeFormView();

        FlowPanel selectedNodeFormContainer = new FlowPanel();
        selectedNodeFormContainer.addStyleName(STYLE_SELECTED_NODE_FORM_CONTAINER);
        RootPanel.get().add(selectedNodeFormContainer);

        Label label = new Label(LABEL_SELECTED);
        label.addStyleName(STYLE_HEADING);
        selectedNodeFormContainer.add(label);

        selectedNodeFormPresenter = new SelectedNodeFormPresenter(nodeRpcService, selectedNodeFormView);
        selectedNodeFormPresenter.go(selectedNodeFormContainer);
    }

    private void buildUiPanel() {
        uiPanelView = new UiPanelView();

        FlowPanel uiPanel = new FlowPanel();
        uiPanel.addStyleName(STYLE_UI_PANEL_CONTAINER);
        RootPanel.get().add(uiPanel);

        uiPanelPresenter = new UiPanelPresenter(uiPanelView);
        uiPanelPresenter.go(uiPanel);
    }

    private void buildTable() {
        nodeTableView = new NodeTableView();

        FlowPanel tableContainer = new FlowPanel();
        tableContainer.addStyleName(STYLE_NODE_TABLE_CONTAINER);
        RootPanel.get().add(tableContainer);

        Label label = new Label(LABEL_ALL_NODES);
        label.addStyleName(STYLE_HEADING);
        tableContainer.add(label);

        nodeTablePresenter = new NodeTablePresenter(nodeRpcService, nodeTableView);
        nodeTablePresenter.go(tableContainer);
    }

    private void buildEditForm() {
        nodeEditFormView = new NodeEditFormView();
        nodeEditFormPresenter = new NodeEditFormPresenter(nodeRpcService, nodeEditFormView);
    }

    private void bind() {
        nodeTreePresenter.setNodeSelectHandler(new NodeSelectHandler() {
            @Override
            public void onNodeSelect(Long selectedNodeId) {
                uiPanelPresenter.setSelectedNodeId(selectedNodeId);
                selectedNodeFormPresenter.setSelectedNode(selectedNodeId);
                nodeEditFormPresenter.setSelectedNode(selectedNodeId);
            }
        });

        uiPanelPresenter.setRefreshHandler(new RefreshHandler() {
            @Override
            public void onRefresh() {
                nodeTablePresenter.updateTable();
            }
        });

        uiPanelPresenter.setAddRootNodeHandler(new AddRootHandler() {
            @Override
            public void onAddRoot() {
                nodeEditFormPresenter.showCreateForm();
            }
        });
    }

    private void update() {
        nodeTreePresenter.updateTree();
        nodeTablePresenter.updateTable();
    }
}
