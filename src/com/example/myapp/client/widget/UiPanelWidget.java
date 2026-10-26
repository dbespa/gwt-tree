package com.example.myapp.client.widget;

import com.example.myapp.client.handler.RefreshHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

    private RefreshHandler handler;

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

    private void bind() {
        if (handler != null) {
            refreshButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    handler.onRefresh();
                    setSelectedNodeId(null);
                }
            });
        }
    }

    public void setHandler(RefreshHandler handler) {
        this.handler = handler;
        bind();
    }

    public void setSelectedNodeId(Long id) {
        String selectedId = id == null ? "" : String.valueOf(id);
        selectedNode.setText(LABEL_SELECTED_ID + selectedId);
    }
}
