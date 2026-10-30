package com.example.myapp.client.uipanel.view;

import com.example.myapp.client.uipanel.presenter.UiPanelPresenter;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UiPanelWidget extends Composite implements UiPanelPresenter.Display {

    private static final String LABEL_REFRESH = "Refresh";
    private static final String LABEL_SELECTED_ID = "selected node id = ";

    private FlowPanel main;
    private Button refreshButton;
    private Label selectedNode;

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

    public void setSelectedNodeId(Long id) {
        String selectedId = id == null ? "" : String.valueOf(id);
        selectedNode.setText(LABEL_SELECTED_ID + selectedId);
    }

    public void setClickHandler(ClickHandler onClick) {
        if (clickRegistration != null) {
            clickRegistration.removeHandler();
        }
        clickRegistration = refreshButton.addClickHandler(onClick);
    }

    public Widget asWidget() {
        return this;
    }
}
