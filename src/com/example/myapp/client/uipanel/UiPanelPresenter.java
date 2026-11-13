package com.example.myapp.client.uipanel;

import com.example.myapp.client.TreeApp;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.event.AddChildClickEvent;
import com.example.myapp.event.AddRootClickEvent;
import com.example.myapp.event.AfterDeleteEvent;
import com.example.myapp.event.EditClickEvent;
import com.example.myapp.event.ErrorEvent;
import com.example.myapp.event.NodeSelectEvent;
import com.example.myapp.event.RefreshEvent;
import com.example.myapp.event.handler.AddChildClickHandler;
import com.example.myapp.event.handler.AddRootClickHandler;
import com.example.myapp.event.handler.AfterDeleteHandler;
import com.example.myapp.event.handler.EditClickHandler;
import com.example.myapp.event.handler.NodeSelectHandler;
import com.example.myapp.event.handler.RefreshHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class UiPanelPresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private EventBus eventBus;

    private UiPanelDisplay view;

    private Long selectedId;

    public UiPanelPresenter(EventBus eventBus, UiPanelDisplay view) {
        nodeRpcService = TreeApp.getRpcService();
        this.eventBus = eventBus;

        this.view = view;
        view.setButtonsActive(false);

        bind();
    }

    private void bind() {
        eventBus.addHandler(NodeSelectEvent.TYPE, new NodeSelectHandler() {
            @Override
            public void onNodeSelect(NodeSelectEvent nodeSelectEvent) {
                setSelectedNodeId(nodeSelectEvent.getSelectedNodeId());
            }
        });

        eventBus.addHandler(AddRootClickEvent.TYPE, new AddRootClickHandler() {
            @Override
            public void onAddRootClick(AddRootClickEvent addRootEvent) {
                setSelectedNodeId(null);
            }
        });

        eventBus.addHandler(AfterDeleteEvent.TYPE, new AfterDeleteHandler() {
            public void onAfterDelete(AfterDeleteEvent afterDeleteEvent) {
                setSelectedNodeId(null);
            }
        });

        view.setRefreshHandler(new RefreshHandler() {
            @Override
            public void onRefresh(RefreshEvent refreshEvent) {
                eventBus.fireEvent(refreshEvent);
            }
        });

        view.setAddRootClickHandler(new AddRootClickHandler() {
            @Override
            public void onAddRootClick(AddRootClickEvent addRootEvent) {
                eventBus.fireEvent(addRootEvent);
            }
        });

        view.setAddChildClickHandler(new AddChildClickHandler() {
            @Override
            public void onAddChildClick(AddChildClickEvent addChildClickEvent) {
                eventBus.fireEvent(addChildClickEvent);
            }
        });

        view.setEditClickHandler(new EditClickHandler() {
            @Override
            public void onEditClick(EditClickEvent editClickEvent) {
                eventBus.fireEvent(editClickEvent);
            }
        });

        view.setAfterDeleteHandler(new AfterDeleteHandler() {
            @Override
            public void onAfterDelete(final AfterDeleteEvent afterDeleteEvent) {
                nodeRpcService.deleteNode(selectedId, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        String errorMessage = "Ошибка сервера: " + caught.getLocalizedMessage();
                        eventBus.fireEvent(new ErrorEvent(errorMessage));
                    }

                    @Override
                    public void onSuccess(Void result) {
                        view.setSelectedNodeId(null);
                        eventBus.fireEvent(afterDeleteEvent);
                    }
                });
            }
        });
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    public void setSelectedNodeId(Long id) {
        selectedId = id;
        view.setSelectedNodeId(id);
        view.setButtonsActive(id != null);
    }
}
