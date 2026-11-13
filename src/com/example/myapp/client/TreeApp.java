package com.example.myapp.client;

import com.example.myapp.client.editform.NodeEditFormPresenter;
import com.example.myapp.client.editform.NodeEditFormValidator;
import com.example.myapp.client.editform.NodeEditFormView;
import com.example.myapp.client.errormessage.ErrorMessageWidget;
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
import com.example.myapp.event.AddChildClickEvent;
import com.example.myapp.event.EditClickEvent;
import com.example.myapp.event.RefreshEvent;
import com.example.myapp.event.handler.AddChildClickHandler;
import com.example.myapp.event.handler.EditClickHandler;
import com.example.myapp.event.handler.RefreshHandler;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class TreeApp implements EntryPoint {

    private static NodeRpcServiceAsync nodeRpcService;
    private EventBus eventBus;

    private NodeTreePresenter nodeTreePresenter;
    private SelectedNodeFormPresenter selectedNodeFormPresenter;
    private NodeTablePresenter nodeTablePresenter;
    private UiPanelPresenter uiPanelPresenter;
    private NodeEditFormPresenter nodeEditFormPresenter;

    private MainWidget mainWidget;

    public void onModuleLoad() {
        build();
        bind();
        update();
    }

    public static NodeRpcServiceAsync getRpcService() {
        return nodeRpcService;
    }

    private void build() {
        nodeRpcService = (NodeRpcServiceAsync) GWT.create(NodeRpcService.class);
        eventBus = new SimpleEventBus();

        mainWidget = new MainWidget();
        ErrorMessageWidget errorMessageWidget = new ErrorMessageWidget(eventBus);

        RootPanel.get().add(mainWidget);
        buildTree();
        buildSelectedNodeForm();
        buildUiPanel();
        buildTable();
        buildEditForm();
    }

    private void buildTree() {
        NodeTreeView nodeTreeView = new NodeTreeView();
        FlowPanel treeContainer = mainWidget.getTreeContainer();

        nodeTreePresenter = new NodeTreePresenter(eventBus, nodeTreeView);
        nodeTreePresenter.go(treeContainer);
    }

    private void buildSelectedNodeForm() {
        SelectedNodeFormView selectedNodeFormView = new SelectedNodeFormView();
        FlowPanel selectedNodeFormContainer = mainWidget.getSelectedNodeFormContainer();

        selectedNodeFormPresenter = new SelectedNodeFormPresenter(eventBus, selectedNodeFormView);
        selectedNodeFormPresenter.go(selectedNodeFormContainer);
    }

    private void buildUiPanel() {
        UiPanelView uiPanelView = new UiPanelView();
        FlowPanel uiPanelContainer = mainWidget.getUiPanelContainer();

        uiPanelPresenter = new UiPanelPresenter(eventBus, uiPanelView);
        uiPanelPresenter.go(uiPanelContainer);
    }

    private void buildTable() {
        NodeTableView nodeTableView = new NodeTableView();
        FlowPanel tableContainer = mainWidget.getTableContainer();

        nodeTablePresenter = new NodeTablePresenter(eventBus, nodeTableView);
        nodeTablePresenter.go(tableContainer);
    }

    private void buildEditForm() {
        NodeEditFormView nodeEditFormView = new NodeEditFormView();
        NodeEditFormValidator nodeEditFormValidator = new NodeEditFormValidator();
        nodeEditFormPresenter = new NodeEditFormPresenter(eventBus, nodeEditFormValidator, nodeEditFormView);
    }

    private void bind() {
        eventBus.addHandler(RefreshEvent.TYPE, new RefreshHandler() {
            @Override
            public void onRefresh(RefreshEvent refreshEvent) {
                selectedNodeFormPresenter.setSelectedNode(nodeTreePresenter.getSelectedNodeId());
                update();
            }
        });

        eventBus.addHandler(AddChildClickEvent.TYPE, new AddChildClickHandler() {
            @Override
            public void onAddChildClick(AddChildClickEvent addChildClickEvent) {
                nodeEditFormPresenter.setCreateChildMode(nodeTreePresenter.getSelectedNodeId());
            }
        });

        eventBus.addHandler(EditClickEvent.TYPE, new EditClickHandler() {
            @Override
            public void onEditClick(EditClickEvent editClickEvent) {
                nodeEditFormPresenter.setEditNodeMode(nodeTreePresenter.getSelectedNodeId());
            }
        });
    }

    private void update() {
        nodeTreePresenter.updateTree();
        nodeTablePresenter.updateTable();
    }
}
