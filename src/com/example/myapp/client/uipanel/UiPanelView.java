package com.example.myapp.client.uipanel;

import com.example.myapp.client.handler.AddChildHandler;
import com.example.myapp.client.handler.AddRootClickHandler;
import com.example.myapp.client.handler.DeleteHandler;
import com.example.myapp.client.handler.EditHandler;
import com.example.myapp.client.handler.RefreshHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class UiPanelView extends Composite implements UiPanelDisplay {

    private static final String STYLE_MAIN = "UiPanelView";
    private static final String STYLE_UI_PANEL = STYLE_MAIN + "-ui-panel";

    private static final String LABEL_REFRESH = "Refresh";
    private static final String LABEL_ADD_ROOT_NODE = "Add root node";
    private static final String LABEL_ADD_CHILD = "Add child";
    private static final String LABEL_EDIT = "Edit";
    private static final String LABEL_DELETE = "Delete";
    private static final String LABEL_SELECTED_ID = "selected node id = ";

    private FlowPanel main;
    private Button refreshButton;
    private Button addRootButton;
    private Button addChildButton;
    private Button editButton;
    private Button deleteButton;
    private Label selectedNodeLabel;

    public UiPanelView() {
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        main.addStyleName(STYLE_UI_PANEL);

        refreshButton = new Button(LABEL_REFRESH);
        addRootButton = new Button(LABEL_ADD_ROOT_NODE);
        addChildButton = new Button(LABEL_ADD_CHILD);
        editButton = new Button(LABEL_EDIT);
        deleteButton = new Button(LABEL_DELETE);
        selectedNodeLabel = new Label(LABEL_SELECTED_ID);

        main.add(refreshButton);
        main.add(addRootButton);
        main.add(addChildButton);
        main.add(editButton);
        main.add(deleteButton);
        main.add(selectedNodeLabel);
    }

    public void setSelectedNodeId(Long id) {
        String selectedNodeId = id == null ? "" : String.valueOf(id);
        selectedNodeLabel.setText(LABEL_SELECTED_ID + selectedNodeId);
    }

    public void setRefreshHandler(final RefreshHandler onRefresh) {
        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onRefresh.onRefresh();
            }
        });
    }

    public void setAddRootClickHandler(final AddRootClickHandler onAddRoot) {
        addRootButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onAddRoot.onAddRootClick();
            }
        });
    }

    public void setAddChildHandler(final AddChildHandler onAddChild) {
        addChildButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onAddChild.onAddChild();
            }
        });
    }

    public void setEditHandler(final EditHandler onEdit) {
        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onEdit.onEdit();
            }
        });
    }

    public void setDeleteHandler(final DeleteHandler onDelete) {
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onDelete.onDelete();
            }
        });
    }

    public void setButtonsActive(Boolean isActive) {
        addChildButton.setEnabled(isActive);
        editButton.setEnabled(isActive);
        deleteButton.setEnabled(isActive);
    }
}
