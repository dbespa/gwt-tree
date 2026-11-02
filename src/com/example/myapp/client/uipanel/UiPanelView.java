package com.example.myapp.client.uipanel;

import com.example.myapp.client.handler.RefreshHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class UiPanelView extends Composite implements UiPanelDisplay {

    private static final String STYLE_MAIN = "UiPanelView";
    private static final String STYLE_UI_PANEL = STYLE_MAIN + "-ui-panel";

    private static final String LABEL_REFRESH = "Refresh";
    private static final String LABEL_SELECTED_ID = "selected node id = ";

    private FlowPanel main;
    private Button refreshButton;
    private Label selectedNodeLabel;

    private HandlerRegistration clickRegistration;

    public UiPanelView() {
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        main.addStyleName(STYLE_UI_PANEL);

        refreshButton = new Button(LABEL_REFRESH);
        selectedNodeLabel = new Label(LABEL_SELECTED_ID);

        main.add(refreshButton);
        main.add(selectedNodeLabel);
    }

    public void setSelectedNodeId(Long id) {
        String selectedNodeId = id == null ? "" : String.valueOf(id);
        selectedNodeLabel.setText(LABEL_SELECTED_ID + selectedNodeId);
    }

    public void setRefreshHandler(final RefreshHandler onRefresh) {
        if (clickRegistration != null) {
            clickRegistration.removeHandler();
        }
        clickRegistration = refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onRefresh.onRefresh();
            }
        });
    }
}
