package com.example.myapp.client;

import com.example.myapp.client.editform.NodeEditFormPresenter;
import com.example.myapp.client.editform.NodeEditFormView;
import com.example.myapp.client.handler.AddChildHandler;
import com.example.myapp.client.handler.AddRootHandler;
import com.example.myapp.client.handler.DeleteHandler;
import com.example.myapp.client.handler.EditHandler;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.handler.RefreshHandler;
import com.example.myapp.client.mainwidget.MainWidget;
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
import com.google.gwt.user.client.ui.RootPanel;

public class TreeApp implements EntryPoint {

    private static NodeRpcServiceAsync nodeRpcService;
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

    private MainWidget mainWidget;

    private Long selectedNodeId;

    public void onModuleLoad() {
        build();
        bind();
        update();
    }

    private void build() {
        nodeRpcService = (NodeRpcServiceAsync) GWT.create(NodeRpcService.class);
        mainWidget = new MainWidget();

        RootPanel.get().add(mainWidget);
        buildTree();
        buildSelectedNodeForm();
        buildUiPanel();
        buildTable();
        buildEditForm();
    }

    public static NodeRpcServiceAsync getRpcService() {
        return nodeRpcService;
    }

    private void buildTree() {
        nodeTreeView = new NodeTreeView();
        FlowPanel treeContainer = mainWidget.getTreeContainer();

        nodeTreePresenter = new NodeTreePresenter(nodeRpcService, nodeTreeView);
        nodeTreePresenter.go(treeContainer);
    }

    private void buildSelectedNodeForm() {
        selectedNodeFormView = new SelectedNodeFormView();
        FlowPanel selectedNodeFormContainer = mainWidget.getSelectedNodeFormContainer();

        selectedNodeFormPresenter = new SelectedNodeFormPresenter(selectedNodeFormView);
        selectedNodeFormPresenter.go(selectedNodeFormContainer);
    }

    private void buildUiPanel() {
        uiPanelView = new UiPanelView();
        FlowPanel uiPanelContainer = mainWidget.getUiPanelContainer();

        uiPanelPresenter = new UiPanelPresenter(uiPanelView);
        uiPanelPresenter.go(uiPanelContainer);
    }

    private void buildTable() {
        nodeTableView = new NodeTableView();
        FlowPanel tableContainer = mainWidget.getTableContainer();

        nodeTablePresenter = new NodeTablePresenter(nodeTableView);
        nodeTablePresenter.go(tableContainer);
    }

    private void buildEditForm() {
        nodeEditFormView = new NodeEditFormView();
        nodeEditFormPresenter = new NodeEditFormPresenter(nodeEditFormView);
    }

    private void bind() {
        nodeTreePresenter.setNodeSelectHandler(new NodeSelectHandler() {
            @Override
            public void onNodeSelect(Long selectedNodeId) {
                TreeApp.this.selectedNodeId = selectedNodeId;
                uiPanelPresenter.setSelectedNodeId(selectedNodeId);
                selectedNodeFormPresenter.setSelectedNode(selectedNodeId);
            }
        });

        uiPanelPresenter.setRefreshHandler(new RefreshHandler() {
            @Override
            public void onRefresh() {
                update();
            }
        });

        uiPanelPresenter.setAddRootHandler(new AddRootHandler() {
            @Override
            public void onAddRoot() {
                TreeApp.this.selectedNodeId = null;
                uiPanelPresenter.setSelectedNodeId(null);
                selectedNodeFormPresenter.setEmpty();
                nodeTreePresenter.setAllNodesUnselected();
                nodeEditFormPresenter.createRoot();
            }
        });

        uiPanelPresenter.setAddChildHandler(new AddChildHandler() {
            @Override
            public void onAddChild() {
                nodeEditFormPresenter.createChildren(selectedNodeId);
            }
        });

        uiPanelPresenter.setEditHandler(new EditHandler() {
            @Override
            public void onEdit() {
                nodeEditFormPresenter.editNode(selectedNodeId);
            }
        });

        uiPanelPresenter.setDeleteHandler(new DeleteHandler() {
            @Override
            public void onDelete() {
                update();
                selectedNodeId = null;
                uiPanelPresenter.setSelectedNodeId(null);
                selectedNodeFormPresenter.setEmpty();
            }
        });

        nodeEditFormPresenter.setRefreshHandler(new RefreshHandler() {
            @Override
            public void onRefresh() {
                selectedNodeFormPresenter.setSelectedNode(selectedNodeId);
                update();
            }
        });
    }

    private void update() {
        nodeTreePresenter.updateTree();
        nodeTablePresenter.updateTable();
    }
}
