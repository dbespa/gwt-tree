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
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
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

    private ErrorMessageWidget errorMessageWidget;
    private MainWidget mainWidget;

    //TODO mativ запоминать состояние дерева после изменений. Сейчас лоно всегда раскрывается полностью.
    //TODO mativ синглетоны на клиенте и сервере. Почему они разные.

    public void onModuleLoad() {
        build();
        update();
    }

    public static NodeRpcServiceAsync getRpcService() {
        return nodeRpcService;
    }

    private void build() {
        nodeRpcService = (NodeRpcServiceAsync) GWT.create(NodeRpcService.class);

        mainWidget = new MainWidget();
        errorMessageWidget = new ErrorMessageWidget();
        errorMessageWidget.setEventBus(eventBus);

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

    private void update() {
        nodeTreePresenter.updateTree(null);
        nodeTablePresenter.updateTable();
    }
}
