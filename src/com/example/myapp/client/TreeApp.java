package com.example.myapp.client;

import java.util.ArrayList;
import java.util.List;

import com.example.myapp.client.tree.Tree;
import com.example.myapp.client.ui.TreeWidget;
import com.example.myapp.shared.TreeNodeBean;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TreeApp implements EntryPoint {

    final Label listLabel = new Label("All nodes:");
    final Button refreshButton = new Button("Refresh");
    
    private List<TreeNodeBean> serverData = new ArrayList<>();
    
    private final DataProviderServiceAsync demoDataService = (DataProviderServiceAsync) GWT.create(DataProviderService.class);
    
    TreeDao treeDao;
    Tree tree;
    TreeWidget treeWidget;
    
    private void getDataFromServer() {
        demoDataService.getTreeNodes(new AsyncCallback<List<TreeNodeBean>>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Данные не пришли");
            }

            @Override
            public void onSuccess(List<TreeNodeBean> result) {
                serverData.addAll(result);
                treeDao = new TreeDao(serverData);
                tree = treeDao.getTree();
                treeWidget = new TreeWidget(tree);
                RootPanel.get().add(treeWidget);
            }

        });
    }

    public void onModuleLoad() {

        RootPanel.get().add(listLabel);
        RootPanel.get().add(refreshButton);
        getDataFromServer();
    }
}
