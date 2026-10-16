package com.example.myapp.client;

import com.example.myapp.client.widget.NodeTableWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TreeApp implements EntryPoint {
    private NodeTableWidget nodeTableWidget;

    private void createNodeTableWidget() {
        nodeTableWidget = new NodeTableWidget(new Command() {
            @Override
            public void execute() {
                RootPanel.get("treeTableContainer").add(nodeTableWidget);
            }
        });
    }

    public void onModuleLoad() {
        Label label = new Label("All nodes:");
        label.addStyleName("heading");

        Button refreshButton = new Button("Refresh");

        RootPanel.get("treeTableContainer").add(label);
        RootPanel.get("treeTableContainer").add(refreshButton);

        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                nodeTableWidget.removeFromParent();
                createNodeTableWidget();
            }
        });

        createNodeTableWidget();
    }
}
