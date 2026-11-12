package com.example.myapp.client;

import com.example.myapp.client.editform.NodeEditFormPresenter;
import com.example.myapp.client.editform.NodeEditFormValidator;
import com.example.myapp.client.editform.NodeEditFormView;
import com.example.myapp.client.errormessage.ErrorMessageUtils;
import com.example.myapp.client.errormessage.ErrorMessageWidget;
import com.example.myapp.client.handler.AddChildClickHandler;
import com.example.myapp.client.handler.AddRootClickHandler;
import com.example.myapp.client.handler.AfterDeleteHandler;
import com.example.myapp.client.handler.EditClickHandler;
import com.example.myapp.client.handler.ErrorHandler;
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

    private MainWidget mainWidget;
    private ErrorMessageWidget errorMessageWidget;

    //TODO mativ
    // Есть место оптимизации. Подумать какую одинаковую работу приложение
    // выполняет несколько раз подряд и как её можно оптимизировать?
    // Только подумать и сообщить, править пока не надо.

    public void onModuleLoad() {
        build();
        bind();
        update();
    }

    private void build() {
        nodeRpcService = (NodeRpcServiceAsync) GWT.create(NodeRpcService.class);
        mainWidget = new MainWidget();
        errorMessageWidget = new ErrorMessageWidget();

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
        NodeTreeView nodeTreeView = new NodeTreeView();
        FlowPanel treeContainer = mainWidget.getTreeContainer();

        nodeTreePresenter = new NodeTreePresenter(nodeTreeView);
        nodeTreePresenter.go(treeContainer);
    }

    private void buildSelectedNodeForm() {
        SelectedNodeFormView selectedNodeFormView = new SelectedNodeFormView();
        FlowPanel selectedNodeFormContainer = mainWidget.getSelectedNodeFormContainer();

        selectedNodeFormPresenter = new SelectedNodeFormPresenter(selectedNodeFormView);
        selectedNodeFormPresenter.go(selectedNodeFormContainer);
    }

    private void buildUiPanel() {
        UiPanelView uiPanelView = new UiPanelView();
        FlowPanel uiPanelContainer = mainWidget.getUiPanelContainer();

        uiPanelPresenter = new UiPanelPresenter(uiPanelView);
        uiPanelPresenter.go(uiPanelContainer);
    }

    private void buildTable() {
        NodeTableView nodeTableView = new NodeTableView();
        FlowPanel tableContainer = mainWidget.getTableContainer();

        nodeTablePresenter = new NodeTablePresenter(nodeTableView);
        nodeTablePresenter.go(tableContainer);
    }

    private void buildEditForm() {
        NodeEditFormView nodeEditFormView = new NodeEditFormView();
        NodeEditFormValidator nodeEditFormValidator = new NodeEditFormValidator();
        nodeEditFormPresenter = new NodeEditFormPresenter(nodeEditFormValidator, nodeEditFormView);
    }

    private void bind() {
        ErrorMessageUtils.setErrorHandler(new ErrorHandler() {
            @Override
            public void onError(String errorMessage) {
                errorMessageWidget.showErrorMessage(errorMessage);
            }
        });

        nodeTreePresenter.setNodeSelectHandler(new NodeSelectHandler() {
            @Override
            public void onNodeSelect(Long selectedNodeId) {
                setSelectedNodeId(selectedNodeId);
            }
        });

        uiPanelPresenter.setRefreshHandler(new RefreshHandler() {
            @Override
            public void onRefresh() {
                update();
            }
        });

        nodeEditFormPresenter.setRefreshHandler(new RefreshHandler() {
            @Override
            public void onRefresh() {
                selectedNodeFormPresenter.setSelectedNode(nodeTreePresenter.getSelectedNodeId());
                update();
            }
        });

        uiPanelPresenter.setAddRootClickHandler(new AddRootClickHandler() {
            @Override
            public void onAddRootClick() {
                setSelectedNodeId(null);
                nodeEditFormPresenter.setCreateRootMode();
            }
        });

        uiPanelPresenter.setAddChildClickHandler(new AddChildClickHandler() {
            @Override
            public void onAddChildClick() {
                nodeEditFormPresenter.setCreateChildMode(nodeTreePresenter.getSelectedNodeId());
            }
        });

        uiPanelPresenter.setEditClickHandler(new EditClickHandler() {
            @Override
            public void onEditClick() {
                nodeEditFormPresenter.setEditNodeMode(nodeTreePresenter.getSelectedNodeId());
            }
        });

        uiPanelPresenter.setAfterDeleteHandler(new AfterDeleteHandler() {
            @Override
            public void onAfterDelete() {
                setSelectedNodeId(null);
                update();
            }
        });
    }

    private void setSelectedNodeId(Long selectedNodeId) {
        if (selectedNodeId == null) {
            nodeTreePresenter.setSelectedNode(null);
        }

        uiPanelPresenter.setSelectedNodeId(selectedNodeId);
        selectedNodeFormPresenter.setSelectedNode(selectedNodeId);
    }

    private void update() {
        nodeTreePresenter.updateTree();
        nodeTablePresenter.updateTable();
    }
}
