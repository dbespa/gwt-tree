package com.example.myapp.client.selectednodeform;

import com.example.myapp.client.TreeApp;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.event.AddRootClickEvent;
import com.example.myapp.event.AfterDeleteEvent;
import com.example.myapp.event.ErrorEvent;
import com.example.myapp.event.NodeSelectEvent;
import com.example.myapp.event.handler.AddRootClickHandler;
import com.example.myapp.event.handler.AfterDeleteHandler;
import com.example.myapp.event.handler.NodeSelectHandler;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class SelectedNodeFormPresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private EventBus eventBus;

    private SelectedNodeFormDisplay view;

    public SelectedNodeFormPresenter(EventBus eventBus, SelectedNodeFormDisplay view) {
        nodeRpcService = TreeApp.getRpcService();

        this.eventBus = eventBus;
        this.view = view;

        bind();
    }

    private void bind() {
        eventBus.addHandler(NodeSelectEvent.TYPE, new NodeSelectHandler() {
            @Override
            public void onNodeSelect(NodeSelectEvent nodeSelectEvent) {
                setSelectedNode(nodeSelectEvent.getSelectedNodeId());
            }
        });

        eventBus.addHandler(AddRootClickEvent.TYPE, new AddRootClickHandler() {
            @Override
            public void onAddRootClick(AddRootClickEvent addRootEvent) {
                setSelectedNode(null);
            }
        });

        eventBus.addHandler(AfterDeleteEvent.TYPE, new AfterDeleteHandler() {
            public void onAfterDelete(AfterDeleteEvent afterDeleteEvent) {
                setSelectedNode(null);
            }
        });
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    public void setSelectedNode(Long selectedNodeId) {
        if (selectedNodeId == null) {
            view.setSelectedNode(null);
            return;
        }

        nodeRpcService.getNodeById(selectedNodeId, new AsyncCallback<NodeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                view.setSelectedNode(null);
                String errorMessage = "Ошибка получения данных с сервера: " + caught.getLocalizedMessage();
                eventBus.fireEvent(new ErrorEvent(errorMessage));
            }

            @Override
            public void onSuccess(NodeBean result) {
                if (result == null) {
                    view.setSelectedNode(null);
                    eventBus.fireEvent(new ErrorEvent("Ошибка данных"));
                    return;
                }
                view.setSelectedNode(result);
            }
        });
    }
}
