package com.example.myapp.client.selectednodeform;

import com.example.myapp.client.EventBusSingleton;
import com.example.myapp.client.TreeApp;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.event.AddRootClickEvent;
import com.example.myapp.event.ErrorEvent;
import com.example.myapp.event.NodeSelectEvent;
import com.example.myapp.event.RefreshEvent;
import com.example.myapp.event.handler.AddRootClickHandler;
import com.example.myapp.event.handler.NodeSelectHandler;
import com.example.myapp.event.handler.RefreshHandler;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class SelectedNodeFormPresenter {

    private static final String ERROR_DATA = "Ошибка данных";

    private NodeRpcServiceAsync nodeRpcService;

    private SelectedNodeFormDisplay view;

    public SelectedNodeFormPresenter(SelectedNodeFormDisplay view) {
        nodeRpcService = TreeApp.getRpcService();
        this.view = view;
        bind();
    }

    private void bind() {
        EventBusSingleton.getInstance().addHandler(NodeSelectEvent.TYPE, new NodeSelectHandler() {
            @Override
            public void onNodeSelect(NodeSelectEvent nodeSelectEvent) {
                setSelectedNode(nodeSelectEvent.getSelectedNodeId());
            }
        });

        EventBusSingleton.getInstance().addHandler(AddRootClickEvent.TYPE, new AddRootClickHandler() {
            @Override
            public void onAddRootClick(AddRootClickEvent addRootEvent) {
                setSelectedNode(null);
            }
        });

        EventBusSingleton.getInstance().addHandler(RefreshEvent.TYPE, new RefreshHandler() {
            @Override
            public void onRefresh(RefreshEvent refreshEvent) {
                setSelectedNode(refreshEvent.getSelectedNodeId());
            }
        });
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    private void setSelectedNode(Long selectedNodeId) {
        if (selectedNodeId == null) {
            view.setSelectedNode(null);
            return;
        }

        nodeRpcService.getNodeById(selectedNodeId, new AsyncCallback<NodeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                view.setSelectedNode(null);
                String errorMessage = "Ошибка получения данных с сервера: " + caught.getLocalizedMessage();
                EventBusSingleton.getInstance().fireEvent(new ErrorEvent(errorMessage));
            }

            @Override
            public void onSuccess(NodeBean result) {
                if (result == null) {
                    view.setSelectedNode(null);
                    EventBusSingleton.getInstance().fireEvent(new ErrorEvent(ERROR_DATA));
                    return;
                }
                view.setSelectedNode(result);
            }
        });
    }
}
