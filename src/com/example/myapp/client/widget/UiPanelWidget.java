package com.example.myapp.client.widget;

import com.example.myapp.client.handler.RefreshHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class UiPanelWidget extends Composite {

    private static final String LABEL_REFRESH = "Refresh";
    private static final String LABEL_SELECTED_ID = "selected node id = ";

    private FlowPanel main;

    private Button refreshButton;
    private Label selectedNode;

    private RefreshHandler onRefresh;
    private HandlerRegistration clickRegistration;

    public UiPanelWidget() {
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();

        refreshButton = new Button(LABEL_REFRESH);
        selectedNode = new Label(LABEL_SELECTED_ID);

        main.add(refreshButton);
        main.add(selectedNode);
    }

    public void setRefreshHandler(RefreshHandler refreshHandler) {
        if (clickRegistration != null) {
            clickRegistration.removeHandler();
        }

        this.onRefresh = refreshHandler;

        if (refreshHandler != null) {
            clickRegistration = refreshButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    onRefresh.onRefresh();
                }
            });
        }
    }

    public void setSelectedNodeId(Long id) {
        String selectedId = id == null ? "" : String.valueOf(id);
        selectedNode.setText(LABEL_SELECTED_ID + selectedId);
    }
}
