package com.example.myapp.client.table;

import java.util.List;

import com.example.myapp.client.TreeApp;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.event.AfterDeleteEvent;
import com.example.myapp.event.ErrorEvent;
import com.example.myapp.event.RefreshEvent;
import com.example.myapp.event.handler.AfterDeleteHandler;
import com.example.myapp.event.handler.RefreshHandler;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class NodeTablePresenter {

    private static final String ERROR_DATA = "Ошибка данных";

    private NodeRpcServiceAsync nodeRpcService;
    private EventBus eventBus;

    private NodeTableDisplay view;

    public NodeTablePresenter(EventBus eventBus, NodeTableDisplay view) {
        nodeRpcService = TreeApp.getRpcService();
        this.eventBus = eventBus;

        this.view = view;

        bind();
    }

    private void bind() {
        eventBus.addHandler(RefreshEvent.TYPE, new RefreshHandler() {
            @Override
            public void onRefresh(RefreshEvent refreshEvent) {
                updateTable();
            }
        });

        eventBus.addHandler(AfterDeleteEvent.TYPE, new AfterDeleteHandler() {
            public void onAfterDelete(AfterDeleteEvent afterDeleteEvent) {
                updateTable();
            }
        });
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    public void updateTable() {
        nodeRpcService.getAllNodes(new AsyncCallback<List<NodeBean>>() {
            @Override
            public void onFailure(Throwable caught) {
                String errorMessage = "Ошибка получения данных с сервера: " + caught.getLocalizedMessage();
                eventBus.fireEvent(new ErrorEvent(errorMessage));
            }

            @Override
            public void onSuccess(List<NodeBean> result) {
                if (result == null) {
                    view.setNodeList(null);
                    eventBus.fireEvent(new ErrorEvent(ERROR_DATA));
                    return;
                }
                view.setNodeList(result);
            }
        });
    }
}
