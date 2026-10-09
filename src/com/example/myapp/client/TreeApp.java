package com.example.myapp.client;

import java.util.ArrayList;
import java.util.List;

import com.example.myapp.client.ui.TreeWidget;
import com.example.myapp.shared.tree.Tree;
import com.example.myapp.shared.tree.TreeNode;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TreeApp implements EntryPoint {

    final Label listLabel = new Label("All nodes:");
    final Button refreshButton = new Button("Refresh");
    final RootPanel treeTableContainer = RootPanel.get("treeTableContainer");

    private List<TreeNode> serverData = new ArrayList<>();

    private final DataProviderServiceAsync dataProviderService = (DataProviderServiceAsync) GWT.create(DataProviderService.class);

    Tree tree;
    TreeWidget treeWidget;

    private void getDataFromServer() {
        dataProviderService.getTreeNodes(new AsyncCallback<List<TreeNode>>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Данные не пришли");
            }

            @Override
            public void onSuccess(List<TreeNode> result) {
                serverData.addAll(result);
                treeWidget = new TreeWidget(result);
                treeTableContainer.add(treeWidget);
            }
        });
    }

    public void onModuleLoad() {
        treeTableContainer.add(listLabel);
        treeTableContainer.add(refreshButton);
        refreshButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                treeWidget.removeFromParent();
                getDataFromServer();
                
            }
        });
        getDataFromServer();
    }
}
