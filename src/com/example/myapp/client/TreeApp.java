package com.example.myapp.client;

import com.example.myapp.client.rpc.NodeRpcService;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.client.widget.NodeTableWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TreeApp implements EntryPoint {

    private static final String LABEL_REFRESH = "Refresh";
    private static final String TREE_TABLE_CONTAINER = "treeTableContainer";
    private static final String STYLE_HEADING = "heading";
    private static final String LABEL_ALL_NODES = "All nodes:";

    private NodeRpcServiceAsync nodeRpcService;

    private NodeTableWidget nodeTableWidget;

    private Button refreshButton;

    public void onModuleLoad() {
        build();
        bind();
        update();
    }

    private void build() {
        nodeRpcService = (NodeRpcServiceAsync) GWT.create(NodeRpcService.class);

        Label label = new Label(LABEL_ALL_NODES);
        label.addStyleName(STYLE_HEADING);
        RootPanel.get(TREE_TABLE_CONTAINER).add(label);

        refreshButton = new Button(LABEL_REFRESH);
        RootPanel.get(TREE_TABLE_CONTAINER).add(refreshButton);

        nodeTableWidget = new NodeTableWidget(nodeRpcService);
        RootPanel.get(TREE_TABLE_CONTAINER).add(nodeTableWidget);
    }

    private void bind() {
        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                nodeTableWidget.refreshTable();
            }
        });
    }

    private void update() {
        nodeTableWidget.refreshTable();
    }
}
